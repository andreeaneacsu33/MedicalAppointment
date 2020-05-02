package com.project.persistence.impl;

import com.project.model.Patient;
import com.project.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientRepository {
    private static SessionFactory sessionFactory;
    public Iterable<Patient> getAll() {
        sessionFactory= HibernateUtil.getSessionFactory();
        List<Patient> patients;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                patients=session.createNativeQuery("select * from patient",Patient.class).list();
                return patients;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Patient save(Patient patient) {
        sessionFactory= HibernateUtil.getSessionFactory();
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                session.save(patient);
                return patient;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Patient findOne(int id){
        sessionFactory=HibernateUtil.getSessionFactory();
        Patient patient;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                patient=session.createNativeQuery("select * from patient where id like :idd",Patient.class).setParameter("idd",id).getSingleResult();
                return patient;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Patient findOne(String username) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Patient patient;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                patient=session.createNativeQuery("select * from patient where email like :usr",Patient.class).setParameter("usr",username).getSingleResult();
                System.out.println(patient);
                return patient;
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
