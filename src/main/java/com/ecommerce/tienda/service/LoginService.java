package com.ecommerce.tienda.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.tienda.entity.Login;
import com.ecommerce.tienda.repository.LoginRepository;

@Service
public class LoginService {
    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public boolean validar(String usuario, String password) {
        Optional<Login> login = loginRepository.findByUsuarioAndPassword(
                usuario,
                password);
        System.out.println(login);
        return login.isPresent();
    }

}
