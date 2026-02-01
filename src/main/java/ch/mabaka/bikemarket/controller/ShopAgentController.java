package ch.mabaka.bikemarket.controller;

import ch.mabaka.bikemarket.model.Bike;
import ch.mabaka.bikemarket.model.BikeMarketEvent;
import ch.mabaka.bikemarket.service.BikeService;
import ch.mabaka.bikemarket.service.BikeMarketEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agent")
public class ShopAgentController {
    @Autowired
    private BikeService bikeService;
    @Autowired
    private BikeMarketEventService eventService;

    @GetMapping("/event/{eventId}")
    public String listBikesForEvent(@PathVariable Long eventId, Model model) {
        BikeMarketEvent event = eventService.findById(eventId).orElseThrow();
        model.addAttribute("event", event);
        model.addAttribute("bikes", bikeService.findByEvent(event));
        return "agent/bikes-for-event";
    }

    @PostMapping("/accept/{bikeId}")
    public String acceptBike(@PathVariable Long bikeId, @RequestParam(required = false) String remark) {
        Bike bike = bikeService.findById(bikeId).orElseThrow();
        if (bike.getStatus() == Bike.Status.REGISTERED) {
            bike.setStatus(Bike.Status.RECEIVED);
            // Optional: bike.setRemark(remark); // falls Bemerkung gewünscht
            bikeService.updateBike(bike);
        }
        return "redirect:/agent/event/" + bike.getEvent().getId();
    }

    @PostMapping("/sell/{bikeId}")
    public String sellBike(@PathVariable Long bikeId,
                           @RequestParam java.math.BigDecimal salePrice,
                           @RequestParam java.math.BigDecimal commission) {
        Bike bike = bikeService.findById(bikeId).orElseThrow();
        if (bike.getStatus() == Bike.Status.RECEIVED) {
            bikeService.markSold(bike, salePrice, commission);
        }
        return "redirect:/agent/event/" + bike.getEvent().getId();
    }

    @PostMapping("/return/{bikeId}")
    public String returnBike(@PathVariable Long bikeId) {
        Bike bike = bikeService.findById(bikeId).orElseThrow();
        if (bike.getStatus() == Bike.Status.RECEIVED) {
            bikeService.markReturned(bike);
        }
        return "redirect:/agent/event/" + bike.getEvent().getId();
    }
}