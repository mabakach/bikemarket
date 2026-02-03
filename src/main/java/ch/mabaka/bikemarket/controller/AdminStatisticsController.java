package ch.mabaka.bikemarket.controller;

import ch.mabaka.bikemarket.model.*;
import ch.mabaka.bikemarket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.LocaleResolver;

@Controller
@RequestMapping("/admin/statistics")
public class AdminStatisticsController {
    @Autowired
    private AdminStatisticsService statisticsService;
    @Autowired
    private ImprintService imprintService;
    @Autowired
    private PrivacyPolicyService privacyPolicyService;
    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping
    public String showStatistics(Model model, HttpServletRequest request) {
        List<BikeMarketEvent> events = statisticsService.getAllEvents();
        model.addAttribute("events", events);
        String lang = localeResolver.resolveLocale(request).getLanguage();
        boolean hasImprint = imprintService.getImprint(lang) != null && imprintService.getImprint(lang).getContent() != null && !imprintService.getImprint(lang).getContent().trim().isEmpty();
        boolean hasPrivacy = privacyPolicyService.getPolicy(lang) != null && privacyPolicyService.getPolicy(lang).getContent() != null && !privacyPolicyService.getPolicy(lang).getContent().trim().isEmpty();
        model.addAttribute("hasImprint", hasImprint);
        model.addAttribute("hasPrivacy", hasPrivacy);
        return "admin/statistics/list";
    }

    @GetMapping("/event/{eventId}")
    public String showEventStatistics(@PathVariable Long eventId, Model model) {
        BikeMarketEvent event = statisticsService.getAllEvents().stream().filter(e -> e.getId().equals(eventId)).findFirst().orElse(null);
        if (event == null) return "redirect:/admin/statistics";
        Map<String, Object> stats = statisticsService.getStatisticsForEvent(event);
        List<Bike> bikes = statisticsService.getBikesForEvent(event);
        model.addAttribute("event", event);
        model.addAttribute("stats", stats);
        model.addAttribute("bikes", bikes);
        return "admin/statistics/event";
    }

    @GetMapping("/event/{eventId}/export")
    public void exportEventStatisticsCsv(@PathVariable Long eventId, HttpServletResponse response) throws Exception {
        BikeMarketEvent event = statisticsService.getAllEvents().stream().filter(e -> e.getId().equals(eventId)).findFirst().orElse(null);
        if (event == null) return;
        List<Bike> bikes = statisticsService.getBikesForEvent(event);
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=event-" + eventId + "-statistics.csv");
        PrintWriter writer = response.getWriter();
        writer.println("Beschreibung,Verkäufer,Status,Verkaufspreis,Provision,Erlös");
        for (Bike bike : bikes) {
            String salePrice = bike.getSalePrice() != null ? bike.getSalePrice().toString() : "";
            String commission = bike.getCommission() != null ? bike.getCommission().toString() : "";
            String erloes = (bike.getSalePrice() != null && bike.getCommission() != null) ? bike.getSalePrice().subtract(bike.getCommission()).toString() : "";
            writer.printf("%s,%s,%s,%s,%s,%s\n",
                bike.getDescription(),
                bike.getOwner().getName(),
                bike.getStatus().name(),
                salePrice,
                commission,
                erloes);
        }
        writer.flush();
    }
}