package fr.eni.tp.filmotheque.dal;

import fr.eni.tp.filmotheque.bo.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAllGenres();

    Genre findGenreById(int id);

    /* ajout ou modification d'un genre */
    void save(Genre genre);

    /* modification d'un genre */
    void update(Genre genre);

    void delete(int idGenre);
}
