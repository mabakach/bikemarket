package ch.mabaka.bikemarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "ch.mabaka.bikemarket.model")
@EnableJpaRepositories(basePackages = "ch.mabaka.bikemarket.repository")
public class JpaConfig {
}
