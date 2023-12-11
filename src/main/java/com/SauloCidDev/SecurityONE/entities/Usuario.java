package com.SauloCidDev.SecurityONE.entities;

import com.SauloCidDev.SecurityONE.enums.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue( generator = "uuid")
    private String id;
    private String userName;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Rol getRol() {
        return rol;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
