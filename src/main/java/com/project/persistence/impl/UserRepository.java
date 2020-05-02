package com.project.persistence.impl;

import com.project.model.User;
import com.project.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository{
    private static SessionFactory sessionFactory;

    public UserRepository() {
    }
    public Iterable<User> getAll() {
        sessionFactory= HibernateUtil.getSessionFactory();
        List<User> users;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                users=session.createNativeQuery("select * from user",User.class).list();
                return users;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public User getByEmail(String username) {
        sessionFactory= HibernateUtil.getSessionFactory();
        User user;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                user=session.createNativeQuery("select * from user where email like :usr",User.class).setParameter("usr",username).getSingleResult();
                System.out.println(user);
                return user;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public User save(User user) {
        sessionFactory= HibernateUtil.getSessionFactory();
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                session.save(user);
                return user;
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
