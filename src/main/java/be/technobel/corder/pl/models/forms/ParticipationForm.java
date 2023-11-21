package be.technobel.corder.pl.models.forms;

import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Products;

import java.sql.Blob;
import java.time.LocalDate;

public record ParticipationForm(
        String firstName,
        String lastName,
        String email,
        String pictureName,
        String pictureType,
        byte[] blob,
        Products productType,
        String street,
        String city,
        String postCode,

        Long satisfaction
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
        participation.setProductType(productType);
        participation.setSatisfaction(satisfaction);
        return participation;
    }
}
