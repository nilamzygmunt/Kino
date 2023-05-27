package po.kinomorrigan.models.initialization;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import po.kinomorrigan.models.*;
import po.kinomorrigan.models.enums.Standard;
import po.kinomorrigan.models.repositories.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DataLoader implements ApplicationRunner {
    private final FilmGenreRepository genreRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;
    private final RepertoireRepository repertoireRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final LoyaltyCardRepository loyaltyCardRepository;
    private final UserRepository userRepository;

    public DataLoader(FilmGenreRepository genreRepository,
                      FilmRepository filmRepository,
                      HallRepository hallRepository,
                      RepertoireRepository repertoireRepository,
                      ScreeningRepository screeningRepository,
                      SeatRepository seatRepository,
                      ReservationRepository reservationRepository,
                      TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository,
                      LoyaltyCardRepository loyaltyCardRepository,
                      UserRepository userRepository) {
        this.genreRepository = genreRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
        this.repertoireRepository = repertoireRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.loyaltyCardRepository = loyaltyCardRepository;
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        FilmGenre genre = new FilmGenre("komedia");
        genreRepository.save(genre);

        Film film = new Film("title", LocalDate.of(2022, 12, 2), 112, "description", "16+", genre);
        filmRepository.save(film);

        Hall hall1 = new Hall(40);
        Hall hall2 = new Hall(25);
        hallRepository.save(hall1);
        hallRepository.save(hall2);

        Repertoire repertoire = new Repertoire();
        repertoireRepository.save(repertoire);

        Screening screening = new Screening(LocalDate.of(2023, 1, 1), LocalTime.of(22, 20), film, hall1, repertoire);
        Screening screening2 = new Screening(LocalDate.of(2023, 1, 2), LocalTime.of(22, 20), film, hall2, repertoire);
        Screening screening3 = new Screening(LocalDate.of(2023, 1, 3), LocalTime.of(22, 20), film, hall1, repertoire);
        screeningRepository.saveAll(List.of(screening, screening2, screening3));
        List<Seat> seats = new ArrayList<>();
        IntStream.range(0, 40).forEach((i) ->
                seats.add(new Seat(hall1, Standard.COMFORT))
        );
        IntStream.range(0,25).forEach((i) ->
                seats.add(new Seat(hall2, Standard.VIP))
        );
        seatRepository.saveAll(seats);
        hallRepository.save(hall1);
        hallRepository.save(hall2);

        TicketType tt = new TicketType("Bilet Standard", "description", 21.5, 0.2);
        ticketTypeRepository.save(tt);
        TicketType tt2 = new TicketType("Bilet VIP", "description2", 38.50, 0.2);
        ticketTypeRepository.save(tt2);
        Ticket ticket = new Ticket(tt, 20f, false, false, screening, seats.get(0), null);
        ticketRepository.save(ticket);

        User user1 = new User("new", "John", "Smith");
        User user2 = new User("client", "Jack", "Black");
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        user2.setLoyaltyCard(loyaltyCard);
        loyaltyCardRepository.save(loyaltyCard);
        userRepository.save(user1);
        userRepository.save(user2);

    }
}
