package com.ecommerce.tienda.repository;

import com.ecommerce.tienda.entity.Login;

import com.ecommerce.tienda.repository.LoginRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {

}
