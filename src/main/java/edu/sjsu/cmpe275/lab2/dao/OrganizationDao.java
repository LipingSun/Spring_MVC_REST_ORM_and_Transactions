package edu.sjsu.cmpe275.lab2.dao;

import edu.sjsu.cmpe275.lab2.domain.Organization;

import java.util.List;

public interface OrganizationDao {
    void store(Organization organization);

    void delete(long orgId);

    Organization findById(long orgId);

    Organization update(Organization org);

    List<Organization> findAll();
}
