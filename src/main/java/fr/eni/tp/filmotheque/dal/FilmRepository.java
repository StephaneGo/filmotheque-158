package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Avis;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Participant;

import java.util.List;
import java.util.Optional;


public interface FilmRepository {
	
	List<Film> findAllFilms();
	Film findFilmById(Integer idFilm);
	void saveFilm(Film film);

//	List<Participant> findActeursByFilm(int idFilm);

}
