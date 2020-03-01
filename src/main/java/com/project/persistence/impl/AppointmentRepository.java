package com.project.persistence.impl;

import com.project.model.Appointment;
import com.project.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppointmentRepository {
    private static SessionFactory sessionFactory;

    public Iterable<Appointment> getAll() {
        sessionFactory= HibernateUtil.getSessionFactory();
        List<Appointment> appointments=null;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                appointments=session.createNativeQuery("select * from appointment",Appointment.class).list();
                return appointments;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Appointment save(Appointment appointment) {
        sessionFactory= HibernateUtil.getSessionFactory();
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                session.save(appointment);
                return appointment;
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
