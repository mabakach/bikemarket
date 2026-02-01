package ch.mabaka.bikemarket.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Bike bike;
    @ManyToOne(optional = false)
    private User shopAgent;
    @Column(nullable = false)
    private LocalDateTime transactionTime;
    @Enumerated(EnumType.STRING)
    private Type type; // SALE or RETURN
    private BigDecimal salePrice;
    private BigDecimal commission;

    public enum Type {
        SALE, RETURN
    }

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Bike getBike() { return bike; }
    public void setBike(Bike bike) { this.bike = bike; }
    public User getShopAgent() { return shopAgent; }
    public void setShopAgent(User shopAgent) { this.shopAgent = shopAgent; }
    public LocalDateTime getTransactionTime() { return transactionTime; }
    public void setTransactionTime(LocalDateTime transactionTime) { this.transactionTime = transactionTime; }
    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }
    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
    public BigDecimal getCommission() { return commission; }
    public void setCommission(BigDecimal commission) { this.commission = commission; }
}
