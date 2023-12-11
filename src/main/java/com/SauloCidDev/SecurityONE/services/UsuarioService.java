package com.SauloCidDev.SecurityONE.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.SauloCidDev.SecurityONE.entities.Usuario;
import com.SauloCidDev.SecurityONE.enums.Rol;
import com.SauloCidDev.SecurityONE.exceptions.MyException;
import com.SauloCidDev.SecurityONE.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = userRepo.findByEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes atrib = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = atrib.getRequest().getSession();
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        }
        return null;

    }

    @Transactional
    public Boolean createUser(@RequestParam String email, @RequestParam String userName,  @RequestParam String password, @RequestParam String confirmPass,
            @RequestParam(defaultValue = "USER") Rol rol,
            ModelMap model) throws MyException {
        List<Usuario> usuarios = userRepo.findAll();
        Boolean band = false;
        Boolean bandEmail = false;
        Boolean bandUsuario = false;

        if (!usuarios.isEmpty()) {
            for (Usuario us : usuarios) {
                if (us.getEmail().equalsIgnoreCase(email)) {
                    bandEmail = true;
                }
                if (us.getUserName().equalsIgnoreCase(userName)) {
                    bandUsuario = true;
                }
            }
        }

        if (bandEmail == false && bandUsuario == false) {
            if (password.equals(confirmPass)) {
                Usuario u = new Usuario();
                u.setEmail(email);
                u.setPassword(new BCryptPasswordEncoder().encode(password));
                u.setUserName(userName);
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
