package be.technobel.corder.bl;

import be.technobel.corder.dal.models.Address;

import java.util.List;

public interface AddressService {
    Address create(Address address);
    Address update(Address address);
    Address findById(Long id);
    List<Address> findAll();
    Address delete(Long id);
}
