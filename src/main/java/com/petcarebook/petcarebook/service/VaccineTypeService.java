package com.petcarebook.petcarebook.service;

import com.petcarebook.petcarebook.entity.VaccineType;
import com.petcarebook.petcarebook.exception.BusinessRuleException;
import com.petcarebook.petcarebook.exception.ResourceNotFoundException;
import com.petcarebook.petcarebook.repository.VaccineTypeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VaccineTypeService {
    private final VaccineTypeRepository repository;
    public VaccineTypeService(VaccineTypeRepository repository) { this.repository = repository; }

    public List<VaccineType> findAll() { return repository.findAll(); }
    public VaccineType findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vaccine type not found: " + id)); }
    public VaccineType save(VaccineType vaccineType) {
        repository.findByNameIgnoreCase(vaccineType.getName()).ifPresent(existing -> {
            if (!existing.getId().equals(vaccineType.getId())) {
                throw new BusinessRuleException("A vaccine type with this name already exists.");
            }
        });
        return repository.save(vaccineType);
    }
}
