package com.bae.persistence.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private boolean paidMembership;
    private boolean hasGloves;
    private boolean hasShoes;
    private boolean hasClothes;
    private boolean isGatheringOfficer;

    public Member() {
    }

    public Member(String firstName, String lastName, boolean paidMembership, boolean hasGloves, boolean hasShoes, boolean hasClothes, boolean isGatheringOfficer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.paidMembership = paidMembership;
        this.hasGloves = hasGloves;
        this.hasShoes = hasShoes;
        this.hasClothes = hasClothes;
        this.isGatheringOfficer = isGatheringOfficer;
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

    public boolean isPaidMembership() {
        return paidMembership;
    }

    public void setPaidMembership(boolean paidMembership) {
        this.paidMembership = paidMembership;
    }

    public boolean isHasGloves() {
        return hasGloves;
    }

    public void setHasGloves(boolean hasGloves) {
        this.hasGloves = hasGloves;
    }

    public boolean isHasShoes() {
        return hasShoes;
    }

    public void setHasShoes(boolean hasShoes) {
        this.hasShoes = hasShoes;
    }

    public boolean isHasClothes() {
        return hasClothes;
    }

    public void setHasClothes(boolean hasClothes) {
        this.hasClothes = hasClothes;
    }

    public boolean isIsGatheringOfficer() {
        return isGatheringOfficer;
    }

    public void setIsGatheringOfficer(boolean isGatheringOfficer) {
        this.isGatheringOfficer = isGatheringOfficer;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", paidMembership=" + paidMembership +
                ", hasGloves=" + hasGloves +
                ", hasShoes=" + hasShoes +
                ", hasClothes=" + hasClothes +
                ", isGatheringOfficer=" + isGatheringOfficer +
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
