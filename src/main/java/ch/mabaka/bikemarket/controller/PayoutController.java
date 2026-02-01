package ch.mabaka.bikemarket.controller;

import ch.mabaka.bikemarket.config.CurrencyConfig;
import ch.mabaka.bikemarket.model.Bike;
import ch.mabaka.bikemarket.model.Receipt;
import ch.mabaka.bikemarket.service.BikeService;
import ch.mabaka.bikemarket.service.PayoutAndReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payout")
public class PayoutController {
    @Autowired
    private BikeService bikeService;
    @Autowired
    private PayoutAndReceiptService payoutService;
    @Autowired
    private CurrencyConfig currencyConfig;

    @GetMapping
    public String payoutList(Model model) {
        model.addAttribute("bikes", payoutService.findSoldUnpaidBikes());
        return "payout/list";
    }

    @PostMapping("/pay/{bikeId}")
    public String markPaidOut(@PathVariable Long bikeId) {
        Bike bike = bikeService.findById(bikeId).orElseThrow();
        payoutService.markPaidOut(bike);
        return "redirect:/payout";
    }

    @GetMapping("/receipt/{bikeId}")
    public String showReceipt(@PathVariable Long bikeId, Model model) {
        Bike bike = bikeService.findById(bikeId).orElseThrow();
        Receipt receipt = payoutService.createReceipt(bike);
        model.addAttribute("receipt", receipt);
        model.addAttribute("currency", currencyConfig.getCurrency());
        return "payout/receipt";
    }
}