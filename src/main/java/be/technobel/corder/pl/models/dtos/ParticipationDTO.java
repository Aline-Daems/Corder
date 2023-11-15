package be.technobel.corder.pl.models.dtos;

import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;

import java.sql.Blob;
import java.time.LocalDate;

public record ParticipationDTO(
        Long id,
        LocalDate participationDate,
        String participantFirstName,
        String participantLastName,
        String participantEmail,
        Address participantAddress,
        boolean validated,
        boolean shipped,
        String pictureName,
        String pictureType,
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
                participation.isValidated(),
                participation.isShipped(),
                participation.getPictureName(),
                participation.getPictureType(),
                participation.getBlob()
        );
    }
}
