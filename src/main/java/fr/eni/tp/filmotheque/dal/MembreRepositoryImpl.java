package fr.eni.tp.filmotheque.dal;

import java.util.Optional;

import fr.eni.tp.filmotheque.bo.Membre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;



@Repository
public class MembreRepositoryImpl implements MembreRepository{

	private JdbcTemplate jdbcTemplate;
	
	public MembreRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Membre> findMembreByPseudo(String pseudo) {
		Optional<Membre> optMembre;
		
		String sql = "select id, pseudo, motDePasse, admin from membres where pseudo=?";
		
		try {
			Membre membre = jdbcTemplate.queryForObject(sql,
					new BeanPropertyRowMapper<Membre>(Membre.class),
					pseudo);
			optMembre = Optional.of(membre);
		}catch(EmptyResultDataAccessException exc) {
			optMembre = Optional.empty();
		}
		
		return optMembre;
	}
	
//	private class MembreRowMapper implements RowMapper<Membre>(){
//		
//	}

}
