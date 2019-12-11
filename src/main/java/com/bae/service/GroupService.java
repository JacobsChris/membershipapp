package com.bae.service;

import com.bae.persistence.domain.Group;
import com.bae.persistence.repo.GroupRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private GroupRepo groupRepo;

    public GroupService(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public List<Group> getAllGroup() {
        return groupRepo.findAll();
    }

    public Group addNewGroup(Group group) {
        return groupRepo.save(group);
    }

    public Group updateGroup(Group group) {
        return groupRepo.save(group);
    }

    public String deleteGroup(Long id) {
        groupRepo.deleteById(id);
        return "Trainer successfully deleted";

    }
}
