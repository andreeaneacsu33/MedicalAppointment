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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
        return reviewList;
    }

    @Override
    public Review save(ReviewDTO reviewDTO) {
        try {
            Review review = new Review();
            review.setIdPatient(repoPatient.findOne(reviewDTO.getPatientEmail()));
            review.setIdDoctor(repoDoctor.findOne(reviewDTO.getDoctorEmail()));
            review.setDescription(reviewDTO.getDescription());
            review.setRating(reviewDTO.getRating());
            review.setWaitingTime(reviewDTO.getWaitingTime());
            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            Date reviewDate = df.parse(reviewDTO.getReviewDate());
            review.setReviewDate(reviewDate);
            review.setRecommend(reviewDTO.getRecommend());
            Review reviewSaved=repoReview.save(review);
            sendEmail(review.getIdPatient(),reviewSaved);
            return reviewSaved;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendEmail(Patient patient,Review review){
        try {
            MimeMessage message=emailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            IEmail email=new ReviewEmailDecorator(new EmailImpl(),patient,review);
            Email emailContent=email.getEmail();
            helper.setTo(emailContent.getTo());
            helper.setSubject(emailContent.getSubject());
            message.setContent(emailContent.getText(),"text/html");
            emailSender.send(message);
            logger.log(AbstractLogger.INFO, "Email successfully sent!");
        } catch (MessagingException exception ) {
            exception.printStackTrace();
        }
    }

    @Override
    public Review findReview(int idPatient,int idDoctor) {
        return repoReview.findOne(idPatient,idDoctor);
    }
}
