package ch.mabaka.bikemarket.service;

import ch.mabaka.bikemarket.model.Role;
import ch.mabaka.bikemarket.model.User;
import ch.mabaka.bikemarket.model.PrivacyPolicyAcceptance;
import ch.mabaka.bikemarket.model.RoleName;
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
    public boolean registerBikeSeller(final String name, final String email, final String rawPassword, final boolean privacyAccepted) {
        if (!privacyAccepted || userRepository.existsByEmail(email)) {
            return false;
        }
        Role sellerRole = roleRepository.findByName(RoleName.BIKESELLER.getValue()).orElseGet(() -> createNewRole(RoleName.BIKESELLER));
        createNewUser(name, email, rawPassword, sellerRole);
        return true;
    }

    public boolean adminExists() {
        return userRepository.findAll().stream()
            .flatMap(u -> u.getRoles().stream())
            .anyMatch(r -> RoleName.ADMIN.getValue().equals(r.getName()));
    }

    @Transactional
    public boolean registerAdmin(final String name, final String email, final String rawPassword, final boolean privacyAccepted) {
        if (!privacyAccepted || userRepository.existsByEmail(email) || adminExists()) {
            return false;
        }
        Role adminRole = roleRepository.findByName(RoleName.ADMIN.getValue()).orElseGet(() -> createNewRole(RoleName.ADMIN));
        createNewUser(name, email, rawPassword, adminRole);
        return true;
    }

	private Role createNewRole(final RoleName roleName) {
		Role r = new Role();
		r.setName(roleName.getValue());
		return roleRepository.save(r);
	}

	private void createNewUser(final String name, final String email, final String rawPassword, Role role) {
		User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEnabled(true);
        user.setPrivacyAccepted(true);
        user.setRoles(new HashSet<>());
        user.getRoles().add(role);
        userRepository.save(user);
        PrivacyPolicyAcceptance acceptance = new PrivacyPolicyAcceptance();
        acceptance.setUser(user);
        acceptance.setAcceptedAt(LocalDateTime.now());
        privacyPolicyAcceptanceRepository.save(acceptance);
	}
}