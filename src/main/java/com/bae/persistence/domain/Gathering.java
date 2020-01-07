package com.bae.persistence.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "GroupList")
public class Gathering {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String location;

    @OneToMany
    @JoinColumn(name = "gath_id")
    private List<Member> members;

    public Gathering() {
    }

    public Gathering(String location) {
        this.location = location;
    }

    public Gathering(String location, List<Member> members) {
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

    public List<Member> getMembers() {
        return this.members;
    }

    public void setMembers(List<Member> members) {
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