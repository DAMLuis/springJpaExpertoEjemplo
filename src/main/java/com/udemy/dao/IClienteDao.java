package com.udemy.dao;



import org.springframework.data.repository.PagingAndSortingRepository;

import com.udemy.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {


	
}
