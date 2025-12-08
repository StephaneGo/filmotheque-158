package fr.eni.tp.filmotheque.bll;

import java.util.List;

import fr.eni.tp.filmotheque.bo.Genre;

public interface GenreService {
	List<Genre> findAllGenres();
	Genre findGenreById(int idGenre);
	void saveGenre(Genre genre);
	void deleteGenre(int idGenre);
	
}
