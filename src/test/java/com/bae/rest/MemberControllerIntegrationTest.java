package com.bae.rest;

import com.bae.persistence.domain.Member;
import com.bae.persistence.repo.MemberRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private MemberRepo repo;
    private ObjectMapper mapper = new ObjectMapper();
    private long id;
    private Member testMember;
    private Member testMemberWithID;

    @Before
    public void init() {
        this.repo.deleteAll();
        this.testMember = new Member("Katie", "Eveleigh", true, true, true, true, true);
        this.testMemberWithID = this.repo.save(this.testMember);
        this.id = this.testMemberWithID.getId();
    }

    @Test
    public void testAddMember() throws Exception {
        String result = this.mock
                .perform(request(HttpMethod.POST, "/member/create").contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(testMember)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertEquals(this.mapper.writeValueAsString(testMemberWithID), result);
    }

    @Test
    public void testDeleteMember() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/member/delete/" + this.id)).andExpect(status().isOk());
    }

    @Test
    public void testGetAllMembers() throws Exception {
        List<Member> memberList = new ArrayList<>();
        memberList.add(this.testMemberWithID);
        String content = this.mock.perform(request(HttpMethod.GET, "/member/getAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(this.mapper.writeValueAsString(memberList), content);
    }

    @Test
    public void testUpdateMember() throws Exception {
        Member newMember = new Member("Katie", "Eveleigh", true, true, true, true, true);
        Member updatedMember = newMember;
        updatedMember.setId(this.id);

        String result = this.mock
                .perform(request(HttpMethod.PUT, "/member/update/" + this.id).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(newMember)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(this.mapper.writeValueAsString(updatedMember), result);

    }

}