package com.thukera.vendasapi.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thukera.vendasapi.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	@Query("""
		       select c
		       from Cliente c
		       where upper(c.nome) like concat('%', upper(:nome), '%')
		         and c.cpf like concat('%', :cpf, '%')
		       """)
	Page<Cliente> buscarPorNomeCpf(
			@Param("nome") String nome,
            @Param("cpf") String cpf,
            Pageable pageable);
}
