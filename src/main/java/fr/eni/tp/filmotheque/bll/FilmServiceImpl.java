package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.FilmRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class FilmServiceImpl implements FilmService {

    private FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Film> consulterFilms() {
        return filmRepository.findAllFilms();
    }

    @Override
    public Film consulterFilmParId(long id) {
        return null;
    }

    @Override
    public List<Participant> consulterParticipants() {
        return List.of();
    }

    @Override
    public Participant consulterParticipantParId(long id) {
        return null;
    }

    @Override
    public void creerFilm(Film film) {

    }
}
