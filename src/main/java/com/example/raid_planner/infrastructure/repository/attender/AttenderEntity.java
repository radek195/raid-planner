package com.example.raid_planner.infrastructure.repository.attender;

import com.example.raid_planner.domain.AttenderDto;
import com.example.raid_planner.infrastructure.repository.groups.GroupEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attender")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttenderEntity {

    @Id
    @SequenceGenerator(
            name = "attender_sequence",
            sequenceName = "attender_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "attender_sequence"
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    private Profession requiredProfession;
    private String actualProfession;
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    public static AttenderEntity from(AttenderDto attenderDto) {
        return AttenderEntity.builder()
                .requiredProfession(attenderDto.getRequiredProfession())
                .actualProfession(attenderDto.getActualProfession())
                .nickname(attenderDto.getNickname())
                .build();
    }

    public AttenderDto toDto() {
        return AttenderDto.builder()
                .id(this.id)
                .requiredProfession(this.requiredProfession)
                .actualProfession(this.actualProfession)
                .nickname(this.nickname)
                .build();
    }
}
