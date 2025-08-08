package com.thukera.vendasapi.rest.produtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thukera.vendasapi.model.Cliente;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClienteFormRequest {
	
	private Long id;
	private String nome;
	private String cpf;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	private String endereco;
	private String email;
	private String telefone;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate cadastro;
	
	public ClienteFormRequest() {
		super();
	}	

	public ClienteFormRequest(Long id, String nome, String cpf, LocalDate dataNascimento, String endereco, String email,
			String telefone, LocalDate cadastro) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.email = email;
		this.telefone = telefone;
		this.cadastro = cadastro;
	}
	
	public Cliente toModel() {
		return new Cliente(id, dataNascimento, cpf, nome, endereco, telefone, email, cadastro);
	}
	
	public static ClienteFormRequest fromModel(Cliente cliente) {
		return new ClienteFormRequest(cliente.getId(),cliente.getNome(),cliente.getCpf(),cliente.getNascimento(),cliente.getEndereco(), cliente.getEmail(),cliente.getTelefone(),cliente.getDataCadastro());
	}
	
}
