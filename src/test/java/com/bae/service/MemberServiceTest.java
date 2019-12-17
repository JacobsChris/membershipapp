package com.bae.service;

import com.bae.persistence.domain.Member;
import com.bae.persistence.repo.MemberRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class MemberServiceTest {

    final long id = 1L;
    @InjectMocks
    private MemberService service;
    @Mock
    private MemberRepo repo;
    private List<Member> memberList;
    private Member testMember;
    private Member testMemberWithID;

    @Before
    public void init() {
        this.memberList = new ArrayList<>();
        this.memberList.add(testMember);
        this.testMember = new Member("Katie", "Eveleigh", true, true, true, true, true);
        this.testMemberWithID = new Member(testMember.getFirstName(), testMember.getLastName(), testMember.isPaidMembership(), testMember.isHasGloves(), testMember.isHasShoes(), testMember.isHasClothes(), testMember.isIsGatheringOfficer());
        this.testMemberWithID.setId(id);

    }


    @Test
    public void addNewMemberTest() {
        when(this.repo.save(testMember)).thenReturn((testMemberWithID));

        assertEquals(this.testMemberWithID, this.service.addNewMember(testMember));

        verify(this.repo, times(1)).save(this.testMember);
    }

    @Test
    public void deleteMemberTest() {
        this.service.deleteMember(id);
        verify(this.repo, times(1)).deleteById(id);
    }

    @Test
    public void findMemberByID() {
        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testMemberWithID));
        assertEquals(this.testMemberWithID, this.service.findMemberByID((this.id)));
        verify(this.repo, times(1)).findById(this.id);
    }

    @Test
    public void getAllMembersTest() {
        when(repo.findAll()).thenReturn(this.memberList);
        assertFalse("Controller has found no members", this.service.getAllMember().isEmpty());
        verify(repo, times(1)).findAll();
    }

    @Test
    public void updateMembersTest() {
        Member newMember = new Member("Katie", "Eveleigh", true, true, true, true, true);
        Member updatedMember = newMember; // Member(newMember.getFirstName(), newMember.getLastName(), newMember.isPaidMembership(), newMember.isHasGloves(), newMember.isHasShoes(), newMember.isHasClothes(), newMember.isGatheringOfficer());
        updatedMember.setId(this.id);

        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testMemberWithID));
        when(this.repo.save(updatedMember)).thenReturn(updatedMember);

        assertEquals(updatedMember, this.service.updateMember(newMember, this.id));

        verify(this.repo, times(1)).findById(this.id);
        verify(this.repo, times(1)).save(updatedMember);
    }
}