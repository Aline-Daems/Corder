package be.technobel.corder.pl.models.dtos;

import be.technobel.corder.dal.models.Participation;

public record SatisfactionCommentDTO(
        String satisfactionComment
) {
    public static SatisfactionCommentDTO fromEntity(Participation participation) {
        return new SatisfactionCommentDTO(
                participation.getSatisfactionComment()
        );
    }
}
