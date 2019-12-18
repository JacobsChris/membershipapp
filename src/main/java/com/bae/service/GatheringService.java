package com.bae.service;

import com.bae.gathering.exceptions.GatheringNotFoundException;
import com.bae.gathering.exceptions.LocationTooLongException;
import com.bae.gathering.exceptions.LocationTooShortException;
import com.bae.member.exceptions.MemberFirstNameTooLongException;
import com.bae.member.exceptions.MemberFirstNameTooShortException;
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
        int locationLength = gathering.getLocation().length();
        if (locationLength < 5) throw new LocationTooShortException();
        if (locationLength > 58) throw new LocationTooLongException();
        return gatheringRepo.save(gathering);
    }

    public Gathering updateGathering(Gathering gathering, Long id) {
        checkLocationLength(gathering);

        Gathering toUpdate = findGatheringByID(id);
        toUpdate.setLocation(gathering.getLocation());
        toUpdate.setMembers(gathering.getMembers());
        return this.gatheringRepo.save(toUpdate);
    }

    private void checkLocationLength(Gathering gathering) {
        int locationLength = gathering.getLocation().length();
        if (locationLength < 2) throw new MemberFirstNameTooShortException();
        if (locationLength > 25) throw new MemberFirstNameTooLongException();
    }

    public String deleteGathering(Long id) {
        gatheringRepo.deleteById(id);
        return "Group successfully deleted";

    }

    public Gathering findGatheringByID(long id) {
        return this.gatheringRepo.findById(id).orElseThrow((
                GatheringNotFoundException::new));
    }
}
