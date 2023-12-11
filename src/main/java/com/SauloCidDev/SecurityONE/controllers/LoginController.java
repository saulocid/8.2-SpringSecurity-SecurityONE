package com.SauloCidDev.SecurityONE.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SauloCidDev.SecurityONE.entities.Usuario;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("/")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, ModelMap model){
        Usuario u = new Usuario();
        u.setUserName(username);
        u.setPassword(password);
        model.addAttribute("usuario", u);
        return "index";
    }
    
}
