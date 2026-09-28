package com.petcarebook.petcarebook.controller;

import com.petcarebook.petcarebook.repository.*;
import com.petcarebook.petcarebook.service.VaccinationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class DashboardController {
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VaccineTypeRepository vaccineTypeRepository;
    private final VaccinationRecordRepository recordRepository;
    private final VaccinationService vaccinationService;

    public DashboardController(OwnerRepository ownerRepository, PetRepository petRepository,
                               VaccineTypeRepository vaccineTypeRepository, VaccinationRecordRepository recordRepository,
                               VaccinationService vaccinationService) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.vaccineTypeRepository = vaccineTypeRepository;
        this.recordRepository = recordRepository;
        this.vaccinationService = vaccinationService;
    }

    @GetMapping("/api/dashboard")
    public Map<String, Object> dashboard() {
        return Map.of(
            "owners", ownerRepository.count(),
            "pets", petRepository.count(),
            "vaccineTypes", vaccineTypeRepository.count(),
            "vaccinationRecords", recordRepository.count(),
            "dueNext7Days", vaccinationService.countDueWithinNext7Days()
        );
    }
}
