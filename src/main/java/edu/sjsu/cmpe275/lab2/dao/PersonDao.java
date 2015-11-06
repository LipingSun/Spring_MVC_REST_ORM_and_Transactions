package edu.sjsu.cmpe275.lab2.dao;

import edu.sjsu.cmpe275.lab2.domain.Person;

import java.util.List;

/**
 * Created by jianxin on 10/30/15.
 */
public interface PersonDao {
    public void store(Person person);
    public void delete(long personId);
    public Person findById(long personId);
    public List<Person> findAll();
}
