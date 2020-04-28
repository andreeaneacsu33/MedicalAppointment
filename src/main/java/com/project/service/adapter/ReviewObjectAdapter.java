package com.project.service.adapter;


import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Review;
import com.project.model.dto.ReviewDTO;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.PatientRepository;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewObjectAdapter implements ObjectAdapter {
    private DoctorRepository repoDoctor;
    private PatientRepository repoPatient;
    private AbstractLogger logger = Logger.getLogger();


    public ReviewObjectAdapter(DoctorRepository repoDoctor, PatientRepository repoPatient) {
        this.repoDoctor=repoDoctor;
        this.repoPatient=repoPatient;
    }

    @Override
    public ModelObject convertFromClientToModel(ClientObject clientObject) {
        try {
            Review review = new Review();
            ReviewDTO reviewDTO=(ReviewDTO)clientObject;
            review.setIdPatient(repoPatient.findOne(reviewDTO.getPatientEmail()));
            review.setIdDoctor(repoDoctor.findOne(reviewDTO.getDoctorEmail()));
            review.setDescription(reviewDTO.getDescription());
            review.setRating(reviewDTO.getRating());
            review.setWaitingTime(reviewDTO.getWaitingTime());
            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            Date reviewDate = df.parse(reviewDTO.getReviewDate());
            review.setReviewDate(reviewDate);
            review.setRecommend(reviewDTO.getRecommend());
            return review;
        } catch (ParseException ex) {
            logger.log(AbstractLogger.ERROR, MessageFormat.format("Converting review to reviewDTO failed with message: {0}",ex.getMessage()));
            ex.printStackTrace();
        }
        return null;
    }

}
