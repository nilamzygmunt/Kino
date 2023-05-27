package po.kinomorrigan.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import po.kinomorrigan.models.Film;
import po.kinomorrigan.models.FilmGenre;
import po.kinomorrigan.models.repositories.FilmGenreRepository;
import po.kinomorrigan.models.repositories.FilmRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {
    @Mock
    private FilmRepository filmRepository;
    @Mock
    private FilmGenreRepository filmGenreRepository;
    private FilmService filmService;
    private Long testFilmId;
    private Film testFilm;

    @BeforeEach
    void setUp() {
        filmService = new FilmService(filmRepository, filmGenreRepository);
        testFilmId = 1L;

        FilmGenre genre = new FilmGenre(testFilmId, "komedia");
        testFilm = new Film("title", LocalDate.of(2022, 12, 2), 112, "description", "16+", genre);
    }

    @Test
    void when_getFilmById_FilmNotInDB_throwException() {
        when(filmRepository.findById(testFilmId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> filmService.getFilmById(testFilmId))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Film not found");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void when_updateFilm_filmDescriptionIsEmptyOrWhitespace_returnOptionalEmpty(String filmDesc) {
        //given
        when(filmRepository.findById(testFilmId)).thenReturn(Optional.of(testFilm));

        //when
        assertThat(filmService.updateFilm(testFilmId, filmDesc)).isEmpty();

        //then
        verify(filmRepository, never()).save(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"title", " aaaa ", "aaaa\t", "a\na"})
    void when_updateFilm_filmDescriptionIsValid_returnOptionalOfFilm(String filmDesc) {
        //given
        when(filmRepository.findById(testFilmId)).thenReturn(Optional.of(testFilm));
        when(filmRepository.save(testFilm)).thenReturn(testFilm);

        //when
        assertThat(filmService.updateFilm(testFilmId, filmDesc).get())
                .hasFieldOrPropertyWithValue("description", filmDesc);

        //then
        verify(filmRepository).save(testFilm);
    }

//    @Test
//    void when_createFilm_NewFilmValidData_returnOptionalOfFilm() {
//        //given
//        when(filmRepository.save(testFilm)).thenReturn(testFilm);
//
//        //when
//        assertThat(filmService.createNewFilm(testFilm.getTitle(),
//                testFilm.getDescription(),
//                testFilm.getAgeCategory(),
//                testFilm.getDurationTimeMinutes(),
//                testFilm.getPremiere(),
//                "Comedy").get())
//                .hasFieldOrPropertyWithValue("title", testFilm.getTitle())
//                .hasFieldOrPropertyWithValue("description", testFilm.getDescription());
//
//        //then
//        verify(filmRepository).save(testFilm);
//    }

    @Test
    void when_createFilm_NewFilmInValidData_returnOptionalEmpty() {
        //given

        //when
        assertThat(filmService.createNewFilm("",
                "",
                testFilm.getAgeCategory(),
                testFilm.getDurationTimeMinutes(),
                testFilm.getPremiere(),
                "Comedy")).isEmpty();

        //then
        verify(filmRepository, never()).save(any());
    }
}
