package com.bae.rest;

import com.bae.member.exceptions.NonUniqueNameCombinationException;
import com.bae.persistence.domain.Member;
import com.bae.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/getAll")
    public List<Member> getAllMember() {
        return memberService.getAllMember();
    }

    @GetMapping("/get/{id}")
    public Member getMember(@PathVariable Long id) {
        return this.memberService.findMemberByID(id);
    }

    @PostMapping("/create")
    public Member addNewMember(@RequestBody Member member) throws NonUniqueNameCombinationException {
        return memberService.addNewMember(member);
    }

    @PostMapping("/create/{id}")
    public Member addNewMember(@RequestBody Member member, @PathVariable Long id) throws NonUniqueNameCombinationException {
        return memberService.addNewMember(member, id);
    }


    @PutMapping("/update/{memberId}")
    public Member updateMember(@PathVariable("memberId") Long id, @RequestBody Member member) {
        return this.memberService.updateMember(member, id);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteMember(@PathVariable(value = "id") Long id) {
        return memberService.deleteMember(id);
    }
}
