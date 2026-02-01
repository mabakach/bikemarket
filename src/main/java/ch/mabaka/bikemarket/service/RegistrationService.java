package ch.mabaka.bikemarket.service;

import ch.mabaka.bikemarket.model.Role;
import ch.mabaka.bikemarket.model.User;
import ch.mabaka.bikemarket.model.PrivacyPolicyAcceptance;
import ch.mabaka.bikemarket.repository.RoleRepository;
import ch.mabaka.bikemarket.repository.UserRepository;
import ch.mabaka.bikemarket.repository.PrivacyPolicyAcceptanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivacyPolicyAcceptanceRepository privacyPolicyAcceptanceRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public boolean registerBikeSeller(String name, String email, String rawPassword, boolean privacyAccepted) {
        if (!privacyAccepted || userRepository.existsByEmail(email)) {
            return false;
        }
        Role sellerRole = roleRepository.findByName("BIKESELLER").orElseGet(() -> {
            Role r = new Role();
            r.setName("BIKESELLER");
            return roleRepository.save(r);
        });
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEnabled(true);
        user.setPrivacyAccepted(true);
        user.setRoles(new HashSet<>());
        user.getRoles().add(sellerRole);
        userRepository.save(user);
        PrivacyPolicyAcceptance acceptance = new PrivacyPolicyAcceptance();
        acceptance.setUser(user);
        acceptance.setAcceptedAt(LocalDateTime.now());
        privacyPolicyAcceptanceRepository.save(acceptance);
        return true;
    }

    public boolean adminExists() {
        return userRepository.findAll().stream()
            .flatMap(u -> u.getRoles().stream())
            .anyMatch(r -> "ADMIN".equals(r.getName()));
    }

    @Transactional
    public boolean registerAdmin(String name, String email, String rawPassword, boolean privacyAccepted) {
        if (!privacyAccepted || userRepository.existsByEmail(email) || adminExists()) {
            return false;
        }
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role r = new Role();
            r.setName("ADMIN");
            return roleRepository.save(r);
        });
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEnabled(true);
        user.setPrivacyAccepted(true);
        user.setRoles(new HashSet<>());
        user.getRoles().add(adminRole);
        userRepository.save(user);
        PrivacyPolicyAcceptance acceptance = new PrivacyPolicyAcceptance();
        acceptance.setUser(user);
        acceptance.setAcceptedAt(LocalDateTime.now());
        privacyPolicyAcceptanceRepository.save(acceptance);
        return true;
    }
}