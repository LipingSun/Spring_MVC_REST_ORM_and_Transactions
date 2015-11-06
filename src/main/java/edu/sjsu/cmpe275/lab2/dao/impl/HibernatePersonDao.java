package edu.sjsu.cmpe275.lab2.dao.impl;

import edu.sjsu.cmpe275.lab2.dao.PersonDao;
import edu.sjsu.cmpe275.lab2.domain.Person;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernatePersonDao implements PersonDao {

    private SessionFactory sessionFactory;

    public HibernatePersonDao() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public void store(Person person) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        try{
            tx.begin();
            session.saveOrUpdate(person);
            tx.commit();
        }catch(RuntimeException e){
            tx.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(long personId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        try{
            tx.begin();
            Person person = session.get(Person.class, personId);
            session.delete(person);
            tx.commit();
        }catch(RuntimeException e){
            tx.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    @Override
    public Person findById(long personId) {
        Session session = sessionFactory.openSession();
        try{
            return session.get(Person.class, personId);
        }finally {
            session.close();
        }
    }

    @Override
    public List<Person> findAll() {
        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("from Person");
            return query.list();
        }finally {
            session.close();
        }
    }
}
