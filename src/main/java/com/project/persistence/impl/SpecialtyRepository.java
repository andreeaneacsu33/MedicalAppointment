package com.project.persistence.impl;

import com.project.model.Specialty;
import com.project.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpecialtyRepository {
    private static SessionFactory sessionFactory;
    public Iterable<Specialty> getAll() {
        sessionFactory= HibernateUtil.getSessionFactory();
        List<Specialty> specialties;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                specialties=session.createNativeQuery("select * from specialty order by name asc",Specialty.class).list();
                return specialties;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Specialty save(Specialty specialty) {
        sessionFactory= HibernateUtil.getSessionFactory();
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                session.save(specialty);
                return specialty;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Specialty findOne(String name) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Specialty specialty;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                specialty=session.createNativeQuery("select * from specialty where name like :nm",Specialty.class).setParameter("nm",name).getSingleResult();
                System.out.println(specialty);
                return specialty;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Specialty findOne(int id) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Specialty specialty;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                specialty=session.createNativeQuery("select * from specialty where id like :idd",Specialty.class).setParameter("idd",id).getSingleResult();
                System.out.println(specialty);
                return specialty;
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
