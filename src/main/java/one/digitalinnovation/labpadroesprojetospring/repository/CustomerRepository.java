package one.digitalinnovation.labpadroesprojetospring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import one.digitalinnovation.labpadroesprojetospring.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

}