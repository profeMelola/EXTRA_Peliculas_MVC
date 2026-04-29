package es.daw.extra_peliculas_mvc.security;

import es.daw.extra_peliculas_mvc.entity.AppUser;
import es.daw.extra_peliculas_mvc.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {


        AppUser user = userRepository.findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException(login));

//        User user = userRepository
//                .findByUsernameOrEmail(login,login)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + login));

        // REGLA DE NEGOCIO: si el usuario no está activo o enabled -> UsernameNotFoundException
        if (!user.isEnabled())
            throw new UsernameNotFoundException("Usuario desactivado: "+login);

        return user;

        // Típico IA: NOOOOOOOOOOOOOOOOOOO
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
//        );
    }



}

