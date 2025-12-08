package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAllGenres();

    Genre findGenreById(int id);

    /* ajout ou modification d'un genre */
    Genre save(Genre genre);

    void delete(int idGenre);
}
