package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.FilmRepository;
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
public class FilmServiceImplTest {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	@Autowired
	public FilmService filmService;
	

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
		List<Film> films = filmService.consulterFilms();
		
		//Assert
		assertNotNull(films);
		Assertions.assertEquals(1, films.size());
        assertEquals("Some like it hot", films.get(0).getTitre());
	
	}

    @Test
    @DisplayName("Test findFilmById cas nominal")
    public void testFindFilmByIdCasNominal() {
        //Arrange
        Integer id = jdbcTemplate.queryForObject("select id from films where titre = 'Some like it hot'", Integer.class );

        //Act
        Film film = filmService.consulterFilmParId(id);

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
        assertThrows(FilmNotFound.class, ()->filmService.consulterFilmParId(id));

    }

    @Test
    @DisplayName("testAjoutFilmCasDroit")
    public void testAjoutFilmCasDroit() {
        Integer idBillyWilder = jdbcTemplate.queryForObject("select id from participants where nom = 'Wilder'", Integer.class);
        Integer idMarylinMonroe = jdbcTemplate.queryForObject("select id from participants where nom = 'Monroe'", Integer.class);

        Film film = new Film();
        film.setTitre("Seven year itch");
        film.setAnnee(1955);
        film.setDuree(105);
        film.setSynopsis("Un bon père de famille se retrouve célibataire pour un été quand une blonde ingénue très craquante vient occuper l'appartement voisin");
        film.setGenre(new Genre(3, "Comedie"));
        film.setRealisateur(new Participant(idBillyWilder, "Wilder", "Billy"));
        Participant monroe = new Participant(idMarylinMonroe, "", "");
        film.setActeurs(List.of(monroe));

        //Act
        filmService.creerFilm(film);

        //Assert
        Film newFilm = filmService.consulterFilmParId(film.getId());
        assertNotNull(newFilm);
        assertEquals(film, newFilm);
        assertEquals(film.getTitre(), newFilm.getTitre());
        assertEquals(film.getAnnee(), newFilm.getAnnee());
        assertEquals(film.getDuree(), newFilm.getDuree());
        assertEquals(film.getActeurs().size(), newFilm.getActeurs().size());
        assertArrayEquals(film.getActeurs().toArray(), newFilm.getActeurs().toArray());
    }

}
