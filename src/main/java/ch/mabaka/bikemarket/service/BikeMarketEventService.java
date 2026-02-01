package ch.mabaka.bikemarket.service;

import ch.mabaka.bikemarket.model.BikeMarketEvent;
import ch.mabaka.bikemarket.repository.BikeMarketEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BikeMarketEventService {
    @Autowired
    private BikeMarketEventRepository eventRepository;

    public List<BikeMarketEvent> findAll() {
        return eventRepository.findAll();
    }

    public Optional<BikeMarketEvent> findById(Long id) {
        return eventRepository.findById(id);
    }

    public BikeMarketEvent save(BikeMarketEvent event) {
        return eventRepository.save(event);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    public boolean hasFutureOrTodayEvent() {
        return eventRepository.findAll().stream()
            .anyMatch(e -> !e.getDate().isBefore(LocalDate.now()));
    }
}