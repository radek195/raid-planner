package com.example.raid_planner.infrastructure.repository.groups;

import com.example.raid_planner.domain.groups.GroupDto;
import com.example.raid_planner.infrastructure.repository.events.EventEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "groups")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupEntity {

    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_sequence"
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    private GroupType groupType;
    private Boolean ready;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

    public static GroupEntity from(GroupDto groupDto) {
        return GroupEntity.builder()
                .id(groupDto.getId())
                .groupType(groupDto.getGroupType())
                .ready(groupDto.getReady())
                .build();
    }

    public GroupDto toDto() {
        return GroupDto.builder()
                .id(this.id)
                .groupType(this.groupType)
                .ready(this.ready)
                .build();
    }
}
