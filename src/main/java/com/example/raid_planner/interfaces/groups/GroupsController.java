package com.example.raid_planner.interfaces.groups;

import com.example.raid_planner.domain.groups.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/events")
@RequiredArgsConstructor
public class GroupsController {

    private final GroupService groupService;

    @PostMapping(path = "/{uuid}/groups")
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse postNewGroup(@PathVariable UUID uuid, @RequestBody GroupRequest request) {
        return GroupResponse.from(groupService.createGroupOfType(uuid, request.getGroupType()));
    }
}
