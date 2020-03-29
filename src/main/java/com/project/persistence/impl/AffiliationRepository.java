package com.project.persistence.impl;

import com.project.model.Affiliation;
import com.project.persistence.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AffiliationRepository {
    private static SessionFactory sessionFactory;

    public Iterable<Affiliation> getAll() {
        sessionFactory= HibernateUtil.getSessionFactory();
        List<Affiliation> affiliations;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                affiliations=session.createNativeQuery("select * from affiliation order by hospitalName asc",Affiliation.class).list();
                return affiliations;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Affiliation findOne(String hospitalTitle) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Affiliation affiliation;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                affiliation=session.createNativeQuery("select * from affiliation where hospitalName like :hsp",Affiliation.class).setParameter("hsp",hospitalTitle).getSingleResult();
                System.out.println(affiliation);
                return affiliation;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Affiliation findOne(int idDoctor) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Affiliation affiliation;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                affiliation=session.createNativeQuery("select * from affiliation where idDoctor like :idd",Affiliation.class).setParameter("idd",idDoctor).getSingleResult();
                System.out.println(affiliation);
                return affiliation;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }

    public Affiliation findOneById(int idAffiliation) {
        sessionFactory= HibernateUtil.getSessionFactory();
        Affiliation affiliation;
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                affiliation=session.createNativeQuery("select * from affiliation where id like :idd",Affiliation.class).setParameter("idd",idAffiliation).getSingleResult();
                System.out.println(affiliation);
                return affiliation;
            }catch (RuntimeException ex){
                ex.printStackTrace();
                if(trans!=null){
                    trans.rollback();
                }
            }
        }
        return null;
    }


    public Affiliation save(Affiliation affiliation) {
        sessionFactory= HibernateUtil.getSessionFactory();
        try(Session session=sessionFactory.openSession()){
            Transaction trans=null;
            try{
                trans=session.beginTransaction();
                session.save(affiliation);
                return affiliation;
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
