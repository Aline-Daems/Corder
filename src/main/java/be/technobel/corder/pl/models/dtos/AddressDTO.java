package be.technobel.corder.pl.models.dtos;

import be.technobel.corder.dal.models.Address;

public record AddressDTO (
     Long id,
     String street,
     String city,
     String postCode)

{public static AddressDTO fromEntity(Address entity) {
    return new AddressDTO(entity.getId(), entity.getCity(), entity.getStreet(), entity.getPostCode());
}}


