package ch.mabaka.bikemarket.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Transaction transaction;
    @ManyToOne(optional = false)
    private Bike bike;
    @Column(nullable = false)
    private LocalDateTime issuedAt;
    @Column(nullable = false)
    private String receiptNumber;
    @Column(nullable = false)
    private String sellerName;
    @Column(nullable = false)
    private String bikeDescription;
    @Column(nullable = false)
    private java.math.BigDecimal salePrice;
    @Column(nullable = false)
    private java.math.BigDecimal commission;
    @Column(nullable = false)
    private String marketName;

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Transaction getTransaction() { return transaction; }
    public void setTransaction(Transaction transaction) { this.transaction = transaction; }
    public Bike getBike() { return bike; }
    public void setBike(Bike bike) { this.bike = bike; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }
    public String getSellerName() { return sellerName; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
    public String getBikeDescription() { return bikeDescription; }
    public void setBikeDescription(String bikeDescription) { this.bikeDescription = bikeDescription; }
    public java.math.BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(java.math.BigDecimal salePrice) { this.salePrice = salePrice; }
    public java.math.BigDecimal getCommission() { return commission; }
    public void setCommission(java.math.BigDecimal commission) { this.commission = commission; }
    public String getMarketName() { return marketName; }
    public void setMarketName(String marketName) { this.marketName = marketName; }
}