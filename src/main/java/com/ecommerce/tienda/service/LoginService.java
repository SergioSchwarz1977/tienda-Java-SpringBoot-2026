package com.ecommerce.tienda.service;

import org.springframework.stereotype.Service;

import com.ecommerce.tienda.entity.Login;
import com.ecommerce.tienda.repository.LoginRepository;

@Service
public class LoginService {
    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Login findById(int id) {
        return loginRepository.findById(id).orElse(null);
    }

}
