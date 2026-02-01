package ch.mabaka.bikemarket.controller;

import ch.mabaka.bikemarket.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminSetupController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/admin-setup")
    public String showAdminSetup(Model model) {
        if (registrationService.adminExists()) {
            return "redirect:/login";
        }
        return "admin-setup";
    }

    @PostMapping("/admin-setup")
    public String setupAdmin(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam(required = false) Boolean privacyAccepted,
                             Model model) {
        boolean success = registrationService.registerAdmin(name, email, password, Boolean.TRUE.equals(privacyAccepted));
        if (success) {
            model.addAttribute("message", "Administrator erfolgreich eingerichtet! Bitte loggen Sie sich ein.");
            return "login";
        } else {
            model.addAttribute("error", "Ein Administrator existiert bereits oder die E-Mail ist vergeben oder die Datenschutzerklärung wurde nicht akzeptiert.");
            return "admin-setup";
        }
    }
}
