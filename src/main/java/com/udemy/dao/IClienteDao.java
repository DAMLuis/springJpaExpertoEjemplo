package com.udemy.dao;


import org.springframework.data.repository.CrudRepository;

import com.udemy.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {


	
}
