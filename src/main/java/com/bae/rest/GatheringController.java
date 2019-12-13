package com.bae.rest;

import com.bae.persistence.domain.Gathering;
import com.bae.service.GatheringService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/gathering")
public class GatheringController {

    private GatheringService gatheringService;

    public GatheringController(GatheringService gatheringService) {
        this.gatheringService = gatheringService;
    }

    @GetMapping("/getAll")
    public List<Gathering> getAllGathering() {
        return gatheringService.getAllGathering();
    }

    @PostMapping("/create")
    public Gathering addNewGathering(@RequestBody Gathering gathering) {
        return gatheringService.addNewGathering(gathering);
    }

    @PutMapping("/update")
    public Gathering updateGathering(@RequestBody Gathering gathering) {
        return gatheringService.updateGathering(gathering);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGathering(@PathVariable(value = "id") Long id) {
        return gatheringService.deleteGathering(id);
    }
}

