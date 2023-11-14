package be.technobel.corder.bl.impl;

import be.technobel.corder.bl.AddressService;
import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.repositories.AddressRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void create(Address address) {
        Address entity = new Address();
        entity.setCity(address.getCity());
        entity.setStreet(address.getStreet());
        entity.setPostCode(address.getPostCode());
        addressRepository.save(entity);
    }


    @Override
    public void update(Address address) {
       addressRepository.save(address);
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(()-> new NotFoundException("Adresse non trouvée"));
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
}
