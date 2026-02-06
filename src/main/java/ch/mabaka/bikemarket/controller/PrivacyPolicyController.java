package ch.mabaka.bikemarket.controller;

import ch.mabaka.bikemarket.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class PrivacyPolicyController {
    @Autowired
    private PrivacyPolicyService privacyPolicyService;
    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/privacy")
    public String showPolicy(Model model, HttpServletRequest request) {
        String lang = localeResolver.resolveLocale(request).getLanguage();
        model.addAttribute("policy", privacyPolicyService.getPolicy(lang).getContent());
        return "privacy";
    }

    @PreAuthorize("hasRole(T(ch.mabaka.bikemarket.security.RoleConstants).ADMIN)")
    @GetMapping("/admin/privacy/edit")
    public String editPolicyForm(Model model, HttpServletRequest request) {
        String lang = localeResolver.resolveLocale(request).getLanguage();
        model.addAttribute("policy", privacyPolicyService.getPolicy(lang).getContent());
        model.addAttribute("language", lang);
        return "admin/privacy-form";
    }

    @PreAuthorize("hasRole(T(ch.mabaka.bikemarket.security.RoleConstants).ADMIN)")
    @PostMapping("/admin/privacy/edit")
    public String updatePolicy(@RequestParam String content, @RequestParam String language) {
        privacyPolicyService.updatePolicy(language, content);
        return "redirect:/privacy";
    }
}