package com.ecommerce.tienda.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.tienda.entity.Login;
import com.ecommerce.tienda.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginControllers {
    private final LoginService loginService;

    public LoginControllers(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public Login login(Login login) {
        return loginService.findById(login.getId());
    }

}
