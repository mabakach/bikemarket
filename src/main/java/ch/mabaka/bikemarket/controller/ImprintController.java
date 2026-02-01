package ch.mabaka.bikemarket.controller;

import ch.mabaka.bikemarket.service.ImprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class ImprintController {
    @Autowired
    private ImprintService imprintService;

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/imprint")
    public String showImprint(Model model, javax.servlet.http.HttpServletRequest request) {
        String lang = localeResolver.resolveLocale(request).getLanguage();
        model.addAttribute("imprint", imprintService.getImprint(lang).getContent());
        return "imprint";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/imprint/edit")
    public String editImprintForm(Model model, javax.servlet.http.HttpServletRequest request) {
        String lang = localeResolver.resolveLocale(request).getLanguage();
        model.addAttribute("imprint", imprintService.getImprint(lang).getContent());
        model.addAttribute("language", lang);
        return "admin/imprint-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/imprint/edit")
    public String updateImprint(@RequestParam String content, @RequestParam String language) {
        imprintService.updateImprint(language, content);
        return "redirect:/imprint";
    }
}