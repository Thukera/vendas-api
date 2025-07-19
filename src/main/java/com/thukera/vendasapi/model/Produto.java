package com.thukera.vendasapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", length = 100)
	private String nome;
	
	@Column(name = "descricao", length = 255)
	private String descricao;
	
	@Column(name = "preco", precision = 16, scale = 2)
	private BigDecimal preco;
	
	@Column
	private String sku;
	
	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;
	
	@PrePersist
	public void prePersist() {
		setDataCadastro(LocalDate.now());
	}

	public Produto() {
		super();
	}
	
	public Produto(String nome, String descricao, BigDecimal preco, String sku) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.sku = sku;
	}
	
	public Produto(long id, String nome, String descricao, BigDecimal preco, String sku) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.sku = sku;
	}	
	
}
