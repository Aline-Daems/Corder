package be.technobel.corder.bl;

import be.technobel.corder.dal.models.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    void create(Address address);
    void update(Long id, Address address);
    Address findById(Long id);
    List<Address> findAll();
    void delete(Long id);
}
