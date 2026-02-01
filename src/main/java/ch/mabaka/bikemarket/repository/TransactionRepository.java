package ch.mabaka.bikemarket.repository;

import ch.mabaka.bikemarket.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
