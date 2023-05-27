package po.kinomorrigan.services;

import org.springframework.stereotype.Service;
import po.kinomorrigan.models.Film;
import po.kinomorrigan.models.FilmGenre;
import po.kinomorrigan.models.repositories.FilmGenreRepository;
import po.kinomorrigan.models.repositories.FilmRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class FilmService {
    private FilmRepository filmRepository;
    private FilmGenreRepository genreRepository;

    public FilmService(FilmRepository filmRepository, FilmGenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Film not found"));
    }

    public Optional<Film> updateFilm(Long filmId, String description) {
        Film film = getFilmById(filmId);
        if (!isNotNullNotEmptyNotWhiteSpace(description)) {
            return Optional.empty();
        }
        film.setDescription(description);
        Film savedFilm = filmRepository.save(film);

        return Optional.of(savedFilm);
    }

    public Optional<Film> createNewFilm(String title, String description, String ageCat, int duration, LocalDate premiere, String genre) {
        if (isNotNullNotEmptyNotWhiteSpace(title) && isNotNullNotEmptyNotWhiteSpace(description)) {

            FilmGenre filmGenre = new FilmGenre(genre);
            genreRepository.save(filmGenre);

            Film film = new Film(title, premiere, duration, description, ageCat, filmGenre);
            Film savedFilm = filmRepository.save(film);

            filmRepository.save(savedFilm);
            return Optional.of(savedFilm);
        } else {
            return Optional.empty();
        }
    }

    public static boolean isNotNullNotEmptyNotWhiteSpace(final String string)
    {
        return string != null && !string.isEmpty() && !string.trim().isEmpty();
    }
}
