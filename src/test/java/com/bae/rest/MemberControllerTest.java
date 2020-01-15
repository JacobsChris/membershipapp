package com.bae.rest;

import com.bae.persistence.domain.Member;
import com.bae.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class MemberControllerTest {

    @InjectMocks
    private MemberController controller;

    @Mock
    private MemberService service;
    private List<Member> memberList;
    private Member testMember;
    private Member testMemberWithID;
    final long id = 1L;

    @Before
    public void init() {
        this.memberList = new ArrayList<>();
        this.testMember = new Member("Katie", "Eveleigh", true, true, true, true, true);
        this.testMemberWithID = new Member(testMember.getFirstName(), testMember.getLastName(), testMember.isPaidMembership(), testMember.isHasGloves(), testMember.isHasShoes(), testMember.isHasClothes(), testMember.isIsGatheringOfficer());
        this.testMemberWithID.setId(id);
        this.memberList.add(testMember);

    }

    @Test
    public void addMemberTest() {
        when(this.service.addNewMember(testMember)).thenReturn(testMemberWithID);

        assertEquals(this.testMemberWithID, this.controller.addNewMember(testMember));

        verify(this.service, times(1)).addNewMember(this.testMember);
    }

    @Test
    public void addMemberTestWitID() {
        when(this.service.addNewMember(testMember, testMemberWithID.getId())).thenReturn(testMemberWithID);

        assertEquals(this.testMemberWithID, this.controller.addNewMember(testMember, testMemberWithID.getId()));

        verify(this.service, times(1)).addNewMember(this.testMember, testMemberWithID.getId());
    }

    @Test
    public void deleteMemberTest() {
        this.controller.deleteMember(id);

        verify(this.service, times(1)).deleteMember(id);
    }

    @Test
    public void getAllMembersTest() {
        when(service.getAllMember()).thenReturn(this.memberList);

        assertFalse(this.controller.getAllMember().isEmpty(), "Controller has found no members.");

    }

    @Test
    public void getMemberWithID() {
        when(service.findMemberByID(testMemberWithID.getId())).thenReturn(this.testMemberWithID);

        assertFalse(this.controller.getMember(testMemberWithID.getId()).equals(null), "Controller has found no members.");


    }

    @Test
    public void updateMemberTest() {
        Member newMember = new Member("Katherine", "Eveleigh", true, true, true, true, true);
        Member updatedMember = new Member(newMember.getFirstName(), newMember.getLastName(), newMember.isPaidMembership(), newMember.isHasGloves(), newMember.isHasShoes(), newMember.isHasClothes(), newMember.isIsGatheringOfficer());
        updatedMember.setId(this.id);

        when(this.service.updateMember(newMember, this.id)).thenReturn((updatedMember));

        assertEquals(updatedMember, this.controller.updateMember(this.id, newMember));

        verify(this.service, times(1)).updateMember(newMember, this.id);

    }
}