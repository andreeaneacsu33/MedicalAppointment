package com.project.service.impl;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Doctor;
import com.project.model.Review;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.ReviewRepository;
import com.project.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorServiceImpl implements DoctorService {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private DoctorRepository repoDoctor;

    @Autowired
    private ReviewRepository repoReview;

    private static final int PAGE_SIZE=6;

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
    public Map<Double, Integer> findOverallRatingStatistics(int idDoctor) {
        List<Review> reviews=new ArrayList<>();
        repoReview.getAll(idDoctor).forEach(reviews::add);
        Map<Double,Integer> ratings=new HashMap<>();
        for(double i=0;i<=5;i+=0.5){
            final double rate=i;
            int count = (int)reviews.stream().filter(x->x.getRating()==rate).count();
            ratings.put(i,count);
        }
        return ratings;
    }

    @Override
    public Map<Integer, Integer> findOverallWaitingTimeStatistics(int idDoctor) {
        List<Review> reviews=new ArrayList<>();
        repoReview.getAll(idDoctor).forEach(reviews::add);
        Map<Integer,Integer> ratings=new HashMap<>();
        for(Review r:reviews){
            Integer val = ratings.get(r.getWaitingTime());
            if(ratings.containsKey(r.getWaitingTime())){
                ratings.replace(r.getWaitingTime(),val+1);
            }else{
                ratings.put(r.getWaitingTime(),1);
            }
        }
        return ratings;
    }

    @Override
    public double findOverallWaitingTime(int idDoctor) {
        List<Review> reviews=new ArrayList<>();
        repoReview.getAll(idDoctor).forEach(reviews::add);
        double averageWaitingTime=reviews.stream().mapToDouble(Review::getWaitingTime).average().orElse(0);
        return Double.parseDouble(new DecimalFormat("#.##").format(averageWaitingTime));
    }
}
