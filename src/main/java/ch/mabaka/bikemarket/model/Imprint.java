package ch.mabaka.bikemarket.model;

import jakarta.persistence.*;

@Entity
public class Imprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false, length = 5)
    private String language = "de";

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}