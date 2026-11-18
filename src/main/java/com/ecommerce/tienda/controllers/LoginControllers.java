package com.ecommerce.tienda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.tienda.entity.Login;
import com.ecommerce.tienda.service.LoginService;

@RestController
@RequestMapping("/api")
public class LoginControllers {

    @Autowired
    private final LoginService loginService;

    public LoginControllers(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        System.out.println("Usuario: [" + login.getUsuario() + "]");
        System.out.println("Password: [" + login.getPassword() + "]");
        
        boolean valido = loginService.validar(login.getUsuario(), login.getPassword());
        if (valido) {
            return ResponseEntity.ok("Login exitoso");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrecta");
    }

}
