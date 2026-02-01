package ch.mabaka.bikemarket.service;

import ch.mabaka.bikemarket.model.PrivacyPolicy;
import ch.mabaka.bikemarket.repository.PrivacyPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivacyPolicyService {
    @Autowired
    private PrivacyPolicyRepository privacyPolicyRepository;

    public PrivacyPolicy getPolicy(String language) {
        Optional<PrivacyPolicy> policyOpt = privacyPolicyRepository.findAll().stream()
            .filter(p -> p.getLanguage().equals(language))
            .findFirst();
        if (policyOpt.isPresent()) {
            return policyOpt.get();
        }
        Optional<PrivacyPolicy> germanPolicy = privacyPolicyRepository.findAll().stream()
            .filter(p -> p.getLanguage().equals("de"))
            .findFirst();
        if (germanPolicy.isPresent()) {
            return germanPolicy.get();
        }
        PrivacyPolicy policy = new PrivacyPolicy();
        policy.setLanguage("de");
        policy.setContent("Dies ist eine Dummy-Datenschutzerklärung für die BikeMarket Applikation.");
        return privacyPolicyRepository.save(policy);
    }

    public void updatePolicy(String language, String content) {
        PrivacyPolicy policy = getPolicy(language);
        policy.setContent(content);
        privacyPolicyRepository.save(policy);
    }
}
