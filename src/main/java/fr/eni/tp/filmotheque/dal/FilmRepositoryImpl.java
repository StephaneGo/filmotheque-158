package fr.eni.tp.filmotheque.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


@Repository
//@Primary
public class FilmRepositoryImpl implements FilmRepository {

	private static String SELECT_FILM = 
			"select f.id as id, titre, annee, duree, "
			+ " synopsis, genreid, realisateurid,"
			+ " libelle, nom, prenom "
			+ " from films f inner join genres g "
			+ "	on  g.id = f.genreid "
			+ "	inner join participants p "
			+ "	on p.id = f.realisateurid ";
	
	private JdbcTemplate jdbcTemplate;


	public FilmRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate =jdbcTemplate;
	}

	
	private class FilmRowMapper implements RowMapper<Film> {

		@Override
		public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
			Film film = new Film();
			film.setId(rs.getInt("id"));
			film.setTitre(rs.getString("titre"));
			film.setAnnee(rs.getInt("annee"));
			film.setDuree(rs.getInt("duree"));
			film.setSynopsis(rs.getString("synopsis"));
			
			Genre genre = new Genre(rs.getInt("genreid"),
					                rs.getString("libelle"));
			
			film.setGenre(genre);
			
			Participant realisateur = new Participant(
					                     rs.getInt("realisateurid"),
					                     rs.getString("nom"),
					                     rs.getString("prenom")
					                     );
			film.setRealisateur(realisateur);
			
			return film;

		}

	}
	
	@Override
	public List<Film> findAllFilms() {

		List<Film> films = jdbcTemplate.query(SELECT_FILM, new FilmRowMapper());

		return films;
	}


	

	@Override
	public Optional<Film> findFilmById(int idFilm) {
		String sql = SELECT_FILM + " where f.id=?";
		
		Optional<Film> optFilm  ;
		
		try {
			Film film = jdbcTemplate.queryForObject(sql, new FilmRowMapper(), idFilm);
			optFilm = Optional.of(film);
		}catch(EmptyResultDataAccessException exc) {
			optFilm = Optional.empty();
		}

		
		return optFilm;
	}

	@Override	
	public void saveFilm(Film film) {
		final String sql = "insert into films (titre, annee, duree, synopsis, genreId, realisateurId) "
				+ " values (?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		

		jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getTitre());
            ps.setInt(2, film.getAnnee());
            ps.setInt(3, film.getDuree());
            ps.setString(4, film.getSynopsis());
            ps.setInt(5, film.getGenre().getId());
            ps.setLong(6, film.getRealisateur().getId());

            return ps;
        }, keyHolder );

		film.setId(keyHolder.getKey().intValue());
				
		if(film.getActeurs().size()>0) {
			final String sql_acteurs = "insert into acteurs (filmId, participantId) values (?, ?)";

            jdbcTemplate.batchUpdate(sql_acteurs, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Participant acteur = film.getActeurs().get(i);
                    ps.setLong(1, film.getId());
                    ps.setLong(2, acteur.getId());
                }

                @Override
                public int getBatchSize() {
                    return film.getActeurs().size();
                }
            });
		}
		
		
	}



	


}
