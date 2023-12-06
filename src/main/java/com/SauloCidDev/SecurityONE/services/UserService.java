package com.SauloCidDev.SecurityONE.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.SauloCidDev.SecurityONE.entities.User;
import com.SauloCidDev.SecurityONE.enums.Rol;
import com.SauloCidDev.SecurityONE.exceptions.MyException;
import com.SauloCidDev.SecurityONE.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;


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

    @Transactional
    public Boolean createUser(String email, String username, String pass, String confirmPass,@RequestParam(defaultValue = "USER") Rol rol,
            ModelMap model) throws MyException {
        List<User> usuarios = userRepo.findAll();
        Boolean band = false;
        Boolean bandEmail = false;
        Boolean bandUsuario = false;

        if (!usuarios.isEmpty()) {
            for (User us : usuarios) {
                if (us.getEmail().equals(email)) {
                    bandEmail = true;
                }
                if (us.getUsername().equals(username)) {
                    bandUsuario = true;
                }
            }
        }

        if (bandEmail == false && bandUsuario == false) {
            if (pass.equals(confirmPass)) {
                User u = new User();
                u.setEmail(email);
                u.setPassword(pass);
                u.setUsername(username);
                u.setRol(rol);
                userRepo.save(u);
                band = true;
            }
        } else {
            if (bandEmail == true) {
                model.addAttribute("email", "El e-mail ya está registrado");
            } else {
                model.addAttribute("usuario", "El usuario ya está registrado");
            }
        }
        return band;
    }
    
}
