package com.bae.service;

import com.bae.gathering.exceptions.GatheringNotFoundException;
import com.bae.persistence.domain.Gathering;
import com.bae.persistence.repo.GatheringRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatheringService {

    private GatheringRepo gatheringRepo;

    public GatheringService(GatheringRepo gatheringRepo) {
        this.gatheringRepo = gatheringRepo;
    }

    public List<Gathering> getAllGathering() {
        return gatheringRepo.findAll();
    }

    public Gathering addNewGathering(Gathering gathering) {
        return gatheringRepo.save(gathering);
    }

    public Gathering updateGathering(Gathering gathering, Long id) {
        Gathering toUpdate = findGatheringByID(id);
        toUpdate.setLocation(gathering.getLocation());
        toUpdate.setMembers(gathering.getMembers());
        return this.gatheringRepo.save(toUpdate);
    }

    public String deleteGathering(Long id) {
        gatheringRepo.deleteById(id);
        return "Group successfully deleted";

    }

    public Gathering findGatheringByID(long id) {
        return this.gatheringRepo.findById(id).orElseThrow((
                () -> new GatheringNotFoundException()));
    }
}
