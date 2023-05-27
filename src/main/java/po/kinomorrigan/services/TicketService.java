package po.kinomorrigan.services;

import org.springframework.stereotype.Service;
import po.kinomorrigan.models.Screening;
import po.kinomorrigan.models.Ticket;
import po.kinomorrigan.models.TicketType;
import po.kinomorrigan.models.repositories.TicketRepository;
import po.kinomorrigan.models.repositories.TicketTypeRepository;

import java.util.List;
import java.util.Optional;

import static po.kinomorrigan.services.CalculationUtils.roundToTwoDecimalPlaces;

@Service
public class TicketService {
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;

    public TicketService(TicketTypeRepository ticketTypeRepository, TicketRepository ticketRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<TicketType> getTicketTypes() {
        List<TicketType> types = ticketTypeRepository.findAll();
        return types;
    }

    public TicketType getTicketTypeById(Long ticketTypeId) throws IllegalArgumentException {
        TicketType type = ticketTypeRepository.findById(ticketTypeId).orElseThrow(
                () -> new IllegalArgumentException("ticket not found")
        );
        return type;
    }

    public Optional<TicketType> validateNewPrice(Long ticketTypeId, Double newPrice) {
        TicketType type = getTicketTypeById(ticketTypeId);
        if (newPrice < 0) {
            return Optional.empty();
        }
        newPrice = roundToTwoDecimalPlaces(newPrice);
        type.setBasePrice(newPrice);
        var ticket = ticketTypeRepository.save(type);
        return Optional.of(ticket);
    }

    public Optional<TicketType> validateNewDiscount(Long ticketTypeId, Double newDiscount) {
        TicketType type = getTicketTypeById(ticketTypeId);
        if (newDiscount < 0 || newDiscount >= 1) {
            return Optional.empty();
        }
        newDiscount = roundToTwoDecimalPlaces(newDiscount);
        type.setDiscount(newDiscount);
        var ticket = ticketTypeRepository.save(type);
        return Optional.of(ticket);
    }

    public List<Ticket> getTicketsForScreening(Screening screening) {
        return ticketRepository.findByScreening(screening);
    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
