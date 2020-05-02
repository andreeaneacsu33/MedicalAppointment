package com.project.service.impl;

import com.project.decorator.EmailImpl;
import com.project.decorator.IEmail;
import com.project.decorator.RegisterEmailDecorator;
import com.project.decorator.ReviewEmailDecorator;
import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Email;
import com.project.model.Patient;
import com.project.model.Review;
import com.project.model.dto.ReviewDTO;
import com.project.model.dto.UserDTO;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.PatientRepository;
import com.project.persistence.impl.ReviewRepository;
import com.project.service.ReviewService;
import com.project.service.adapter.ReviewObjectAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private ReviewRepository repoReview;

    @Autowired
    private DoctorRepository repoDoctor;

    @Autowired
    private PatientRepository repoPatient;

    @Autowired
    public JavaMailSender emailSender;


    @Override
    public List<Review> findReviews(int idDoctor) {
        List<Review> reviewList = new ArrayList<>();
        repoReview.getAll(idDoctor).forEach(reviewList::add);
        logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Retrieved reviews",ReviewServiceImpl.class));
        return reviewList;
    }

    @Override
    public Review save(ReviewDTO reviewDTO) {
        ReviewObjectAdapter reviewObjectAdapter = new ReviewObjectAdapter(repoDoctor, repoPatient);
        Review review = (Review) reviewObjectAdapter.convertFromClientToModel(reviewDTO);
        Review reviewSaved = repoReview.save(review);
        sendEmail(review.getIdPatient(), reviewSaved);
        logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Saved review",ReviewServiceImpl.class));
        return reviewSaved;
    }

    private void sendEmail(Patient patient, Review review) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            IEmail email = new ReviewEmailDecorator(new EmailImpl(), patient, review);
            Email emailContent = email.getEmail();
            helper.setTo(emailContent.getTo());
            helper.setSubject(emailContent.getSubject());
            message.setContent(emailContent.getText(), "text/html");
            emailSender.send(message);
            logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Email sent",ReviewServiceImpl.class));
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Review findReview(int idPatient, int idDoctor) {
        logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Review found",ReviewServiceImpl.class));
        return repoReview.findOne(idPatient, idDoctor);
    }
}
