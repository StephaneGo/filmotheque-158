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
import fr.eni.tp.filmotheque.exception.FilmNotFound;
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
import org.springframework.transaction.annotation.Transactional;


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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	public FilmRepositoryImpl( NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate =namedParameterJdbcTemplate.getJdbcTemplate();
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

    public List<Participant> findActeursByFilm(int idFilm){
        String sql = "select p.id as id, nom, prenom "
                + "from participants p inner join acteurs a "
                + " on p.id = a.participantid and a.filmid = ?";

        List<Participant> acteurs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Participant>(Participant.class), idFilm);

        return acteurs;
    }

	@Override
    /*
        Récupération de tous les films.
        Les acteurs ne sont pas chargés (Lazy loading)
     */
	public List<Film> findAllFilms() {

		List<Film> films = jdbcTemplate.query(SELECT_FILM, new FilmRowMapper());

		return films;
	}


	

	@Override
	public Film findFilmById(Integer idFilm) {
		String sql = SELECT_FILM + " where f.id=?";
		
		Film film  =null;
		
		try {
			film = jdbcTemplate.queryForObject(sql, new FilmRowMapper(), idFilm);
            List<Participant> acteurs = findActeursByFilm(idFilm);
            film.setActeurs(acteurs);

		}catch(EmptyResultDataAccessException exc) {
			throw new FilmNotFound(idFilm);
		}

		return film;
	}

    /*
	@Override
    @Transactional
    //Ajout d'un film V1
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
*/

    @Override
    public void saveFilm(Film film) {
        String sql = "insert into films (titre, annee, duree, synopsis, genreid, realisateurid) "
                + " values (:titre, :annee_sortie, :duree, :synopsis, :genre_id, :realisateur_id)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("titre", film.getTitre());
        parameters.addValue("annee_sortie", film.getAnnee());
        parameters.addValue("duree", film.getDuree());
        parameters.addValue("synopsis", film.getSynopsis());
        parameters.addValue("genre_id", film.getGenre().getId());
        parameters.addValue("realisateur_id", film.getRealisateur().getId());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder ,new String[] { "id"} );

        film.setId(keyHolder.getKey().intValue());

        if(film.getActeurs().size()>0) {
            sql = "insert into acteurs (filmid, participantid) values (:film_id, :participant_id)";

            MapSqlParameterSource[] acteursParameters = new MapSqlParameterSource[film.getActeurs().size()];
            for(int i=0;i<film.getActeurs().size();i++) {
                acteursParameters[i]=new MapSqlParameterSource();
                acteursParameters[i].addValue("film_id", film.getId());
                acteursParameters[i].addValue("participant_id", film.getActeurs().get(i).getId());
            }

            namedParameterJdbcTemplate.batchUpdate(sql, acteursParameters);

        }


    }

	


}
