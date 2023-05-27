package po.kinomorrigan.controllers;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import po.kinomorrigan.models.User;
import po.kinomorrigan.models.enums.PaymentType;
import po.kinomorrigan.services.LoyaltyCardService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/loyalty-card")
public class LoyaltyCardController
{
    private final LoyaltyCardService loyaltyCardService;
    Logger logger = LoggerFactory.getLogger(LoyaltyCardController.class);
    User user = null;

    public LoyaltyCardController(LoyaltyCardService loyaltyCardService)
    {
        this.loyaltyCardService = loyaltyCardService;
    }

    @GetMapping
    public String displayLoyaltyCardForm()
    {
        logger.info("Im herre0");
        return "loyalty-card/loyalty-card-form";
    }

    @PostMapping("/fill-data")
    public String validateData(@RequestParam String login, Model model, RedirectAttributes redirectAttrs)
    {
        Optional<User> reqUser = loyaltyCardService.validateUser(login);
        if(reqUser.isEmpty())
        {
            redirectAttrs.addFlashAttribute("errorMessage", "Podaj poprawny login");
            return "redirect:/loyalty-card";
        }
        user = loyaltyCardService.getUserByLogin(login);
        if(loyaltyCardService.userHasLoyaltyCard(user))
        {
            logger.info("He has a card");
            redirectAttrs.addFlashAttribute("errorMessage", "Uzytkownik posiada juz karte lojalnosciowa");
            return "redirect:/loyalty-card";
        }
        else
        {
            model.addAttribute("login", login);
            return "loyalty-card/policy";
        }
    }

    @GetMapping("/{login}/payment-methods")
    public String getPaymentMethods(Model model)
    {
        model.addAttribute("user", user);
        return "loyalty-card/payment-methods";
    }

    @GetMapping("/payment-type")
    public String payWithMethod(HttpSession session, @RequestParam PaymentType method,
                                Model model,
                                RedirectAttributes redirectAttrs) {
        logger.info("mapped correctly");
        if (method == PaymentType.CARD) {
            boolean isApproved = loyaltyCardService.createLoyaltyCard(user.getLogin());
            if (!isApproved) {
                logger.info("nope");
                model.addAttribute("errorMessage", "Transakcja nie powiodła się");
            } else
            {
                logger.info("yes");
                String positiveAlert = "Utworzono kartę dla użytkownnika: " + user.getLogin();
                model.addAttribute("alertPositive", positiveAlert);

            }
           return  "loyalty-card/payment-method-card";
        } else {
            boolean isApproved = loyaltyCardService.createLoyaltyCard(user.getLogin());
            if (!isApproved) {
                model.addAttribute("errorMessage", "Transakcja nie powiodła się");
            } else {
                model.addAttribute("alertPositive", "Utworzono kartę dla użytkownnika: "+ user.getLogin());
            }
            model.addAttribute("user", user);
            return "loyalty-card/payment-method-transfer";
        }
    }


}
