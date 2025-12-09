package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.dal.GenreRepository;
import fr.eni.tp.filmotheque.exception.GenreNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GenreServiceImplTest {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	@Autowired
	public GenreService genreService;
	

	@BeforeEach
	public void initTest() {
		jdbcTemplate.execute("delete from genres");
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
		List<Genre> genres = genreService.findAllGenres();
		
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
        genreService.saveGenre(genre);
		
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
        genreService.deleteGenre(4);
		
		//Assert
		assertThrows(GenreNotFoundException.class, ()->genreService.findGenreById(4));
		

	
	}

	
}
