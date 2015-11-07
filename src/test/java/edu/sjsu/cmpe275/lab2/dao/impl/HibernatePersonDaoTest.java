package edu.sjsu.cmpe275.lab2.dao.impl;

import edu.sjsu.cmpe275.lab2.domain.Address;
import edu.sjsu.cmpe275.lab2.domain.Organization;
import edu.sjsu.cmpe275.lab2.domain.Person;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Liping on 11/6/15.
 */
public class HibernatePersonDaoTest {


    private HibernatePersonDao hibernatePersonDao;
    private HibernateOrganizationDao hibernateOrganizationDao;

    public HibernatePersonDaoTest() {
        hibernatePersonDao = new HibernatePersonDao();
        hibernateOrganizationDao = new HibernateOrganizationDao();
    }

    @Test
    public void testStore() throws Exception {
        Address address = new Address("street", "city", "state", "95555");
        Organization org = hibernateOrganizationDao.findById(1);
        Person person = new Person("Jan", "Frank", "email@gmail.com", "desc", address, org);
        hibernatePersonDao.store(person);
    }

    @Test
    public void testFindById() throws Exception {
        Person person = hibernatePersonDao.findById(4);
        Assert.assertEquals(person.getId(), 4);
    }

    @Test
    public void testFindAll() throws Exception {
        hibernatePersonDao.findAll();
    }

    @Test
    public void testDelete() throws Exception {
        hibernatePersonDao.delete(1);
    }
}