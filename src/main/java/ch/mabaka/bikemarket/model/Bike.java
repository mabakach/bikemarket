package ch.mabaka.bikemarket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User owner;
    @ManyToOne(optional = false)
    private BikeMarketEvent event;
    @NotNull
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String description;
    @NotNull
    @Min(1)
    @Column(nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Status status = Status.REGISTERED;
    private BigDecimal salePrice;
    private BigDecimal commission;
    private boolean returned = false;
    private boolean paidOut = false;

    public enum Status {
        REGISTERED, RECEIVED, SOLD, RETURNED
    }

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
    public BikeMarketEvent getEvent() { return event; }
    public void setEvent(BikeMarketEvent event) { this.event = event; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
    public BigDecimal getCommission() { return commission; }
    public void setCommission(BigDecimal commission) { this.commission = commission; }
    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }
    public boolean isPaidOut() { return paidOut; }
    public void setPaidOut(boolean paidOut) { this.paidOut = paidOut; }
}