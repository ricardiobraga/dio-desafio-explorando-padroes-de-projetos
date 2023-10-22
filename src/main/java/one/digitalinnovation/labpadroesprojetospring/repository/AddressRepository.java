package one.digitalinnovation.labpadroesprojetospring.repository;

import org.springframework.data.repository.CrudRepository;

import one.digitalinnovation.labpadroesprojetospring.model.Address;

public interface AddressRepository extends CrudRepository<Address, String> {
    
}
