package com.bae.service;

import com.bae.member.exceptions.*;
import com.bae.persistence.domain.Member;
import com.bae.persistence.repo.MemberRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private MemberRepo memberRepo;
    private GatheringService gatheringService;

    public MemberService(MemberRepo memberRepo, GatheringService gatheringService) {
        this.memberRepo = memberRepo;
        this.gatheringService = gatheringService;
    }

    public List<Member> getAllMember() {
        return memberRepo.findAll();
    }


    public Member addNewMember(Member member) {
        checkNameLength(member);
        return memberRepo.save(member);
    }

    public Member addNewMember(Member member, long id) {
        checkNameLength(member);
        memberRepo.save(member);
        gatheringService.addMember(member, id);

        return null;
    }

    public Member findMemberByID(Long id) {
        return this.memberRepo.findById(id).orElseThrow(
                MemberNotFoundException::new);

    }


    public Member updateMember(Member member, Long id) {
        checkNameLength(member);
        Member toUpdate = findMemberByID(id);
        toUpdate.setFirstName(member.getFirstName());
        toUpdate.setLastName(member.getLastName());
        toUpdate.setPaidMembership(member.isPaidMembership());
        toUpdate.setHasClothes(member.isHasClothes());
        toUpdate.setIsGatheringOfficer(member.isIsGatheringOfficer());
        toUpdate.setHasGloves(member.isHasGloves());
        toUpdate.setHasShoes(member.isHasShoes());

        return memberRepo.save(toUpdate);
    }

    private void checkNameLength(Member member) {
        int firstNameLength = member.getFirstName().length();
        int secondNameLength = member.getLastName().length();
        if (firstNameLength < 2) throw new MemberFirstNameTooShortException();
        if (secondNameLength < 2) throw new MemberSecondNameTooShortException();
        if (firstNameLength > 25) throw new MemberFirstNameTooLongException();
        if (secondNameLength > 25) throw new MemberSecondNameTooLongException();
    }

    public String deleteMember(Long id) {
        memberRepo.deleteById(id);
        return "Member successfully deleted.";
    }
}
