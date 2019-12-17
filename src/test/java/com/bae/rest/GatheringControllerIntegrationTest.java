package com.bae.rest;

import com.bae.persistence.domain.Gathering;
import com.bae.persistence.domain.Member;
import com.bae.persistence.repo.GatheringRepo;
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
import java.util.Collection;
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
        Collection<Member> members = null;
        this.testGathering = new Gathering("Cardiff", members);
        this.testGatheringWithID = this.repo.save(this.testGathering);
        this.id = this.testGatheringWithID.getId();
    }

    @Test
    public void testAddMember() throws Exception {
        String result = this.mock
                .perform(request(HttpMethod.POST, "/create").contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(testGathering)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertEquals(this.mapper.writeValueAsString(testGatheringWithID), result);
    }

    @Test
    public void testDeleteMember() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/delete/" + this.id)).andExpect(status().isOk());
    }

    @Test
    public void testGetAllGathering() throws Exception {
        List<Gathering> gatheringList = new ArrayList<>();
        gatheringList.add(this.testGatheringWithID);
        String content = this.mock.perform(request(HttpMethod.GET, "/getAll").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(this.mapper.writeValueAsString(gatheringList), content);
    }

    @Test
    public void testUpdateMember() throws Exception {
        Collection<Member> members = null;
        Gathering newGathering = new Gathering("Cardiff", members);
        Gathering updatedGathering = newGathering;
        updatedGathering.setId(this.id);

        String result = this.mock
                .perform(request(HttpMethod.PUT, "/update/" + this.id).accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(newGathering)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(this.mapper.writeValueAsString(updatedGathering), result);

    }

}