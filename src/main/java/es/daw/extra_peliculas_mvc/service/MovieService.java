package es.daw.extra_peliculas_mvc.service;

import es.daw.extra_peliculas_mvc.client.MovieApiClient;
import es.daw.extra_peliculas_mvc.dto.MovieResponseDto;
import es.daw.extra_peliculas_mvc.dto.MovieWithCastDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieApiClient apiClient;

    public List<MovieResponseDto> getAllMovies() {
        return apiClient.getAllMovies();
    }

    public MovieResponseDto getMovieById(Long id) {
        return apiClient.getMovieById(id);
    }

    public MovieWithCastDto getCastByMovieId(Long id) {
        return apiClient.getMovieWithCast(id);
    }
}
