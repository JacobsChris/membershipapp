package com.bae.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String location;

    public Group() {
    }

    public Group(String location, int groupOfficers) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return getId() == group.getId() &&
                getLocation().equals(group.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLocation());
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", location='" + location + '\'' +
                '}';
    }
}


