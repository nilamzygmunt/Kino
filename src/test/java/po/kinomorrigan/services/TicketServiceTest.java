package po.kinomorrigan.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import po.kinomorrigan.models.TicketType;
import po.kinomorrigan.models.repositories.TicketRepository;
import po.kinomorrigan.models.repositories.TicketTypeRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static po.kinomorrigan.services.CalculationUtils.roundToTwoDecimalPlaces;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketTypeRepository ticketTypeRepository;
    @Mock
    private TicketRepository ticketRepository;
    private TicketService ticketService;
    private Long testTicketTypeId;
    private TicketType testTicketType;

    @BeforeEach
    void setUp() {
        ticketService = new TicketService(ticketTypeRepository, ticketRepository);
        testTicketTypeId = 1L;
        testTicketType = new TicketType("Bilet standard", "description", 18.5, 0.33);
    }

    @Test
    void when_getTicketTypeById_ticketTypeNotInDB_throwException() {
        when(ticketTypeRepository.findById(testTicketTypeId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketService.getTicketTypeById(testTicketTypeId))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ticket not found");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -0.1, 1.0, 1.5})
    void when_validateNewDiscount_discountOutOfRange_returnOptionalEmpty(double newDiscount) {
        //given
        when(ticketTypeRepository.findById(testTicketTypeId)).thenReturn(Optional.of(testTicketType));
        //when
        assertThat(ticketService.validateNewDiscount(testTicketTypeId, newDiscount)).isEmpty();
        //then
        verify(ticketTypeRepository, never()).save(any());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 0.1, 0.5, 0.99999999})
    void when_validateNewDiscount_discountValueInRange_returnOptionalOfTicketType(double newDiscount) {
        //given
        when(ticketTypeRepository.findById(testTicketTypeId)).thenReturn(Optional.of(testTicketType));
        when(ticketTypeRepository.save(testTicketType)).thenReturn(testTicketType);
        //when
        assertThat(ticketService.validateNewDiscount(testTicketTypeId, newDiscount).get())
                .hasFieldOrPropertyWithValue("discount", roundToTwoDecimalPlaces(newDiscount));
        //then
        verify(ticketTypeRepository).save(testTicketType);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 0.111111, Integer.MAX_VALUE})
    void when_validateNewPrice_priceValueInRange_returnOptionalOfTicketType(double newPrice) {
        //given
        when(ticketTypeRepository.findById(testTicketTypeId)).thenReturn(Optional.of(testTicketType));
        when(ticketTypeRepository.save(testTicketType)).thenReturn(testTicketType);
        //when
        assertThat(ticketService.validateNewPrice(testTicketTypeId, newPrice).get())
                .hasFieldOrPropertyWithValue("basePrice", roundToTwoDecimalPlaces(newPrice));
        //then
        verify(ticketTypeRepository).save(testTicketType);
    }

    @Test
    void when_validateNewPrice_priceNegative_returnOptionalEmpty() {
        //given
        when(ticketTypeRepository.findById(testTicketTypeId)).thenReturn(Optional.of(testTicketType));
        //when
        assertThat(ticketService.validateNewDiscount(testTicketTypeId, -0.00001)).isEmpty();
        //then
        verify(ticketTypeRepository, never()).save(any());
    }
}
