package ch.mabaka.bikemarket.repository;

import ch.mabaka.bikemarket.model.BikeMarketEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeMarketEventRepository extends JpaRepository<BikeMarketEvent, Long> {
}
