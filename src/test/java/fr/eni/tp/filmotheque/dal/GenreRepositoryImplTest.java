package fr.eni.tp.filmotheque.dal;

import java.util.List;

import fr.eni.tp.filmotheque.exception.GenreNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import fr.eni.tp.filmotheque.bo.Genre;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GenreRepositoryImplTest {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	@Autowired
	public GenreRepository genreRepository;
	

	@BeforeEach
	public void initTest() {
		//jdbcTemplate.execute("truncate table genres");
        jdbcTemplate.update("delete from Genres");
		jdbcTemplate.update("insert into genres ( id, libelle) values (1, 'Animation')");
		jdbcTemplate.update("insert into genres ( id, libelle) values (2, 'Aventure')");
		jdbcTemplate.update("insert into genres ( id, libelle) values (3, 'Comédie')");
		jdbcTemplate.update("insert into genres ( id, libelle) values (4, 'Horreur')");
	}

	@Test
	@DisplayName("Test findAllGenres")
	public void testFindAllGenresCasNominal() {
		//Arrange
		
		//Act 
		List<Genre> genres = genreRepository.findAllGenres();
		
		//Assert
		assertNotNull(genres);
		Assertions.assertEquals(4, genres.size());
	
	}

	
	@Test
	@DisplayName("Test ajout Genre")
	public void testAjoutGenreCasNominal() {
		//Arrange
		Genre genre = new Genre(99, "nouveau");
		
		//Act 
		genreRepository.save(genre);
		
		//Assert
		assertNotNull(genre);
        assertEquals(genre.getId(), 99);
        assertEquals(genre.getTitre(),"nouveau" );

	
	}

	@Test
	@DisplayName("Test suppression Genre cas nominal : aucun film n'utilise le genre à supprimer")
	public void testSuppressionGenreCasNominal() {
		//Arrange
		
		//Act 
		genreRepository.delete(4);
		
		//Assert
		assertThrows(GenreNotFoundException.class, ()->genreRepository.findGenreById(4));
		

	
	}

	
}
