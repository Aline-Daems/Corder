package be.technobel.corder.pl.models.forms;

import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ParticipationForm(
        @NotBlank(message = "First name cannot be blank") String firstName,
        @NotBlank(message = "Last name cannot be blank") String lastName,
        @NotBlank(message = "Email cannot be blank") String email,
        @NotBlank(message = "Product type cannot be blank") String productType,
        @NotNull(message = "Status cannot be blank") Status status,
        @NotBlank(message = "Street cannot be blank") String street,
        @NotBlank(message = "City cannot be blank") String city,
        @NotBlank(message = "satisfactionComment cannot be blank") String satisfactionComment,
        @Min(value = 0, message = "Post code should not be less than 0")
        @Max(value = 99999, message = "Post code should not be greater than 99999") int postCode,
        @Min(value = 1, message = "Satisfaction should not be less than 0")
        @Max(value = 3, message = "Satisfaction should not be greater than 10") int satisfaction,
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
