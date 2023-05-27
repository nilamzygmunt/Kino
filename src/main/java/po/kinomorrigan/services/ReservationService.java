package po.kinomorrigan.services;

import org.springframework.stereotype.Service;
import po.kinomorrigan.models.*;
import po.kinomorrigan.models.enums.PaymentType;
import po.kinomorrigan.models.repositories.PaymentRepository;
import po.kinomorrigan.models.repositories.ReservationRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static po.kinomorrigan.services.CalculationUtils.roundToTwoDecimalPlaces;

@Service
public class ReservationService {

    private final ScreeningService screeningService;
    private final TicketService ticketService;
    private final SeatService seatService;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;


    public ReservationService(ScreeningService screeningService, TicketService ticketService, SeatService seatService, ReservationRepository reservationRepository, PaymentRepository paymentRepository) {
        this.screeningService = screeningService;
        this.ticketService = ticketService;
        this.seatService = seatService;
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
    }

    public Optional<Reservation> bookSeatsIfAvailable(Long screeningId, List<Long> seats) {
        Screening screening = screeningService.getScreeningById(screeningId);
        List<Ticket> bookedTickets = ticketService.getTicketsForScreening(screening);
        List<Long> takenSeats = bookedTickets.stream().map(t -> t.getSeat().getId()).toList();
        List<Long> alreadyBooked = seats.stream().filter(takenSeats::contains).toList();
        if (!alreadyBooked.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(createReservation(screening, seats));
        }
    }

    private Reservation createReservation(Screening screening, List<Long> seats) {
        Reservation reservation = new Reservation();
        Reservation savedReservation = reservationRepository.save(reservation);
        seats.forEach(s -> {
                    Seat seat = seatService.getSeatById(s);
                    Ticket ticket = new Ticket(screening, seat, savedReservation);
                    savedReservation.addTicket(ticket);
                    ticketService.saveTicket(ticket);
                }
        );
        return reservationRepository.save(savedReservation);
    }

    public void setTicketTypesForReservation(Long reservationId, Map<String, String> ticketNumbers) {
        Reservation reservation = getReservationById(reservationId);
        List<Ticket> tickets = reservation.getTickets();
        AtomicInteger currentTicketIndex = new AtomicInteger();
        ticketNumbers.entrySet().forEach(e -> {

            var tokens = e.getKey().split("_");
            Long typeId = Long.parseLong(tokens[tokens.length - 1]);
            boolean isDiscount = e.getKey().contains("Reduced");
            TicketType type = ticketService.getTicketTypeById(typeId);
            System.out.println(e.getValue());
            System.out.println(e.getValue().getClass());
            for (int i = 0; i < Integer.parseInt(e.getValue()); i++) {
                updateTicketData(tickets.get(currentTicketIndex.getAndIncrement()), type, isDiscount);
            }
        });
    }

    public double calculateCost(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        return reservation.getTickets().stream().mapToDouble(Ticket::getPrice).sum();
    }

    private void updateTicketData(Ticket ticket, TicketType type, boolean isDiscount) {
        ticket.setType(type);
        ticket.setDiscount(isDiscount);
        ticket.setPrice(roundToTwoDecimalPlaces(
                isDiscount ?
                        type.getBasePrice() - type.getBasePrice() * type.getDiscount() :
                        type.getBasePrice())
        );
        ticketService.saveTicket(ticket);
    }

    private Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(
                () -> new IllegalArgumentException("reservation not found")
        );
    }

    public boolean approvePayment(Long reservationId) {
        Random gen = new Random();
        boolean isApproved = gen.nextDouble() < 0.9;
        if (isApproved) {
            Reservation reservation = getReservationById(reservationId);
            Payment payment = new Payment(PaymentType.CARD);
            payment = paymentRepository.save(payment);
            reservation.setPayment(payment);
            reservation.getTickets().forEach(t -> t.setPaid(true));
            reservationRepository.save(reservation);
        }
        return isApproved;
    }

    public void approveCashPayment(Long reservationId) {
        Reservation reservation = getReservationById(reservationId);
        Payment payment = new Payment(PaymentType.CASH);
        payment = paymentRepository.save(payment);
        reservation.setPayment(payment);
        reservation.getTickets().forEach(t -> t.setPaid(true));
        reservationRepository.save(reservation);
    }

    public boolean approveReservation(Long reservationId) {
        Random gen = new Random();
        boolean isApproved = gen.nextDouble() < 0.9;
        if (isApproved) {
            Reservation reservation = getReservationById(reservationId);
            Payment payment = new Payment(PaymentType.RESERVATION);
            payment = paymentRepository.save(payment);
            reservation.setPayment(payment);
            reservation.getTickets().forEach(t -> t.setPaid(false));
            reservationRepository.save(reservation);
        }
        return isApproved;
    }
}
