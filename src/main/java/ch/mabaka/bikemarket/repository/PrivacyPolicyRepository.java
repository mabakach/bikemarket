package ch.mabaka.bikemarket.repository;

import ch.mabaka.bikemarket.model.PrivacyPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivacyPolicyRepository extends JpaRepository<PrivacyPolicy, Long> {
}
