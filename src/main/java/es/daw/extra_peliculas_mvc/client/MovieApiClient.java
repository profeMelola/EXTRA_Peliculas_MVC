package es.daw.extra_peliculas_mvc.client;

import es.daw.extra_peliculas_mvc.dto.MovieResponseDto;
import es.daw.extra_peliculas_mvc.dto.MovieWithCastDto;
import es.daw.extra_peliculas_mvc.exception.MovieApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieApiClient {

    private final WebClient movieWebClient;

    // GET /movies — listado completo
    public List<MovieResponseDto> getAllMovies() {
        MovieResponseDto[] movies = movieWebClient.get()
                .uri("/movies")
                .retrieve()
                // Sí te puedes conectar pero el api devuelve un status de error...
                // para errores 400 y 500, pero hay conexión!!!
                .onStatus(status -> status.isError(), resp ->
                        resp.bodyToMono(String.class).defaultIfEmpty("")
                                .flatMap(body -> Mono.error(
                                        new MovieApiException("Error al obtener películas: "
                                                + resp.statusCode() + " " + body)))
                )
//                .bodyToFlux(MovieResponseDto.class)
//                .collectList()
                .bodyToMono(MovieResponseDto[].class)
                // Salta siempre!!! y si está caído el api o he puesto mal la url????
                // Error que no me da respuesta el api. No tengo code status
                .onErrorMap(ex -> {
                    if (ex instanceof MovieApiException) return ex;
                    return new MovieApiException("No se pudo conectar con /movies. Detalle: " + ex.getMessage());
                })
                .block();

        return movies != null ? Arrays.asList(movies) : Collections.emptyList();
    }

    // GET /movies/{id} — detalle sin reparto
    public MovieResponseDto getMovieById(Long id) {
        return movieWebClient.get()
                .uri("/movies/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), resp ->
                        Mono.empty()
                )
                .onStatus(status -> status.is5xxServerError(), resp ->
                        resp.bodyToMono(String.class).defaultIfEmpty("")
                                .flatMap(body -> Mono.error(
                                        new MovieApiException("Error del servidor al obtener película "
                                                + id + ": " + body)))
                )
                .bodyToMono(MovieResponseDto.class)
                .onErrorMap(ex -> {
                    if (ex instanceof MovieApiException) return ex;
                    return new MovieApiException("No se pudo conectar con /movies/" + id + ". Detalle: " + ex.getMessage());
                })
                .block();
    }

    // GET /movies/{id}/with-cast — detalle con reparto embebido
    public MovieWithCastDto getMovieWithCast(Long id) {
        return movieWebClient.get()
                .uri("/movies/{id}/with-cast", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), resp ->
                        Mono.empty()
                )
                .onStatus(status -> status.is5xxServerError(), resp ->
                        resp.bodyToMono(String.class).defaultIfEmpty("")
                                .flatMap(body -> Mono.error(
                                        new MovieApiException("Error del servidor al obtener reparto de película "
                                                + id + ": " + body)))
                )
                .bodyToMono(MovieWithCastDto.class)
                .onErrorMap(ex -> {
                    if (ex instanceof MovieApiException) return ex;
                    return new MovieApiException("No se pudo conectar con /movies/" + id + "/with-cast. Detalle: " + ex.getMessage());
                })
                .block();
    }
}
