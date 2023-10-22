package one.digitalinnovation.labpadroesprojetospring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.labpadroesprojetospring.model.Address;
import one.digitalinnovation.labpadroesprojetospring.model.Customer;
import one.digitalinnovation.labpadroesprojetospring.repository.AddressRepository;
import one.digitalinnovation.labpadroesprojetospring.repository.CustomerRepository;



@Service
public class CustomerServive {

	// Singleton: Injetar os componentes do Spring com @Autowired.
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ViaCepService viaCepService;
	
	// Strategy: Implementar os métodos definidos na interface.
	// Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

	
	public Iterable<Customer> findAll() {
		// Buscar todos os Clientes.
		return customerRepository.findAll();
	}

	
	public Customer findById(Long id) {
		// Buscar Cliente por ID.
		Optional<Customer> cliente = customerRepository.findById(id);
		return cliente.get();
	}

	
	public void inserir(Customer customer) {
		saveCustomerWithCep(customer);
	}

	
	public void update(Long id, Customer customer) {
		// Buscar Cliente por ID, caso exista:
		Optional<Customer> customerDb = customerRepository.findById(id);
		if (customerDb.isPresent()) {
			saveCustomerWithCep(customer);
		}
	}

	
	public void delete(Long id) {
		// Deletar Cliente por ID.
		customerRepository.deleteById(id);
	}

	private void saveCustomerWithCep(Customer customer) {
		// Verificar se o Endereco do Cliente já existe (pelo CEP).
		String cep = customer.getAddress().getCep();
		Address address = addressRepository.findById(cep).orElseGet(() -> {
			// Caso não exista, integrar com o ViaCEP e persistir o retorno.
			Address newAddress = viaCepService.findCep(cep);
			addressRepository.save(newAddress);
			return newAddress;
		});
		customer.setaddress(address);
		// Inserir Cliente, vinculando o Endereco (novo ou existente).
		customerRepository.save(customer);
	}

}
