package fr.eni.tp.filmotheque.exception;

public class FilmNotFound extends RuntimeException {
    public FilmNotFound(Integer idFilm) {
        super("Film not found with id " + idFilm);
    }
}
