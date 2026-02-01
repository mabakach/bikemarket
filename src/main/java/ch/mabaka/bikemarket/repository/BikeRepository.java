package ch.mabaka.bikemarket.repository;

import ch.mabaka.bikemarket.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, Long> {
}
