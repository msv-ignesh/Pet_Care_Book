package com.petcarebook.petcarebook.repository;
import com.petcarebook.petcarebook.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface VaccineTypeRepository extends JpaRepository<VaccineType, Long> {
    Optional<VaccineType> findByNameIgnoreCase(String name);
}
