package com.project.service.impl;

import com.project.model.Review;
import com.project.model.dto.ReviewDTO;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.PatientRepository;
import com.project.persistence.impl.ReviewRepository;
import com.project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository repoReview;

    @Autowired
    private DoctorRepository repoDoctor;

    @Autowired
    private PatientRepository repoPatient;


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
            return repoReview.save(review);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Review findReview(int idPatient,int idDoctor) {
        return repoReview.findOne(idPatient,idDoctor);
    }
}
