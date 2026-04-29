package es.daw.extra_peliculas_mvc.dto;

import java.util.List;

// Película con su lista de actores
public record MovieWithCastDto(
        Long id,
        String title,
        int releaseYear,
        String genre,
        boolean active,
        List<ActorCastDto> cast
) {}