package ch.mabaka.bikemarket.controller;

import ch.mabaka.bikemarket.service.BikeMarketEventService;
import ch.mabaka.bikemarket.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private BikeMarketEventService eventService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!eventService.hasFutureOrTodayEvent()) {
            model.addAttribute("error", "Eine Registrierung ist aktuell nicht möglich, da keine zukünftige oder heutige Fahrradbörse existiert.");
            return "register";
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam(required = false) Boolean privacyAccepted,
                               Model model) {
        boolean success = registrationService.registerBikeSeller(name, email, password, Boolean.TRUE.equals(privacyAccepted));
        if (success) {
            model.addAttribute("message", "Registrierung erfolgreich! Bitte loggen Sie sich ein.");
            return "login";
        } else {
            model.addAttribute("error", "Registrierung fehlgeschlagen! E-Mail bereits vergeben oder Datenschutzerklärung nicht akzeptiert.");
            return "register";
        }
    }

    @GetMapping({"/", "/login"})
    public String showLogin(Model model) {
        if (!registrationService.adminExists()) {
            return "redirect:/admin-setup";
        }
        return "login";
    }
}