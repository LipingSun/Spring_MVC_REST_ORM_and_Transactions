package edu.sjsu.cmpe275.lab2.dao.impl;

import edu.sjsu.cmpe275.lab2.domain.Organization;
import edu.sjsu.cmpe275.lab2.dao.OrganizationDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by jianxin on 10/30/15.
 */
public class HibernateOrganizationDao implements OrganizationDao {

    private SessionFactory sessionFactory;

    public HibernateOrganizationDao() {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public void store(Organization organization) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        try{
            tx.begin();
            session.saveOrUpdate(organization);
            tx.commit();
        }catch(RuntimeException e){
            tx.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(long orgId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        try{
            tx.begin();
            Organization organization = (Organization) session.get(Organization.class, orgId);
            session.delete(organization);
            tx.commit();
        }catch(RuntimeException e){
            tx.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    @Override
    public Organization findById(long orgId) {
        Session session = sessionFactory.openSession();
        try{
            return (Organization) session.get(Organization.class, orgId);
        }finally {
            session.close();
        }
    }

    @Override
    public List<Organization> findAll() {
        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("from Organization");
            return query.list();
        }finally {
            session.close();
        }
    }
}
