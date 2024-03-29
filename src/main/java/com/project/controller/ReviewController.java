package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Review;
import com.project.model.dto.ReviewDTO;
import com.project.service.impl.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ReviewController {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private ReviewServiceImpl reviewService;

    @RequestMapping(value = "/review/patient/{idPatient}/doctor/{idDoctor}",method = RequestMethod.GET)
    public ResponseEntity<?> getReview(@PathVariable int idPatient,@PathVariable int idDoctor){
        try{
            Review review=reviewService.findReview(idPatient,idDoctor);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved review",ReviewController.class));
            return new ResponseEntity<>(review, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve review failed with message: {1}",ReviewController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/review",method = RequestMethod.POST)
    public ResponseEntity<?> saveAffiliation(@RequestBody ReviewDTO reviewDTO){
        try{
            Review review=reviewService.save(reviewDTO);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved review",UserController.class));
            return new ResponseEntity<>(review, HttpStatus.OK);
        }catch (Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve review failed with message: {1}",ReviewController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reviews/{idDoctor}", method = RequestMethod.GET)
    public ResponseEntity<?> getReviews(@PathVariable int idDoctor){
        List<Review> reviews=reviewService.findReviews(idDoctor);
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved doctor's reviews",ReviewController.class));
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
