package edu.sjsu.cmpe275.lab2.dao;

import edu.sjsu.cmpe275.lab2.domain.Organization;

import java.util.List;

/**
 * Created by jianxin on 10/30/15.
 */
public interface OrganizationDao {
    public void store(Organization organization);
    public void delete(long orgId);
    public Organization findById(long orgId);
    public List<Organization> findAll();
}
