package fr.eni.tp.filmotheque.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.exception.GenreNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
@Primary
public class GenreRepositoryImpl implements GenreRepository {

	private JdbcTemplate jdbcTemplate;


	public GenreRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Genre> findAllGenres() {
		String sql = "select id, libelle from genres";

		List<Genre> genres = jdbcTemplate.query(sql, new GenreRowMapper());
		return genres;
	}

	@Override
	public Genre findGenreById(int id) {

		String sql = "select id, libelle from genres where id = ?";

        Genre genre = null;
		
		try {
			genre = jdbcTemplate.queryForObject(sql, new GenreRowMapper(), id);
		}catch(EmptyResultDataAccessException exc) {
			throw new GenreNotFoundException(id);
		}
		
		return genre;
	}

	// classe interne
	private class GenreRowMapper implements RowMapper<Genre> {

		@Override
		public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Genre(rs.getLong("id"), rs.getString("libelle"));

		}

	}

	@Override
	public void save(Genre genre) {
		
			String sql = "insert into Genres (id, libelle) values (?, ?)";

			jdbcTemplate.update(sql, genre.getId(), genre.getTitre() );

	}

    @Override
    public void update(Genre genre) {

            String sql = "update Genres set libelle=? where id=?";
            jdbcTemplate.update(sql, genre.getTitre(), genre.getId());

    }

	@Override
	public void delete(int idGenre) {
		String sql = "delete from  Genres where id=?";
		jdbcTemplate.update(sql, idGenre);
		
	}

}
