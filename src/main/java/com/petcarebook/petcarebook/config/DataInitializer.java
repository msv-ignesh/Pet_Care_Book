package com.petcarebook.petcarebook.config;

import com.petcarebook.petcarebook.entity.VaccineType;
import com.petcarebook.petcarebook.repository.VaccineTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seedVaccineTypes(VaccineTypeRepository repository) {
        return args -> {
            seed(repository, "Rabies", 365);
            seed(repository, "DHPP", 365);
            seed(repository, "Bordetella", 365);
            seed(repository, "Feline FVRCP", 365);
            seed(repository, "Deworming", 180);
        };
    }

    private void seed(VaccineTypeRepository repository, String name, int days) {
        if (repository.findByNameIgnoreCase(name).isEmpty()) {
            repository.save(new VaccineType(name, days));
        }
    }
}
