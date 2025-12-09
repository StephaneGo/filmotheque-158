package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.exception.FilmNotFound;
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
public class FilmRepositoryImplTest {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	@Autowired
	public FilmRepository filmRepository;
	

	@BeforeEach
	public void initTest() {
		//jdbcTemplate.execute("truncate table genres");
        jdbcTemplate.update("delete from acteurs");
        jdbcTemplate.update("delete from films");
		jdbcTemplate.update("insert into films ( titre, annee, duree, synopsis, genreid, realisateurid)" +
                " values ('Some like it hot', 1959, 120," +
                "'Lorsque deux musiciens sont témoins d''un succès populaire, ils fuient l''État dans un groupe entièrement féminin, déguisés en femmes, mais d''autres complications s''ensuivent'" +
                " ,3, (select id from participants where nom = 'Wilder'))");
        jdbcTemplate.update("insert into acteurs (filmid, participantid) values ((select id from films where titre = 'Some like it hot'), (select id from participants where nom = 'Monroe'))");
        jdbcTemplate.update("insert into acteurs (filmid, participantid) values ((select id from films where titre = 'Some like it hot'), (select id from participants where nom = 'Lemmon'))");
        jdbcTemplate.update("insert into acteurs (filmid, participantid) values ((select id from films where titre = 'Some like it hot'), (select id from participants where nom = 'Curtis'))");
	}

	@Test
	@DisplayName("Test findAllFilms")
	public void testFindAllFilmsCasNominal() {
		//Arrange
		
		//Act 
		List<Film> films = filmRepository.findAllFilms();
		
		//Assert
		assertNotNull(films);
		Assertions.assertEquals(1, films.size());
        assertEquals("Some like it hot", films.get(0).getTitre());
	
	}

    @Test
    @DisplayName("Test findFilmById cas nominal")
    public void testFindFilmByIdCasNominal() {
        //Arrange
        Integer id = jdbcTemplate.queryForObject("select id from films where titre = ?", Integer.class, "Some like it hot");

        //Act
        Film film = filmRepository.findFilmById(id);

        //Assert
        assertNotNull(film);
        assertEquals("Some like it hot", film.getTitre());
        assertEquals(3, film.getActeurs().size());

    }


    @Test
    @DisplayName("Test findFilmById cas film non trouvé")
    public void testFindFilmByIdCasFilmNonTrouve() {
        //Arrange
        Integer id = 9999;

        //Act
        assertThrows(FilmNotFound.class, ()->filmRepository.findFilmById(id));

    }


	/*
	@Test
	@DisplayName("Test ajout Genre")
	public void testAjoutGenreCasNominal() {
		//Arrange
		Genre genre = new Genre(99l, "nouveau");
		
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

	*/
}
