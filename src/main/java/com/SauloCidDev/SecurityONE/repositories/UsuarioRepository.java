package com.SauloCidDev.SecurityONE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.SauloCidDev.SecurityONE.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String>{
    
    @Query("SELECT u FROM Usuario u WHERE u.user_name = :user_name")
    public Usuario findByUserName(@Param("user_name") String userName);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario findByEmail(@Param("email") String email);

}
