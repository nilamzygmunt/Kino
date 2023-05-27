package po.kinomorrigan.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import po.kinomorrigan.models.Hall;
import po.kinomorrigan.models.repositories.HallRepository;


import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HallServiceTest
{
    @Mock
    private HallRepository hallRepository;
    @Mock
    private SeatService seatService;
    private HallService hallService;
    private Long testHallId;
    private Hall testHall;

    @BeforeEach
    void setUp() {
        hallService = new HallService(hallRepository, seatService);
        testHallId = 1L;
        testHall = new Hall(30);
    }

    @Test
    void when_getHallById_ticketTypeNotInDB_throwException() {
        when(hallRepository.findById(testHallId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hallService.getHallById(testHallId))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("hall not found");
    }
    @ParameterizedTest
    @ValueSource(ints = {2, 8, Integer.MAX_VALUE})
    void when_updateHall_seatsNumberIsInvalid_returnOptionalEmpty(int newSeatsNumber)
    {
        //given
        when(hallRepository.findById(testHallId)).thenReturn(Optional.of(testHall));
        //when
        assertThat(hallService.validateNumberOfSeats(testHallId, newSeatsNumber)).isEmpty();

        //then
        verify(hallRepository, never()).save(any());
    }

    @ParameterizedTest
    @ValueSource(ints = {20, 38, 50})
    void when_updateFilm_newSeatsNumberIsValid_returnOptionalOfHAll(int newSeatsNumber)
    {
        when(hallRepository.findById(testHallId)).thenReturn(Optional.of(testHall));
        when(hallRepository.save(testHall)).thenReturn(testHall);

        //when
        assertThat(hallService.validateNumberOfSeats(testHallId, newSeatsNumber).get())
                .hasFieldOrPropertyWithValue("numberOfSeats", newSeatsNumber);

        //then
        verify(hallRepository).save(testHall);
    }
}

