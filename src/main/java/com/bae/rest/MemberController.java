package com.bae.rest;

import com.bae.persistence.domain.Member;
import com.bae.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/getAll")
    public List<Member> getAllMember() {
        return memberService.getAllMember();
    }

    @PostMapping("/create")
    public Member addNewMember(@RequestBody Member member) {
        return memberService.addNewMember(member);
    }

    @PutMapping("/update")
    public Member updateTrainer(@RequestBody Member member) {
        return memberService.updateMember(member);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTrainer(@PathVariable(value = "id") Long id) {
        return memberService.deleteMember(id);
    }
}
