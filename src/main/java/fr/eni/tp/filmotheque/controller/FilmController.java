package fr.eni.tp.filmotheque.controller;

import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bll.GenreService;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dto.FilmDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
//@SessionAttributes("genresEnSession")
public class FilmController {

    Logger logger = LoggerFactory.getLogger(FilmController.class);

    private FilmService filmService;
    private GenreService genreService;

    public FilmController(FilmService filmService, GenreService genreService) {

        this.filmService = filmService;
        this.genreService = genreService;
    }

    @GetMapping("/login")
    public String login(Model modele) {

        return "connexion";    }

    @GetMapping({"/", "/accueil"})
    public String accueil(){
        return "accueil";
    }


    @GetMapping("/films/detail")
    public String afficherUnFilm(@RequestParam(name="id") Integer identifiant, Model model) {


        Film film = null;
        //try {
            film = this.filmService.consulterFilmParId(identifiant);

    /*}catch(FilmNotFound filmNotFound){
            return "view-erreur";
        }*/
        System.out.println(film);

        model.addAttribute("film", film);
        return "view-film-detail";
    }


    @GetMapping("/films")
    public String afficherFilms(Model model) {
        logger.debug("debut afficherFilms -niveau debug");
        logger.info("debut afficherFilms - niveau info");
        logger.warn("debut afficherFilms - niveau warning");
        logger.error("debut afficherFilms - niveau error");

        List<Film> films = this.filmService.consulterFilms();
        for (Film film : films) {
            //System.out.println(film);
            logger.info("film : {}", film);
        }

        model.addAttribute("films", films);
        logger.debug("fin afficherFilms - niveau debug");
        return "view-films";
    }

    @GetMapping("/films/creer")
    public String afficherFormulaireFilm(Model  model) {
        FilmDto  filmDto = (FilmDto) model.getAttribute("filmDto");
        if(filmDto == null){
            model.addAttribute("filmDto", new FilmDto());
        }

        return "view-film-form";
    }


    @PostMapping("/films/creer")
    public String creerFilm(@Valid  FilmDto filmDto, BindingResult resultat, RedirectAttributes redirectAttr) {

        if(resultat.hasErrors()) {

            redirectAttr.addFlashAttribute( "org.springframework.validation.BindingResult.filmDto", resultat);
            redirectAttr.addFlashAttribute("filmDto", filmDto);
            return "redirect:/films/creer";
        }


        Film film = new Film();
        BeanUtils.copyProperties(filmDto, film);
        Genre genre = genreService.findGenreById(filmDto.getGenreId());
        //Genre genre = new Genre(filmDto.getGenreId());
        film.setGenre(genre);
        Participant realisateur = filmService.consulterParticipantParId(filmDto.getRealisateurId());
        film.setRealisateur(realisateur);
        Participant acteur=null;
        List<Participant> acteurs= new ArrayList<Participant>();
        for(long idActeur: filmDto.getActeursIds()){
             acteurs.add(filmService.consulterParticipantParId(idActeur));
        }
        film.setActeurs(acteurs);

        filmService.creerFilm(film);

        return "redirect:/films/detail?id=" +  film.getId();
    }


    //Mise en commentaire car genres mis dans le contexte application
    /*
    @ModelAttribute("genresEnSession")
    @ApplicationScope
    public List<Genre> chargerGenres(){
        System.out.println("Chargement en Session - GENRES");
        return filmService.consulterGenres();
    }
*/

}
