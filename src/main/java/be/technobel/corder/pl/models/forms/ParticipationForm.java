package be.technobel.corder.pl.models.forms;

import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Status;

import java.time.LocalDate;

public record ParticipationForm(
        String firstName,
        String lastName,
        String email,
        String productType,
        Status status,
        String street,
        String city,
        int postCode,
        int satisfaction,
        String satisfactionComment,
        boolean acceptNewsletter
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
        participation.setStatus(status);
        participation.setParticipationDate(LocalDate.now());
        participation.setProductType(productType.toLowerCase());
        participation.setSatisfaction(satisfaction);
        participation.setSatisfactionComment(satisfactionComment);
        participation.setAcceptNewsletter(acceptNewsletter);
        return participation;
    }
}
