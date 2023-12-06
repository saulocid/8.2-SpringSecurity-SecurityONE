package com.SauloCidDev.SecurityONE.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.SauloCidDev.SecurityONE.entities.User;
import com.SauloCidDev.SecurityONE.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        User usuario = userRepo.findUserByEmail(email);

        if(usuario != null){
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes atrib = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = atrib.getRequest().getSession();
            return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), permisos);
        }
        return null;

    }
    
}
