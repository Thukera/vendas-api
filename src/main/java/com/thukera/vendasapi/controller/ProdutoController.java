package com.thukera.vendasapi.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import org.springframework.web.server.ResponseStatusException;

import com.thukera.vendasapi.model.Produto;
import com.thukera.vendasapi.model.repository.ProdutoRepository;
import com.thukera.vendasapi.rest.produtos.ProdutoFormRequest;

@RestController
@RequestMapping("api/produtos")
@CrossOrigin("*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;

	@PostMapping
	public ProdutoFormRequest salvar(@RequestBody ProdutoFormRequest produto) {
		System.out.println("==================== CONTROLLER PRODUTOS - POST PRODUTO");
		System.out.println("== Produto : " + produto);

		Produto entidadeProduto = produto.toModel();
		repository.save(entidadeProduto);
		System.out.println("== Save() : " + entidadeProduto);

		return ProdutoFormRequest.fromModel(entidadeProduto);
	}

	// JAVA PADRÃO
//	@GetMapping
//	public List<ProdutoFormRequest> getList() {
//		return repository.findAll().stream().map(new Function<Produto, ProdutoFormRequest>() {
//
//			@Override
//			public ProdutoFormRequest apply(Produto produto) {
//				return ProdutoFormRequest.fromModel(produto);
//			}
//		}).collect(Collectors.toList());
//	}


	@GetMapping
	public List<ProdutoFormRequest> getList() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return repository.findAll().stream()
				//.map(p -> ProdutoFormRequest.fromModel(p)) // LAMBDA FUNCTION
				.sorted(Comparator.comparing(Produto::getId)) // ✅ Order by ID
				.map(ProdutoFormRequest::fromModel) // JAVA REFERENCE 
				.collect(Collectors.toList());
	}

	@GetMapping("{id}")
	public ResponseEntity<ProdutoFormRequest> getById(@PathVariable Long id) {
		Optional<Produto> produtoExistente = repository.findById(id);
		if(produtoExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		var produto =produtoExistente.map(ProdutoFormRequest::fromModel).get();
		
		return ResponseEntity.ok(produto);
		
	}

	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(@PathVariable long id, @RequestBody ProdutoFormRequest produto) {
		System.out.println("==================== CONTROLLER PRODUTOS - PUT PRODUTO");
		System.out.println("== Produto : " + produto);

		Optional<Produto> produtoExistente = repository.findById(id);

		if (produtoExistente.isEmpty()) {
			System.out.println("== Produto Não Cadastrado");
			// throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			return ResponseEntity.notFound().build();
		}
		System.out.println("== Produto  Existente: " + produtoExistente);

		Produto entidade = produto.toModel();
		System.out.println("== Entidade: " + entidade);

		entidade.setId(id);

		repository.save(entidade);
		System.out.println("== Salvo");

		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		System.out.println("==================== CONTROLLER PRODUTOS - PUT PRODUTO");
		System.out.println("== Produto ID : " + id);

		Optional<Produto> produtoExistente = repository.findById(id);

		if (produtoExistente.isEmpty()) {
			System.out.println("== Produto Não Cadastrado");
			// throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			return ResponseEntity.notFound().build();
		}
		System.out.println("== Produto  Existente: " + produtoExistente);

		repository.delete(produtoExistente.get());
		System.out.println("== Deletado");

		return ResponseEntity.noContent().build();
	}

}
