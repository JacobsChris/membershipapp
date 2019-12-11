package com.bae.rest;

import com.bae.persistence.domain.Group;
import com.bae.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/memberapp")
public class GroupController {

    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/member")
    public List<Group> getAllGroup() {
        return groupService.getAllGroup();
    }

    @PostMapping("/trainer")
    public Group addNewGroup(@RequestBody Group group) {
        return groupService.addNewGroup(group);
    }

    @PutMapping("/trainer")
    public Group updateGroup(@RequestBody Group group) {
        return groupService.updateGroup(group);
    }

    @DeleteMapping("/trainer/{id}")
    public String deleteGroup(@PathVariable(value = "id") Long id) {
        return groupService.deleteGroup(id);
    }
}

