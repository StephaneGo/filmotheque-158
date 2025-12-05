package fr.eni.tp.filmotheque.controller;

import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dto.FilmDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
//@SessionAttributes("genresEnSession")
public class FilmController {

    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping("/films/detail")
    public String afficherUnFilm(@RequestParam(name="id") long identifiant, Model model) {

        Film film = this.filmService.consulterFilmParId(identifiant);
        System.out.println(film);

        model.addAttribute("film", film);
        return "view-film-detail";
    }


    @GetMapping("/films")
    public String afficherFilms(Model model) {

        List<Film> films = this.filmService.consulterFilms();
        for (Film film : films) {
            System.out.println(film);
        }

        model.addAttribute("films", films);

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
        Genre genre = filmService.consulterGenreParId(filmDto.getGenreId());
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
