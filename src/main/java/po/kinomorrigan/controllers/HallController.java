package po.kinomorrigan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import po.kinomorrigan.models.Hall;
import po.kinomorrigan.services.HallService;

import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping("/halls")
public class HallController
{
    private final HallService hallService;

    public HallController(HallService hallService)
    {
        this.hallService = hallService;
    }

    @GetMapping
    public String displayHallsList(Model model)
    {
        List<Hall> halls = hallService.getHalls();
        model.addAttribute("halls", halls);
        return "admin/halls-list";
    }

    @GetMapping("/{hallId}")
    public String displayHallDetails(@PathVariable Long hallId, Model model)
    {
        Hall hall = hallService.getHallById(hallId);
        model.addAttribute("hall", hall);
        return "admin/hall-details";
    }

    @PostMapping("{hallId}/edit-seats")
    public String updateNumberOfSeats(@PathVariable Long hallId, @RequestParam Integer newSeatsNumber, Model model, RedirectAttributes redirectAttrs)
    {
        Optional<Hall> updatedHall = hallService.validateNumberOfSeats(hallId, newSeatsNumber);
        if(updatedHall.isEmpty())
        {
            redirectAttrs.addFlashAttribute("errorMessage", "Wprowadzono niepoprawna liczbe miejsc");
            return "redirect:/halls/" + hallId;
        }
        else
        {
            model.addAttribute("hall", updatedHall.get());
            model.addAttribute("alertPositive", "Liczba miejsc zaktualizowania pomyslnie");
            return "admin/hall-details";
        }
    }


}
