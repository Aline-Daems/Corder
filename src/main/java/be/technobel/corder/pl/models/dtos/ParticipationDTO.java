package be.technobel.corder.pl.models.dtos;

import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Status;

import java.sql.Blob;
import java.time.LocalDate;

public record ParticipationDTO(
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
        byte[] blob
) {
    public static ParticipationDTO fromEntity(Participation participation) {
        return new ParticipationDTO(
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
                participation.getBlob()
        );
    }
}
