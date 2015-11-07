package edu.sjsu.cmpe275.lab2.dao.impl;

import edu.sjsu.cmpe275.lab2.domain.Address;
import edu.sjsu.cmpe275.lab2.domain.Organization;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Liping on 11/6/15.
 */
public class HibernateOrganizationDaoTest {

    private HibernateOrganizationDao hibernateOrganizationDao;

    public HibernateOrganizationDaoTest() {
        hibernateOrganizationDao = new HibernateOrganizationDao();
    }
    @Test
    public void testStore() throws Exception {
        Address address = new Address("street", "city", "state", "95555");
        Organization org = new Organization("org", "des", address);
        hibernateOrganizationDao.store(org);
    }

    @Test
    public void testDelete() throws Exception {
        Organization org = hibernateOrganizationDao.findById(1);
        Assert.assertEquals(org.getId(), 1);
    }

    @Test
    public void testFindById() throws Exception {

    }

    @Test
    public void testFindAll() throws Exception {

    }
}