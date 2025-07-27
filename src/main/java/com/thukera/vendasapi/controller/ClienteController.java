package com.thukera.vendasapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thukera.vendasapi.model.Cliente;
import com.thukera.vendasapi.model.repository.ClienteRepository;
import com.thukera.vendasapi.rest.produtos.ClienteFormRequest;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {
	
	@Autowired
	private ClienteRepository repository;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody ClienteFormRequest request) {
		Cliente cliente = request.toModel();
		repository.save(cliente);
		return ResponseEntity.ok(ClienteFormRequest.fromModel(cliente));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ClienteFormRequest request) {
		
		Optional<Cliente> clienteExistente = repository.findById(id);	
		if(clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cliente cliente = request.toModel();
		cliente.setId(id);
		repository.save(cliente);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("{id}")
	public ResponseEntity<ClienteFormRequest> getById(@PathVariable Long id){
		
		return repository.findById(id)
				.map( ClienteFormRequest::fromModel ) // Metodo Reference :: 
				.map( clienteFR -> ResponseEntity.ok(clienteFR) ) // clienteFR é resultado do primeito .Map 
				.orElseGet( () -> ResponseEntity.notFound().build() ); // .map and Consumer ( () - > {} )
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id){
		
		return repository.findById(id)
				.map( cliente -> {
					repository.delete(cliente);
					return ResponseEntity.noContent().build();
					
				})
				.orElseGet( () -> ResponseEntity.notFound().build() );
		
	}
	
	@GetMapping
	public List<ClienteFormRequest> getLisa(){
		return repository
				.findAll()
				.stream()
				.map( ClienteFormRequest::fromModel )
				.collect(Collectors.toList());
	}
	
	
	
}
