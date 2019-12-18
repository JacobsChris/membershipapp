package com.bae.service;

import com.bae.persistence.domain.Gathering;
import com.bae.persistence.domain.Member;
import com.bae.persistence.repo.GatheringRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class GatheringServiceTest {

    final long id = 1L;
    @InjectMocks
    private GatheringService service;
    @Mock
    private GatheringRepo repo;
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
    public void getAllGathering() {
        when(repo.findAll()).thenReturn(this.gatheringList);
        assertFalse("Controller has found no groups", this.service.getAllGathering().isEmpty());
        verify(repo, times(1)).findAll();


    }

    @Test
    public void addNewGathering() {
        when(this.repo.save(testGathering)).thenReturn(testGatheringWithID);
        assertEquals(this.testGatheringWithID, this.service.addNewGathering(this.testGathering));

        verify(this.repo, times(1)).save(this.testGathering);
    }

    @Test
    public void updateGathering() {
        Gathering newGathering = new Gathering("Cardiff", members);
        Gathering updatedGathering = new Gathering(newGathering.getLocation(), newGathering.getMembers());
        updatedGathering.setId(this.id);
        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testGatheringWithID));
        when(this.repo.save(updatedGathering)).thenReturn(updatedGathering);
        assertEquals(updatedGathering, this.service.updateGathering(newGathering, this.id));
        verify(this.repo, times(1)).findById(this.id);
        verify(this.repo, times(1)).save(updatedGathering);

    }


    @Test
    public void deleteGathering() {
        when(this.repo.existsById(id)).thenReturn(true, false);
        this.service.deleteGathering(id);
        verify(this.repo, times(1)).deleteById(id);
    }

    @Test
    public void findGatheringByID() {
        when(this.repo.findById(this.id)).thenReturn(Optional.of(this.testGatheringWithID));
        assertEquals(this.testGatheringWithID, this.service.findGatheringByID(this.id));
        verify(this.repo, times(1)).findById(this.id);
    }
}