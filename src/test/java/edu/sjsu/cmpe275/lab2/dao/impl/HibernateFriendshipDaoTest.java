package edu.sjsu.cmpe275.lab2.dao.impl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Liping on 11/7/15.
 */
public class HibernateFriendshipDaoTest {

    private HibernateFriendshipDao hibernateFriendshipDao;

    public HibernateFriendshipDaoTest() {
        hibernateFriendshipDao = new HibernateFriendshipDao();
    }

    @Test
    public void testCreate() throws Exception {
        hibernateFriendshipDao.create(5, 8);
    }

    @Test
    public void testDelete() throws Exception {
        hibernateFriendshipDao.delete(2, 8);
    }
}