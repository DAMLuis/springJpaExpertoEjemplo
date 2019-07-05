package com.udemy.dao;

import org.springframework.data.repository.CrudRepository;

import com.udemy.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura , Long> {

}
