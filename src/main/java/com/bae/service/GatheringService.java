package com.bae.service;

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

    public Gathering updateGathering(Gathering gathering) {
        return gatheringRepo.save(gathering);
    }

    public String deleteGathering(Long id) {
        gatheringRepo.deleteById(id);
        return "Trainer successfully deleted";

    }
}
