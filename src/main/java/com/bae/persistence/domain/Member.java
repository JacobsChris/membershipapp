package com.bae.persistence.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name="members")
    private Group groupID;

    private enumBool paidMembership;
    private enumBool hasGloves;
    private enumBool hasShoes;
    private enumBool hasClothes;
    private enumBool isGroupOfficer;

    public Member() {
    }

    public Member(String firstName, String lastName, enumBool paidMembership, enumBool hasGloves, enumBool hasShoes, enumBool hasClothes, enumBool isGroupOfficer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.paidMembership = paidMembership;
        this.hasGloves = hasGloves;
        this.hasShoes = hasShoes;
        this.hasClothes = hasClothes;
        this.isGroupOfficer = isGroupOfficer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public enumBool isPaidMembership() {
        return paidMembership;
    }

    public void setPaidMembership(enumBool paidMembership) {
        this.paidMembership = paidMembership;
    }

    public enumBool isHasGloves() {
        return hasGloves;
    }

    public void setHasGloves(enumBool hasGloves) {
        this.hasGloves = hasGloves;
    }

    public enumBool isHasShoes() {
        return hasShoes;
    }

    public void setHasShoes(enumBool hasShoes) {
        this.hasShoes = hasShoes;
    }

    public enumBool isHasClothes() {
        return hasClothes;
    }

    public void setHasClothes(enumBool hasClothes) {
        this.hasClothes = hasClothes;
    }


    public enumBool isGroupOfficer() {
        return isGroupOfficer;
    }

    public void setGroupOfficer(enumBool groupOfficer) {
        isGroupOfficer = groupOfficer;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", groupID=" + groupID +
                ", paidMembership=" + paidMembership +
                ", hasGloves=" + hasGloves +
                ", hasShoes=" + hasShoes +
                ", hasClothes=" + hasClothes +
                ", isGroupOfficer=" + isGroupOfficer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return getId() == member.getId() &&
                getFirstName().equals(member.getFirstName()) &&
                getLastName().equals(member.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName());
    }
}
