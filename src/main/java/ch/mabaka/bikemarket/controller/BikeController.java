package ch.mabaka.bikemarket.controller;

import ch.mabaka.bikemarket.config.CurrencyConfig;
import ch.mabaka.bikemarket.model.Bike;
import ch.mabaka.bikemarket.model.BikeMarketEvent;
import ch.mabaka.bikemarket.model.User;
import ch.mabaka.bikemarket.service.BikeService;
import ch.mabaka.bikemarket.service.BikeMarketEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import java.math.BigDecimal;

@Controller
@RequestMapping("/bikes")
public class BikeController {
    @Autowired
    private BikeService bikeService;
    @Autowired
    private BikeMarketEventService eventService;
    @Autowired
    private CurrencyConfig currencyConfig;

    @GetMapping
    public String listBikes(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("bikes", bikeService.findByOwner(user));
        return "bikes/list";
    }

    @GetMapping("/new")
    public String showRegisterForm(Model model) {
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("bike", new Bike());
        model.addAttribute("currency", currencyConfig.getCurrency());
        return "bikes/form";
    }

    @PostMapping
    public String registerBike(@AuthenticationPrincipal User user,
                              @RequestParam Long eventId,
                              @Valid Bike bike,
                              BindingResult bindingResult,
                              Model model) {
        BikeMarketEvent event = eventService.findById(eventId).orElseThrow();
        bike.setOwner(user);
        bike.setEvent(event);
        if (bindingResult.hasErrors()) {
            model.addAttribute("events", eventService.findAll());
            model.addAttribute("currency", currencyConfig.getCurrency());
            return "bikes/form";
        }
        bikeService.registerBike(user, event, bike.getDescription(), bike.getPrice());
        return "redirect:/bikes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, @AuthenticationPrincipal User user, Model model) {
        Bike bike = bikeService.findById(id).orElseThrow();
        if (!bikeService.canEdit(bike, user)) {
            return "redirect:/bikes";
        }
        model.addAttribute("bike", bike);
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("currency", currencyConfig.getCurrency());
        return "bikes/form";
    }

    @PostMapping("/edit/{id}")
    public String updateBike(@PathVariable Long id,
                             @AuthenticationPrincipal User user,
                             @RequestParam Long eventId,
                             @RequestParam String description,
                             @RequestParam BigDecimal price) {
        Bike bike = bikeService.findById(id).orElseThrow();
        if (!bikeService.canEdit(bike, user)) {
            return "redirect:/bikes";
        }
        BikeMarketEvent event = eventService.findById(eventId).orElseThrow();
        bike.setEvent(event);
        bike.setDescription(description);
        bike.setPrice(price);
        bikeService.updateBike(bike);
        return "redirect:/bikes";
    }

    @PostMapping("/delete/{id}")
    public String deleteBike(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Bike bike = bikeService.findById(id).orElseThrow();
        if (!bikeService.canEdit(bike, user)) {
            return "redirect:/bikes";
        }
        bikeService.deleteBike(id);
        return "redirect:/bikes";
    }
}