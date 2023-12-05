package be.technobel.corder.pl.models.dtos;

import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Status;

import java.time.LocalDate;

public record ParticipationNoBlobDTO(
        Long id,
        LocalDate participationDate,
        String participantFirstName,
        String participantLastName,
        String participantEmail,
        Address participantAddress,
        Status status,
        String pictureName,
        String pictureType,
        String productType,
        int satisfaction,
        String satisfactionComment,
        boolean acceptNewsletter
) {
    public static ParticipationNoBlobDTO fromEntity(Participation participation) {
        return new ParticipationNoBlobDTO(
                participation.getId(),
                participation.getParticipationDate(),
                participation.getParticipantFirstName(),
                participation.getParticipantLastName(),
                participation.getParticipantEmail(),
                participation.getParticipantAddress(),
                participation.getStatus(),
                participation.getPictureName(),
                participation.getPictureType(),
                participation.getProductType(),
                participation.getSatisfaction(),
                participation.getSatisfactionComment(),
                participation.isAcceptNewsletter()
        );
    }
}
