package be.technobel.corder.pl.models.dtos;

import be.technobel.corder.dal.models.Participation;

public record ParticipationIdDTO(
        Long id
) {
    public static ParticipationIdDTO fromEntity(Participation participation) {
        return new ParticipationIdDTO(
                participation.getId()
        );
    }
}
