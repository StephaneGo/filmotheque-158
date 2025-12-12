package fr.eni.tp.filmotheque.bll;

import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dal.FilmRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Film> films = filmRepository.findAllFilms();
        return films;
    }

    @Override
    public Film consulterFilmParId(Integer id) {

        return filmRepository.findFilmById(id);
    }



    @Override
    @Transactional
    public void creerFilm(Film film) {
        filmRepository.saveFilm(film);
    }
}
