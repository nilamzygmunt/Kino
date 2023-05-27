package po.kinomorrigan.services;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import po.kinomorrigan.models.Screening;
import po.kinomorrigan.models.Seat;
import po.kinomorrigan.models.Ticket;
import po.kinomorrigan.models.repositories.ScreeningRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {
    private ScreeningRepository screeningRepository;
    private TicketService ticketService;

    public ScreeningService(ScreeningRepository screeningRepository, TicketService ticketService) {
        this.screeningRepository = screeningRepository;
        this.ticketService = ticketService;
    }

    public List<Screening> getScreeningList() {
        return screeningRepository.findAll();
    }

    public Screening getScreeningById(Long id) {
        return screeningRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("screening not found")
        );
    }

    public Optional<Pair<Screening, List<Seat>>> getScreeningIfAvailable(Long screeningId) {
        Screening screening = getScreeningById(screeningId);
        List<Ticket> ticketsForScreening = ticketService.getTicketsForScreening(screening);
        int availableSeatsNumber = screening.getHall().getSeats().size();
        if (ticketsForScreening.size() >= availableSeatsNumber) {
            return Optional.empty();
        }
        List<Seat> takenSeats = ticketsForScreening.stream().map(Ticket::getSeat).toList();
        return Optional.of(Pair.of(screening, takenSeats));
    }
}
