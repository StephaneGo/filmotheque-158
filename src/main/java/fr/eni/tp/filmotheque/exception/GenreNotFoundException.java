package fr.eni.tp.filmotheque.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(int id) {
        super("Genre with id " + id + " not found");
    }
}
