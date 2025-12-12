package fr.eni.tp.filmotheque.dal;

import java.util.Optional;

import fr.eni.tp.filmotheque.bo.Membre;
import org.springframework.jdbc.core.JdbcTemplate;


public interface MembreRepository {
	
	
	
	public Optional<Membre> findMembreByPseudo(String pseudo) ;

}
