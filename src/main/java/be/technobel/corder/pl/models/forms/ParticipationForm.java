package be.technobel.corder.pl.models.forms;

import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;

import java.sql.Blob;
import java.time.LocalDate;

public record ParticipationForm(
        String firstName,
        String lastName,
        String email,
        String pictureName,
        String pictureType,
        byte[] blob,
        String street,
        String city,
        String postCode
) {
    public Participation toEntity() {
        Participation participation = new Participation();
        Address address = new Address();

        address.setStreet(street);
        address.setCity(city);
        address.setPostCode(postCode);
        participation.setParticipantFirstName(firstName);
        participation.setParticipantLastName(lastName);
        participation.setParticipantEmail(email);
        participation.setParticipantAddress(address);
        participation.setPictureName(pictureName);
        participation.setPictureType(pictureType);
        participation.setParticipationDate(LocalDate.now());
        participation.setBlob(blob);
        return participation;
    }
}
