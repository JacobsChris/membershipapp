package com.bae.rest;

import com.bae.persistence.domain.Member;
import com.bae.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memberapp")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public List<Member> getAllMember() {
        return memberService.getAllMember();
    }

    @PostMapping("/trainer")
    public Member addNewMember(@RequestBody Member member) {
        return memberService.addNewMember(member);
    }

    @PutMapping("/trainer")
    public Member updateTrainer(@RequestBody Member member) {
        return memberService.updateMember(member);
    }

    @DeleteMapping("/trainer/{id}")
    public String deleteTrainer(@PathVariable(value = "id") Long id) {
        return memberService.deleteMember(id);
    }
}
