package ch.mabaka.bikemarket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class BikeMarketEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull
    @Size(min = 2, max = 100)
    private String name;
    @Column(nullable = false)
    @NotNull
    private LocalDate date;
    private String description;

    @OneToMany(mappedBy = "event")
    private List<Bike> bikes = new ArrayList<>();

    @Column(nullable = false)
    @NotNull
    @Min(0)
    @Max(100)
    private double commissionPercent = 10.0; // Standardwert 10%

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Bike> getBikes() { return bikes; }
    public void setBikes(List<Bike> bikes) { this.bikes = bikes; }
    public double getCommissionPercent() { return commissionPercent; }
    public void setCommissionPercent(double commissionPercent) { this.commissionPercent = commissionPercent; }
}