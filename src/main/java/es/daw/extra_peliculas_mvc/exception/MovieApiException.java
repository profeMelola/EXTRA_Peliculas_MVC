package es.daw.extra_peliculas_mvc.exception;

public class MovieApiException extends RuntimeException {
    public MovieApiException(String message) {
        super(message);
    }

    public MovieApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
