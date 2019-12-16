package com.bae.service;

import com.bae.member.exceptions.MemberNotFoundException;
import com.bae.persistence.domain.Member;
import com.bae.persistence.repo.MemberRepo;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceIntegrationTest {

    @Autowired
    private MemberService service;

    @Autowired
    private MemberRepo repo;

    private Member testMember;

    private Member testMemberWithID;

    @Before
    public void init() {
        this.testMember = new Member("Katie", "Eveleigh", true, true, true, true, true);
        this.testMemberWithID = testMember; // Member(newMember.getFirstName(), newMember.getLastName(), newMember.isPaidMembership(), newMember.isHasGloves(), newMember.isHasShoes(), newMember.isHasClothes(), newMember.isGatheringOfficer());

        this.repo.deleteAll();

        this.testMemberWithID = this.repo.save(this.testMember);
    }

    @Test
    public void testAddMember() {
        assertEquals(this.testMemberWithID, this.service.addNewMember(testMember));
    }

    @Test
    public void testDeleteMember() {
        assertEquals(this.service.deleteMember((this.testMemberWithID.getId())), "Member successfully deleted.");
        try {
            this.service.findMemberByID(this.testMemberWithID.getId());
        } catch (MemberNotFoundException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void testFindMember() {
        assertEquals(this.service.findMemberByID(this.testMemberWithID.getId()), testMemberWithID);
    }

    @Test
    public void testFindMembers() {
        assertEquals(this.service.getAllMember(), Arrays.asList(new Member[]{this.testMemberWithID}));
    }

    @Test
    public void testUpdateMember() {
        Member newMember = new Member("Katie", "Eveleigh", true, true, true, true, true);
        newMember.setId(this.testMemberWithID.getId());
        Member updatedMember = new Member(newMember.getFirstName(), newMember.getLastName(), newMember.isPaidMembership(), newMember.isHasGloves(), newMember.isHasShoes(), newMember.isHasClothes(), newMember.isGatheringOfficer());
        updatedMember.setId(this.testMemberWithID.getId());
        assertEquals(this.service.updateMember(newMember, this.testMemberWithID.getId()), updatedMember);
    }


}