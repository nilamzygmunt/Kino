package po.kinomorrigan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import po.kinomorrigan.models.TicketType;
import po.kinomorrigan.services.TicketService;

import java.util.Optional;

@Controller
@RequestMapping("/ticket-types")
public class TicketTypeController {
    private final TicketService ticketService;

    public TicketTypeController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/prices")
    public String displayTicketTypes(Model model) {
        var ticketTypes = ticketService.getTicketTypes();
        model.addAttribute("tickets", ticketTypes);
        return "admin/ticket-types-view";
    }

    @GetMapping("/{ticketTypeId}")
    public String displayTicketTypeDetails(@PathVariable Long ticketTypeId, Model model) {
        TicketType type = ticketService.getTicketTypeById(ticketTypeId);
        model.addAttribute("ticket", type);
        return "admin/ticket-type-details";
    }

    @PostMapping("/{ticketTypeId}/update-price")
    public String updateTicketPrice(@PathVariable Long ticketTypeId,
                                    @RequestParam Double newPrice,
                                    Model model,
                                    RedirectAttributes redirectAttrs) {
        Optional<TicketType> updatedTicket = ticketService.validateNewPrice(ticketTypeId, newPrice);
        if (updatedTicket.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMessage", "Wprowadzono niepoprawne dane");
            return "redirect:/ticket-types/" + ticketTypeId;
        } else {
            model.addAttribute("ticket", updatedTicket.get());
            model.addAttribute("alertPositive", "Cena biletu zaktualizowana pomyślnie");
            return "admin/ticket-type-details";
        }
    }

    @PostMapping("/{ticketTypeId}/update-discount")
    public String updateTicketDiscount(@PathVariable Long ticketTypeId,
                                       @RequestParam Double newDiscount,
                                       Model model,
                                       RedirectAttributes redirectAttrs) {
        Optional<TicketType> updatedTicket = ticketService.validateNewDiscount(ticketTypeId, newDiscount);
        if (updatedTicket.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMessage", "Wprowadzono niepoprawne dane");
            return "redirect:/ticket-types/" + ticketTypeId;

        } else {
            model.addAttribute("ticket", updatedTicket.get());
            model.addAttribute("alertPositive", "Wartość ulgi zaktualizowana pomyślnie");
            return "admin/ticket-type-details";
        }
    }

}
