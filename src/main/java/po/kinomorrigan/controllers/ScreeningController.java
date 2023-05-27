package po.kinomorrigan.controllers;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import po.kinomorrigan.models.Screening;
import po.kinomorrigan.models.Seat;
import po.kinomorrigan.services.ScreeningService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/screenings")
public class ScreeningController {
    private ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public String displayScreeningList(Model model) {
        List<Screening> screenings = screeningService.getScreeningList();
        model.addAttribute("screenings", screenings);
        return "purchase/screening-list";
    }

    @GetMapping("/{screeningId}")
    public String chooseScreening(@PathVariable Long screeningId,
                                  Model model,
                                  RedirectAttributes redirectAttrs) {
        Optional<Pair<Screening, List<Seat>>> screening = screeningService.getScreeningIfAvailable(screeningId);
        if (screening.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMessage", "Brak dostępnych miejsc na wybrany seans");
            return "redirect:/screenings";
        }
        model.addAttribute("screening", screening.get().getFirst());
        model.addAttribute("takenSeats", screening.get().getSecond());
        return "purchase/screening-seats";
    }
}
