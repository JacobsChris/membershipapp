package com.bae.service;

import com.bae.gathering.exceptions.GatheringNotFoundException;
import com.bae.persistence.domain.Gathering;
import com.bae.persistence.domain.Member;
import com.bae.persistence.repo.GatheringRepo;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GatheringServiceIntegrationTest {

    @Autowired
    private GatheringService service;

    @Autowired
    private GatheringRepo repo;

    private Gathering testGathering;

    private Gathering testGatheringWithID;

    @Before
    public void init() {
        this.testGathering = new Gathering("Cardiff");
        this.repo.deleteAll();
        this.testGatheringWithID = this.repo.save(this.testGathering);

    }

    @Test
    public void testAddGathering() {
        assertEquals(this.testGatheringWithID, this.service.addNewGathering(testGathering));

    }

    @Test
    public void testDeleteGathering() {
        assertEquals("Group successfully deleted", this.service.deleteGathering((this.testGatheringWithID.getId())));
        String x = "sucess";
        try {
            this.service.findGatheringByID(this.testGatheringWithID.getId());
        } catch (GatheringNotFoundException e) {
            x = "Member not found exception";
        } catch (Exception e) {
            x = "error: " + e;
        }

        assertEquals("Member not found exception", x);
    }


    @Test
    public void testFindGatheringByID() {
        assertThat(this.service.findGatheringByID(this.testGatheringWithID.getId())).isEqualTo(this.testGatheringWithID);

    }

    @Test
    public void testGetAllGatherings() {
        assertThat(this.service.getAllGathering()).isEqualTo(Arrays.asList(new Gathering[]{this.testGatheringWithID}));
    }

    @Test
    public void testUpdateGathering() {
        Collection<Member> members = null;
        Gathering newGathering = new Gathering("Cardiff");
        Gathering updatedGathering = new Gathering(newGathering.getLocation(), newGathering.getMembers());
        updatedGathering.setId(this.testGatheringWithID.getId());
        assertThat(this.service.updateGathering(newGathering, this.testGatheringWithID.getId())).isEqualTo(updatedGathering);


    }
}