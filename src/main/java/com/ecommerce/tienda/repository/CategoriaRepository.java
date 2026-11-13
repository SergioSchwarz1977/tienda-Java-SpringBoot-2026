package com.ecommerce.tienda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.tienda.entity.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
   
    Optional<Categoria> findByCategoria(String categoria);
   
}
