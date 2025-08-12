package com.thukera.vendasapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

		System.out.println("CLiente : " + request);
		Optional<Cliente> clienteExistente = repository.findById(id);
		if (clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cliente cliente = request.toModel();
		System.out.println("CLiente : " + request);
		cliente.setId(id);
		repository.save(cliente);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("{id}")
	public ResponseEntity<ClienteFormRequest> getById(@PathVariable Long id) {

		return repository.findById(id).map(ClienteFormRequest::fromModel) // Metodo Reference ::
				.map(clienteFR -> ResponseEntity.ok(clienteFR)) // clienteFR é resultado do primeito .Map
				.orElseGet(() -> ResponseEntity.notFound().build()); // .map and Consumer ( () - > {} )

	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {

		return repository.findById(id).map(cliente -> {
			repository.delete(cliente);
			return ResponseEntity.noContent().build();

		}).orElseGet(() -> ResponseEntity.notFound().build());

	}

	@GetMapping
	public Page<ClienteFormRequest> getLisa(
			@RequestParam(required = false) String nome, 
			@RequestParam(required = false) String cpf,
			Pageable pageable) {
		return repository.buscarPorNomeCpf(nome, cpf, pageable).map(ClienteFormRequest::fromModel);
	}

}
