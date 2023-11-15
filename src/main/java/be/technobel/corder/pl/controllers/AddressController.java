package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.AddressService;
import be.technobel.corder.dal.models.Address;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/Address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Address>> getAllAddress() {
        return ResponseEntity.ok(addressService.findAll().stream().map(Address::fromEntity).toList());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void create(@RequestBody Address address) {
        addressService.create(address);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        return ResponseEntity.ok(Address.fromEntity(addressService.findById(id).orElseThrow(() -> new NotFoundException("Pas trouvé"))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@PathVariable Long id, @RequestBody Address address) {
        addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }
}
