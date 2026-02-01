package ch.mabaka.bikemarket.service;

import ch.mabaka.bikemarket.model.BikeMarketEvent;
import ch.mabaka.bikemarket.model.Bike;
import ch.mabaka.bikemarket.repository.BikeMarketEventRepository;
import ch.mabaka.bikemarket.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AdminStatisticsService {
    @Autowired
    private BikeMarketEventRepository eventRepository;
    @Autowired
    private BikeRepository bikeRepository;

    public List<BikeMarketEvent> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Bike> getBikesForEvent(BikeMarketEvent event) {
        return bikeRepository.findAll().stream()
            .filter(b -> b.getEvent().equals(event))
            .toList();
    }

    public Map<String, Object> getStatisticsForEvent(BikeMarketEvent event) {
        List<Bike> bikes = getBikesForEvent(event);
        long totalBikes = bikes.size();
        long soldBikes = bikes.stream().filter(b -> b.getStatus() == Bike.Status.SOLD).count();
        long returnedBikes = bikes.stream().filter(b -> b.getStatus() == Bike.Status.RETURNED).count();
        long receivedBikes = bikes.stream().filter(b -> b.getStatus() == Bike.Status.RECEIVED).count();
        BigDecimal totalSales = bikes.stream().filter(b -> b.getStatus() == Bike.Status.SOLD && b.getSalePrice() != null).map(Bike::getSalePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCommission = bikes.stream().filter(b -> b.getStatus() == Bike.Status.SOLD && b.getCommission() != null).map(Bike::getCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBikes", totalBikes);
        stats.put("soldBikes", soldBikes);
        stats.put("returnedBikes", returnedBikes);
        stats.put("receivedBikes", receivedBikes);
        stats.put("totalSales", totalSales);
        stats.put("totalCommission", totalCommission);
        return stats;
    }
}
