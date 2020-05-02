package com.project.persistence.impl;

import com.project.model.Review;
import com.project.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepository {
    private static SessionFactory sessionFactory;

    public Iterable<Review> getAll() {
        sessionFactory= HibernateUtil.getSessionFactory();
        List<Review> reviews;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                reviews=session.createNativeQuery("select * from review",Review.class).list();
                return reviews;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Iterable<Review> getAll(int idDoctor) {
        sessionFactory= HibernateUtil.getSessionFactory();
        List<Review> reviews;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                reviews=session.createNativeQuery("select * from review where idDoctor like :idd",Review.class).setParameter("idd",idDoctor).list();
                return reviews;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Review save(Review review) {
        sessionFactory= HibernateUtil.getSessionFactory();
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                session.save(review);
                return review;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Review findOne(int idPatient,int idDoctor) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Review review;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                review=session.createNativeQuery("select * from review where idPatient like :idP and idDoctor like :idD",Review.class).setParameter("idP",idPatient).setParameter("idD",idDoctor).getSingleResult();
                System.out.println(review);
                return review;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }
}
