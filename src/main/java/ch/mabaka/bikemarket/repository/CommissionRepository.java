package ch.mabaka.bikemarket.repository;

import ch.mabaka.bikemarket.model.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionRepository extends JpaRepository<Commission, Long> {
}
