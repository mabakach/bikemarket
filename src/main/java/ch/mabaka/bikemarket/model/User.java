package ch.mabaka.bikemarket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull
    @Size(min = 6, max = 100)
    @Column(nullable = false)
    private String password;
    @NotNull
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    private boolean enabled = true;
    private boolean privacyAccepted = false;

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public boolean isPrivacyAccepted() { return privacyAccepted; }
    public void setPrivacyAccepted(boolean privacyAccepted) { this.privacyAccepted = privacyAccepted; }
}