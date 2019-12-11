package com.bae.service;

import com.bae.persistence.domain.Member;
import com.bae.persistence.repo.MemberRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private MemberRepo memberRepo;

    public MemberService(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    public List<Member> getAllMember() {
        return memberRepo.findAll();
    }

    public Member addNewMember(Member member) {
        return memberRepo.save(member);
    }

    public Member updateMember(Member member) {
        return memberRepo.save(member);
    }

    public String deleteMember(Long id) {
        memberRepo.deleteById(id);
        return "Member successfully deleted.";
    }
}
