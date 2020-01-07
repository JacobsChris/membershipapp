package com.bae.rest;

import com.bae.persistence.domain.Gathering;
import com.bae.persistence.domain.Member;
import com.bae.persistence.repo.GatheringRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
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
public class GatheringControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private GatheringRepo repo;
    private ObjectMapper mapper = new ObjectMapper();
    private long id;
    private Gathering testGathering;
    private Gathering testGatheringWithID;


    @Before
    public void init() {
        this.repo.deleteAll();
        this.testGathering = new Gathering("Cardiff");
        List<Member> memdude = Lists.emptyList();
        this.testGathering.setMembers(memdude);
        this.testGatheringWithID = this.repo.save(this.testGathering);
        this.id = this.testGatheringWithID.getId();


    }

    @Test
    public void testAddMember() throws Exception {
        String result = this.mock
                .perform(request(HttpMethod.POST, "/gathering/create").contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(testGathering)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertEquals(this.mapper.writeValueAsString(testGatheringWithID), result);
    }

    @Test
    public void testDeleteMember() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/gathering/delete/" + this.id)).andExpect(status().isOk());
    }

    @Test
    public void testGetAllGathering() throws Exception {
        List<Gathering> gatheringList = new ArrayList<>();
        gatheringList.add(this.testGatheringWithID);
        String content = this.mock.perform(
                request(HttpMethod.GET, "/gathering/getAll")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


        assertEquals(this.mapper.writeValueAsString(gatheringList), content);
    }


    @Test
    public void testUpdateMember() throws Exception {
        Gathering updatedGathering = testGathering;
        updatedGathering.setId(this.id);//1L

        String result = this.mock
                .perform(request(HttpMethod.PUT, "/gathering/update/" + this.id).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(updatedGathering)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertEquals(this.mapper.writeValueAsString(updatedGathering), result);

    }

}