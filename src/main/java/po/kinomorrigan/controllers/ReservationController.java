package po.kinomorrigan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import po.kinomorrigan.models.Reservation;
import po.kinomorrigan.models.TicketType;
import po.kinomorrigan.models.enums.PaymentType;
import po.kinomorrigan.services.ReservationService;
import po.kinomorrigan.services.TicketService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final TicketService ticketService;

    public ReservationController(ReservationService reservationService, TicketService ticketService) {
        this.reservationService = reservationService;
        this.ticketService = ticketService;
    }

    @PostMapping("/screenings/{screeningId}/book")
    public String bookSeats(@PathVariable Long screeningId,
                            @RequestParam List<Long> seatIds,
                            Model model,
                            RedirectAttributes redirectAttrs) {
        Optional<Reservation> reservation = reservationService.bookSeatsIfAvailable(screeningId, seatIds);
        if (reservation.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMessage", "Wprowadzono niepoprawne dane");
            return "redirect:/screenings/" + screeningId;
        }
        model.addAttribute("reservation", reservation.get());
        List<TicketType> types = ticketService.getTicketTypes();
        model.addAttribute("ticketTypes", types);
        return "purchase/ticket-type-choice";
    }

    @PostMapping("/{reservationId}/ticket-types")
    public String chooseTicketTypes(@PathVariable Long reservationId,
                                    @RequestParam Map<String, String> ticketNumbers,
                                    Model model) {
        reservationService.setTicketTypesForReservation(reservationId, ticketNumbers);
        double cost = reservationService.calculateCost(reservationId);
        model.addAttribute("cost", cost);
        model.addAttribute("reservationId", reservationId);
        return "purchase/payment-methods";
    }

    @GetMapping("/{reservationId}/payment-methods")
    public String getPaymentMethods(@PathVariable Long reservationId,
                                    Model model) {
        model.addAttribute("reservationId", reservationId);
        return "purchase/payment-methods";
    }

    @GetMapping("/{reservationId}/payment-type")
    public String payWithMethod(@PathVariable Long reservationId,
                                @RequestParam PaymentType method,
                                Model model,
                                RedirectAttributes redirectAttrs) {
        if (method == PaymentType.CARD) {
            boolean isApproved = reservationService.approvePayment(reservationId);
            if (!isApproved) {
                redirectAttrs.addFlashAttribute("errorMessage", "Transakcja nie powiodła się");
            } else {
                redirectAttrs.addFlashAttribute("alertPositive", "Transakcja zakończona pomyślnie");
            }
            return "redirect:/reservations/" + reservationId + "/payment-type/card";
        } else if (method == PaymentType.RESERVATION) {
            boolean isApproved = reservationService.approveReservation(reservationId);
            if (!isApproved) {
                redirectAttrs.addFlashAttribute("errorMessage", "Rezerwacja nie powiodła się");
            } else {
                redirectAttrs.addFlashAttribute("alertPositive", "Rezerwacja zakończona pomyślnie");
            }
            return "redirect:/reservations/" + reservationId + "/payment-type/reservation";
        } else {
            model.addAttribute("reservation", reservationId);
            return "purchase/payment-method-cash";
        }
    }

    @GetMapping("/{reservationId}/payment-type/card")
    public String payWithCard(@PathVariable Long reservationId,
                              Model model,
                              RedirectAttributes redirectAttrs) {
        return "purchase/payment-method-card";
    }

    @GetMapping("/{reservationId}/payment-type/reservation")
    public String makeReservation(@PathVariable Long reservationId,
                              Model model,
                              RedirectAttributes redirectAttrs) {
        return "purchase/payment-method-reservation";
    }

    @GetMapping("/{reservationId}/payment-method/cash/approve")
    public String payWithCash(@PathVariable Long reservationId,
                              Model model) {
        reservationService.approveCashPayment(reservationId);
        model.addAttribute("alertPositive", "Sprzedaż zakończona pomyślnie");
        return "purchase/payment-approve";
    }
}
