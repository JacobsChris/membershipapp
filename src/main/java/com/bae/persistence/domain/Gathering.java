package com.bae.persistence.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "GroupList")
public class Gathering {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String location;

    @OneToMany(mappedBy = "gatheringID")
    private Collection<Member> members;

    public Gathering() {
    }

    public Gathering(String location) {
        this.location = location;
    }

    public Gathering(String location, Collection<Member> members) {
        this.location = location;
        this.members = members;

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

    public Collection<Member> getMembers() {
        return this.members;
    }

    public void setMembers(Collection<Member> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gathering gathering = (Gathering) o;
        return getId() == gathering.getId() &&
                getLocation().equals(gathering.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLocation(), getMembers());
    }

    @Override
    public String toString() {

        return "Gathering{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", members=" + members +
                '}';
    }
}