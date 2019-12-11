package com.bae.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String location;
    private int groupOfficers;

    public Group(String location, int groupOfficers) {
        this.location = location;
        this.groupOfficers = groupOfficers;
    }

}


