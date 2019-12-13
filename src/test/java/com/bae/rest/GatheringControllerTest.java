package com.bae.rest;

import com.bae.persistence.domain.Gathering;
import com.bae.persistence.domain.Member;
import com.bae.service.GatheringService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
public class GatheringControllerTest {
    final long id = 1L;
    @InjectMocks
    private GatheringController controller;
    @Mock
    private GatheringService service;
    private List<Gathering> gatheringList;
    private Gathering testGathering;
    private Gathering testGatheringWithID;
    private Collection<Member> members;

    @Before
    public void init() {
        this.gatheringList = new ArrayList<>();
        this.gatheringList.add(testGathering);
        this.testGathering = new Gathering("Cardiff", members);
        this.testGatheringWithID = new Gathering(testGathering.getLocation(), testGathering.getMembers());
        this.testGatheringWithID.setId(id);
    }

    @Test
    public void addNewGatheringTest() {
        when(this.service.addNewGathering(testGathering)).thenReturn(testGatheringWithID);

        assertEquals(this.testGatheringWithID, this.controller.addNewGathering(testGathering));

        verify(this.service, times(1)).addNewGathering(this.testGathering);

    }

    @Test
    public void deleteGatheringTest() {
        this.controller.deleteGathering(id);

        verify(this.service, times(1)).deleteGathering(id);
    }

    @Test
    public void findGatheringByIDTest() {
        when(this.service.findGatheringByID(this.id)).thenReturn(this.testGatheringWithID);

    }

    @Test
    public void getAllGatheringsTest() {
        when(service.getAllGathering()).thenReturn(this.gatheringList);

        assertFalse("Controller has found no ducks", this.controller.getAllGathering().isEmpty());

        verify(service, times(1)).getAllGathering();
    }

    @Test
    public void updateGatheringTest() {
        Gathering newGathering = new Gathering("Manchester", members);
        Gathering updatedGathering = new Gathering(newGathering.getLocation(), newGathering.getMembers());
        updatedGathering.setId(this.id);

        when(this.service.updateGathering(newGathering, this.id)).thenReturn(updatedGathering);

        assertEquals(updatedGathering, this.controller.updateGathering(newGathering, this.id));

        verify(this.service, times(1)).updateGathering(newGathering, this.id);
    }
}

