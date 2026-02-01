package ch.mabaka.bikemarket.controller;

import ch.mabaka.bikemarket.model.BikeMarketEvent;
import ch.mabaka.bikemarket.service.BikeMarketEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/events")
public class BikeMarketEventController {
    @Autowired
    private BikeMarketEventService eventService;

    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "events/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new BikeMarketEvent());
        return "events/form";
    }

    @PostMapping
    public String createEvent(@ModelAttribute BikeMarketEvent event) {
        eventService.save(event);
        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BikeMarketEvent event = eventService.findById(id).orElseThrow();
        model.addAttribute("event", event);
        return "events/form";
    }

    @PostMapping("/edit/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute BikeMarketEvent event) {
        event.setId(id);
        eventService.save(event);
        return "redirect:/events";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return "redirect:/events";
    }
}
