package edu.sjsu.cmpe275.lab2.dao.impl;

import edu.sjsu.cmpe275.lab2.dao.OrganizationDao;
import edu.sjsu.cmpe275.lab2.domain.Organization;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

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
            session.save(organization);
            tx.commit();
        }catch(RuntimeException e){
            tx.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public Organization update(Organization org) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        try{
            tx.begin();
            Organization organization = session.get(Organization.class, org.getId());
            organization.setName(org.getName());
            if (org.getDescription() != null) {
                organization.setDescription(org.getDescription());
            }
            if (org.getAddress() != null) {
                organization.setAddress(org.getAddress());
            }
            session.update(organization);
            tx.commit();
            return organization;
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
            Organization organization = session.get(Organization.class, orgId);
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
            return session.get(Organization.class, orgId);
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
