package com.project.service.impl;

import com.project.model.Doctor;
import com.project.model.Review;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.ReviewRepository;
import com.project.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repoDoctor;
    private final ReviewRepository repoReview;
    private static final int PAGE_SIZE=6;
    @Autowired
    public DoctorServiceImpl(DoctorRepository repoDoctor, ReviewRepository repoReview) {
        this.repoDoctor = repoDoctor;
        this.repoReview = repoReview;
    }

    @Override
    public Doctor findDoctor(String username) {
        return repoDoctor.findOne(username);
    }

    @Override
    public int findTotalPages() {
        int totalSize=repoDoctor.count();
        return (totalSize+PAGE_SIZE-1)/PAGE_SIZE;
    }

    @Override
    public List<Doctor> findPaginated(int page) {
        List<Doctor> doctors=new ArrayList<>();
        repoDoctor.getPaginated((page-1)*PAGE_SIZE,PAGE_SIZE).forEach(doctors::add);
        return doctors;
    }

    @Override
    public double findOverallRating(int idDoctor) {
        List<Review> reviews=new ArrayList<>();
        repoReview.getAll(idDoctor).forEach(reviews::add);
        double averageRating=reviews.stream().mapToDouble(Review::getRating).average().orElse(5);
        return Double.parseDouble(new DecimalFormat("#.##").format(averageRating));
    }

    @Override
    public double findOverallWaitingTime(int idDoctor) {
        List<Review> reviews=new ArrayList<>();
        repoReview.getAll(idDoctor).forEach(reviews::add);
        double averageWaitingTime=reviews.stream().mapToDouble(Review::getWaitingTime).average().orElse(0);
        return Double.parseDouble(new DecimalFormat("#.##").format(averageWaitingTime));
    }
}
