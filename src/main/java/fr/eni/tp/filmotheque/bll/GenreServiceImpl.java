package fr.eni.tp.filmotheque.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.dal.GenreRepository;

@Service("genreService")
public class GenreServiceImpl implements GenreService{

	public GenreRepository genreRepository;

	public GenreServiceImpl(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	@Override
	public List<Genre> findAllGenres() {
		return genreRepository.findAllGenres();
	}

	@Override
	public Genre findGenreById(int idGenre)  {

		return genreRepository.findGenreById(idGenre);
	}

	@Override
	public void saveGenre(Genre genre) {
		
		genreRepository.save(genre);
		
	}

	@Override
	public void deleteGenre(int idGenre) {
		//On supprime le genre
		genreRepository.delete(idGenre);
	}

}
