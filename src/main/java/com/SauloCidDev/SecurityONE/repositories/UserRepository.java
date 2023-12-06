package com.SauloCidDev.SecurityONE.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SauloCidDev.SecurityONE.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    
    @Query("SELECT u FROM User u WHERE u.username = :opc")
    public User findUserByName(@Param("opc") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findUserByEmail(String email);

}
