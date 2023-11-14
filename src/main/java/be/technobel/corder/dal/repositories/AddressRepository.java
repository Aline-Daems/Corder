package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
