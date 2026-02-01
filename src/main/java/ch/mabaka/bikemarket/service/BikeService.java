package ch.mabaka.bikemarket.service;

import ch.mabaka.bikemarket.model.Bike;
import ch.mabaka.bikemarket.model.BikeMarketEvent;
import ch.mabaka.bikemarket.model.User;
import ch.mabaka.bikemarket.repository.BikeRepository;
import ch.mabaka.bikemarket.repository.BikeMarketEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BikeService {
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private BikeMarketEventRepository eventRepository;
    @Autowired
    private EmailService emailService;

    public List<Bike> findByOwner(User owner) {
        return bikeRepository.findAll().stream()
            .filter(b -> b.getOwner().equals(owner))
            .toList();
    }

    public List<Bike> findByEvent(BikeMarketEvent event) {
        return bikeRepository.findAll().stream()
            .filter(b -> b.getEvent().equals(event))
            .toList();
    }

    public Optional<Bike> findById(Long id) {
        return bikeRepository.findById(id);
    }

    public Bike registerBike(User owner, BikeMarketEvent event, String description, BigDecimal price) {
        Bike bike = new Bike();
        bike.setOwner(owner);
        bike.setEvent(event);
        bike.setDescription(description);
        bike.setPrice(price);
        bike.setStatus(Bike.Status.REGISTERED);
        return bikeRepository.save(bike);
    }

    public Bike updateBike(Bike bike) {
        return bikeRepository.save(bike);
    }

    public void deleteBike(Long id) {
        bikeRepository.deleteById(id);
    }

    public boolean canEdit(Bike bike, User user) {
        return bike.getOwner().equals(user) && bike.getStatus() == Bike.Status.REGISTERED;
    }

    public Bike markReceived(Bike bike) {
        bike.setStatus(Bike.Status.RECEIVED);
        Bike saved = bikeRepository.save(bike);
        // E-Mail an BikeSeller
        String subject = "Fahrrad entgegengenommen: " + bike.getDescription();
        String text = "Ihr Fahrrad '" + bike.getDescription() + "' wurde am " + java.time.LocalDate.now() + " für die Börse '" + bike.getEvent().getName() + "' entgegengenommen.";
        emailService.sendMail(bike.getOwner().getEmail(), subject, text);
        return saved;
    }

    public Bike markSold(Bike bike, java.math.BigDecimal salePrice, java.math.BigDecimal commission) {
        bike.setStatus(Bike.Status.SOLD);
        bike.setSalePrice(salePrice);
        bike.setCommission(commission);
        return bikeRepository.save(bike);
    }

    public Bike markReturned(Bike bike) {
        bike.setStatus(Bike.Status.RETURNED);
        bike.setReturned(true);
        Bike saved = bikeRepository.save(bike);
        // E-Mail an BikeSeller
        String subject = "Fahrrad zurückgegeben: " + bike.getDescription();
        String text = "Ihr Fahrrad '" + bike.getDescription() + "' wurde am " + java.time.LocalDate.now() + " für die Börse '" + bike.getEvent().getName() + "' zurückgegeben.";
        emailService.sendMail(bike.getOwner().getEmail(), subject, text);
        return saved;
    }
}