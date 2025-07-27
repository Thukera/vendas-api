package com.thukera.vendasapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thukera.vendasapi.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
