package es.daw.extra_peliculas_mvc.controller;

import es.daw.extra_peliculas_mvc.dto.MovieResponseDto;
import es.daw.extra_peliculas_mvc.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
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

}
