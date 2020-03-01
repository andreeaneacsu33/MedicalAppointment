package com.project.service;

import com.project.model.Review;
import com.project.model.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<Review> findReviews(int idDoctor);
    Review save(ReviewDTO reviewDTO);
    Review findReview(int idPatient, int idDoctor);
}
