package edu.sjsu.cmpe275.lab2.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ORGANIZATION")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "org")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Person> persons;

    public Organization() {
    }

    public Organization(String name) {
        this.name = name;
    }

    public Organization(String name, String description, Address address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Address getAddress() {
        return address;
    }

    @JsonProperty(value = "address")
    public String getAddressString() {
        if (address != null) {
            return address.getStreet() + ", " + address.getCity() + ", " + address.getState() + ", " + address.getZip();
        } else {
            return null;
        }
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}