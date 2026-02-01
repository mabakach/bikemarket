package ch.mabaka.bikemarket.repository;

import ch.mabaka.bikemarket.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
