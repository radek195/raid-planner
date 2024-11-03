package com.example.raid_planner.interfaces.groups;

import com.example.raid_planner.domain.events.EventService;
import com.example.raid_planner.domain.groups.AttenderDto;
import com.example.raid_planner.domain.groups.GroupDto;
import com.example.raid_planner.domain.groups.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/events/{uuid}/groups")
@RequiredArgsConstructor
public class GroupsController {

    private final EventService eventService;
    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse postNewGroup(@PathVariable UUID uuid, @RequestBody GroupRequest request) {
        GroupDto group = groupService.createGroupOfType(uuid, request.getGroupType());
        return GroupResponse.from(group);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable UUID uuid, @PathVariable Long id) {
        groupService.deleteGroup(uuid, id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void postAttender(@PathVariable UUID uuid, @PathVariable Long id, @RequestBody AttenderRequest request) {
        eventService.checkEventByUUIDForOrganizer(uuid);
        groupService.appendAttender(
                id,
                AttenderDto.builder()
                        .requiredProfession(request.getRequiredProfession())
                        .build()
        );
    }
}
