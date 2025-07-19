package com.thukera.vendasapi.rest.produtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thukera.vendasapi.model.Produto;

import lombok.Data;

@Data
public class ProdutoFormRequest {

	private Long id;
	private String descricao;
	private String nome;
	private BigDecimal preco;
	private String sku;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate cadastro;
	
	public Produto toModel () {
		return new Produto(nome,descricao,preco,sku);
	}
	
	public static ProdutoFormRequest fromModel(Produto produto) {
		return new ProdutoFormRequest(produto.getId() , produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getSku(),produto.getDataCadastro());
	}
	
	public ProdutoFormRequest() {
		super();
	}
	
	public ProdutoFormRequest(String descricao, String nome, BigDecimal preco, String sku) {
		super();
		this.descricao = descricao;
		this.nome = nome;
		this.preco = preco;
		this.sku = sku;
	}

	public ProdutoFormRequest(Long id, String descricao, String nome, BigDecimal preco, String sku,LocalDate cadastro) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.nome = nome;
		this.preco = preco;
		this.sku = sku;
		this.cadastro = cadastro;
	}



	
	
	
	
	
}
