package com.thukera.vendasapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	public ProdutoFormRequest salvar (@RequestBody ProdutoFormRequest produto ) {
		System.out.println("==================== CONTROLLER PRODUTOS - POST PRODUTO");
		System.out.println("== Produto : " + produto);
		
		Produto entidadeProduto = produto.toModel();
		repository.save(entidadeProduto);
		System.out.println("== Save() : " + entidadeProduto);
		
		return ProdutoFormRequest.fromModel(entidadeProduto);
	}
	
	
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(@PathVariable long id, @RequestBody ProdutoFormRequest produto) {
		System.out.println("==================== CONTROLLER PRODUTOS - PUT PRODUTO");
		System.out.println("== Produto : " + produto);
		
		Optional<Produto> produtoExistente = repository.findById(id);
		
		if(produtoExistente.isEmpty()) {
			System.out.println("== Produto Não Cadastrado");
			//throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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
	
	
	
}
