package fr.eni.tp.filmotheque.security;

import fr.eni.tp.filmotheque.bo.Membre;
import fr.eni.tp.filmotheque.dal.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmothequeUserDetailsService implements UserDetailsService {

    private MembreRepository membreRepo;

    public FilmothequeUserDetailsService(MembreRepository membreRepo) {
        this.membreRepo = membreRepo;
    }


    @Override
    /* Cette méthode est appelée par Spring à chaque fois qu'un utilise essaye de se connecter */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Charger l'utilisateur depuis la base de données
        Optional<Membre> optMembre = membreRepo.findMembreByPseudo(username);

        if(optMembre.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        Membre membre = optMembre.get();
        User.UserBuilder userBuilder = User.builder().username(membre.getPseudo())
                .password(membre.getMotDePasse());

        if(membre.isAdmin()) {
            userBuilder = userBuilder.roles("ADMIN", "MEMBRE");
        }else {
            userBuilder = userBuilder.roles( "MEMBRE");
        }


        return userBuilder.build();
    }
}
