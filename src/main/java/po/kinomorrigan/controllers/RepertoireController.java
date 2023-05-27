package po.kinomorrigan.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import po.kinomorrigan.models.Film;
import po.kinomorrigan.models.Repertoire;
import po.kinomorrigan.services.FilmService;
import po.kinomorrigan.services.RepertoireService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/repertoire")
public class RepertoireController {
    private RepertoireService repertoireService;
    private FilmService filmService;

    public RepertoireController(RepertoireService repertoireService, FilmService filmService) {
        this.repertoireService = repertoireService;
        this.filmService = filmService;
    }

    @GetMapping
    public String displayRepertoireList(Model model) {
        List<Repertoire> repertoires = repertoireService.getRepertoireList();
        model.addAttribute("screenings", repertoires.get(0).getScreenings());
        return "repertoire/repertoire-list";
    }

    @GetMapping("/{filmId}")
    public String chooseRepertoireScreening(@PathVariable Long filmId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Film film = filmService.getFilmById(filmId);

            if (film == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Nie znaleziono filmu");
                return "redirect:/repertoire";
            }

            model.addAttribute("film", film);
            return "repertoire/film-details";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nie znaleziono filmu");
            return "redirect:/repertoire";
        }
    }

    @PostMapping("/{filmId}/update-film")
    public String updateFilm(@PathVariable Long filmId, @RequestParam String description, Model model) {
        Film film = filmService.getFilmById(filmId);
        filmService.updateFilm(filmId, description);
        List<Repertoire> repertoires = repertoireService.getRepertoireList();
        model.addAttribute("screenings", repertoires.get(0).getScreenings());
        return "repertoire/repertoire-list";
    }

    @GetMapping("/film-creator")
    public String filmCreatorScreening() {
        return "repertoire/film-creator";
    }

    @PostMapping("/create-film")
    public String createFilm(@RequestParam String description,
                             @RequestParam String title,
                             @RequestParam String ageCategory,
                             @RequestParam int duration,
                             @RequestParam LocalDate premiere,
                             @RequestParam String genre,
                             Model model) {
        filmService.createNewFilm(title, description, ageCategory, duration, premiere, genre);
        List<Repertoire> repertoires = repertoireService.getRepertoireList();
        model.addAttribute("screenings", repertoires.get(0).getScreenings());
        return "repertoire/repertoire-list";
    }
}
