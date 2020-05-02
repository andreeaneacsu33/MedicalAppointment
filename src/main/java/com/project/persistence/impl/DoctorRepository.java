package com.project.persistence.impl;

import com.project.model.Doctor;
import com.project.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorRepository {
    private static SessionFactory sessionFactory;

    public Iterable<Doctor> getAll() {
        sessionFactory= HibernateUtil.getSessionFactory();
        List<Doctor> doctors;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                doctors=session.createNativeQuery("select * from doctor",Doctor.class).list();
                return doctors;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Iterable<Doctor> getPaginated(int first,int size){
        sessionFactory= HibernateUtil.getSessionFactory();
        List<Doctor> doctors;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                doctors=session.createNativeQuery("select * from doctor",Doctor.class).setFirstResult(first).setMaxResults(size).list();
                return doctors;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public int count(){
        sessionFactory= HibernateUtil.getSessionFactory();
        int size=0;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                size=((Number)session.createNativeQuery("select count(*) from doctor").getSingleResult()).intValue();
                return size;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return size;
    }

    public Doctor save(Doctor doctor) {
        sessionFactory= HibernateUtil.getSessionFactory();
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                session.save(doctor);
                return doctor;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Doctor findOne(int id){
        sessionFactory=HibernateUtil.getSessionFactory();
        Doctor doctor;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                doctor=session.createNativeQuery("select * from doctor where id like :idd",Doctor.class).setParameter("idd",id).getSingleResult();
                return doctor;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Doctor findOne(String username) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Doctor doctor;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                doctor=session.createNativeQuery("select * from doctor where email like :usr",Doctor.class).setParameter("usr",username).getSingleResult();
                System.out.println(doctor);
                return doctor;
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
