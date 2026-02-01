package ch.mabaka.bikemarket.service;

import ch.mabaka.bikemarket.model.Bike;
import ch.mabaka.bikemarket.model.Receipt;
import ch.mabaka.bikemarket.repository.BikeRepository;
import ch.mabaka.bikemarket.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PayoutAndReceiptService {
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CurrencyConfig currencyConfig;

    public List<Bike> findSoldUnpaidBikes() {
        return bikeRepository.findAll().stream()
            .filter(b -> b.getStatus() == Bike.Status.SOLD && !b.isPaidOut())
            .toList();
    }

    @Transactional
    public void markPaidOut(Bike bike) {
        bike.setPaidOut(true);
        bikeRepository.save(bike);
        // E-Mail mit Quittung an BikeSeller
        Receipt receipt = createReceipt(bike);
        String currency = currencyConfig.getCurrency();
        String subject = "Auszahlung für Fahrrad: " + bike.getDescription();
        String text = "Ihr Fahrrad '" + bike.getDescription() + "' wurde verkauft.\n" +
                "Verkaufspreis: " + bike.getSalePrice() + " " + currency + "\n" +
                "Provision: " + bike.getCommission() + " " + currency + "\n" +
                "Erlös: " + bike.getSalePrice().subtract(bike.getCommission()) + " " + currency + "\n" +
                "Quittungsnummer: " + receipt.getReceiptNumber() + "\n" +
                "Markt: " + bike.getEvent().getName();
        emailService.sendMail(bike.getOwner().getEmail(), subject, text);
    }

    public Receipt createReceipt(Bike bike) {
        Receipt receipt = new Receipt();
        receipt.setBike(bike);
        receipt.setIssuedAt(LocalDateTime.now());
        receipt.setReceiptNumber("R-" + bike.getId() + "-" + System.currentTimeMillis());
        receipt.setSellerName(bike.getOwner().getName());
        receipt.setBikeDescription(bike.getDescription());
        receipt.setSalePrice(bike.getSalePrice());
        receipt.setCommission(bike.getCommission());
        receipt.setMarketName(bike.getEvent().getName());
        return receiptRepository.save(receipt);
    }

    public List<Receipt> findReceiptsForBike(Bike bike) {
        return receiptRepository.findAll().stream()
            .filter(r -> r.getBike().equals(bike))
            .toList();
    }
}