package es.daw.extra_peliculas_mvc.controller;

import es.daw.extra_peliculas_mvc.dto.MovieResponseDto;
import es.daw.extra_peliculas_mvc.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    // -------------- sin lombok la inyección por constructor ------------
//    private final MovieService movieService;
//
//    // es opcional el @Autowired
//    @Autowired
//    public MovieController(MovieService movieService) {
//        this.movieService = movieService;
//    }
    // ---------------------------

//    // Inyección por propiedad
//    @Autowired
//    private MovieService movieService;

    private final MovieService movieService;

    @GetMapping
    public String list(Model model) {
        List<MovieResponseDto> movies = movieService.getAllMovies();

        model.addAttribute("movies", movies);

        return "list"; // la vista, no un endpoint
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        log.trace("El id de la movie es: {}", id);

        Optional<MovieResponseDto> optMovie= movieService.getMovieById(id);

        // Si no hay detalle de la peli, podemos mejorar añadiendo un mensaje en la vista list para indicarlo!!!!
        if (optMovie.isEmpty())
            return "redirect:/movies";

        MovieResponseDto movie = optMovie.get();

        model.addAttribute("movie", movie);

        return "detail";



    }

}
