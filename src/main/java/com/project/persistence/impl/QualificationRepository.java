package com.project.persistence.impl;

import com.project.model.Doctor;
import com.project.model.Qualification;
import com.project.model.Specialty;
import com.project.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QualificationRepository {
    private static SessionFactory sessionFactory;

    public Iterable<Qualification> getAll() {
        sessionFactory= HibernateUtil.getSessionFactory();
        List<Qualification> qualifications=null;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                qualifications=session.createNativeQuery("select * from qualification order by title asc",Qualification.class).list();
                return qualifications;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Qualification findOne(String title) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Qualification qualification;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                qualification=session.createNativeQuery("select * from qualification where title like :nm",Qualification.class).setParameter("nm",title).getSingleResult();
                System.out.println(qualification);
                return qualification;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Qualification findOne(int idDoctor) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Qualification qualification;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                qualification=session.createNativeQuery("select * from qualification where idDoctor like :idd",Qualification.class).setParameter("idd",idDoctor).getSingleResult();
                System.out.println(qualification);
                return qualification;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Qualification save(Qualification qualification) {
        sessionFactory= HibernateUtil.getSessionFactory();
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                session.save(qualification);
                return qualification;
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
