package es.daw.extra_peliculas_mvc.dto;

/** Respuesta para Movie */
public record MovieResponseDto(
        Long id,
        String title,
        int releaseYear,
        String genre,
        boolean active
) {}
