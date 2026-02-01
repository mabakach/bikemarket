package ch.mabaka.bikemarket.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal rate; // z.B. 0.10 für 10%
    @Column(nullable = false)
    private String description;

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
