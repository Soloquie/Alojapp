package co.uniquindio.alojapp.seguridad;

import co.uniquindio.alojapp.persistencia.Entity.Usuario;
import co.uniquindio.alojapp.persistencia.Repository.UsuarioRepository;
import co.uniquindio.alojapp.persistencia.Repository.AnfitrionRepository;
import co.uniquindio.alojapp.persistencia.Repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final AnfitrionRepository anfitrionRepository;
    private final AdministradorRepository administradorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe usuario: " + email));

        List<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_HUESPED"));
        if (anfitrionRepository.existsByUsuarioId(u.getId())) {
            auths.add(new SimpleGrantedAuthority("ROLE_ANFITRION"));
        }
        if (administradorRepository.existsByUsuarioId(u.getId())) {
            auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        boolean enabled = !"BLOQUEADO".equalsIgnoreCase(String.valueOf(u.getEstado())); // ajusta si tu estado es enum/cadena
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getContrasenaHash(),  // debe ser el hash BCrypt almacenado
                enabled,
                true,
                true,
                true,
                auths
        );
    }
}
