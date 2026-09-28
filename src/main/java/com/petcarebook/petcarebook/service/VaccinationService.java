package com.petcarebook.petcarebook.service;

import com.petcarebook.petcarebook.dto.VaccinationRequest;
import com.petcarebook.petcarebook.dto.CompleteVaccinationHistoryItem;
import com.petcarebook.petcarebook.entity.*;
import com.petcarebook.petcarebook.exception.BusinessRuleException;
import com.petcarebook.petcarebook.exception.ResourceNotFoundException;
import com.petcarebook.petcarebook.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class VaccinationService {
    private final VaccinationRecordRepository recordRepository;
    private final PetRepository petRepository;
    private final VaccineTypeRepository vaccineTypeRepository;

    public VaccinationService(VaccinationRecordRepository recordRepository,
                              PetRepository petRepository,
                              VaccineTypeRepository vaccineTypeRepository) {
        this.recordRepository = recordRepository;
        this.petRepository = petRepository;
        this.vaccineTypeRepository = vaccineTypeRepository;
    }

    public VaccinationRecord log(VaccinationRequest request) {
        if (request.getDateGiven().isAfter(LocalDate.now())) {
            throw new BusinessRuleException("Vaccination date cannot be in the future.");
        }

        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found: " + request.getPetId()));
        VaccineType vaccineType = vaccineTypeRepository.findById(request.getVaccineTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine type not found: " + request.getVaccineTypeId()));

        VaccinationRecord record = new VaccinationRecord();
        record.setPet(pet);
        record.setVaccineType(vaccineType);
        record.setDateGiven(request.getDateGiven());

        // Required business rule: next due date is calculated from the vaccine's fixed interval.
        record.setNextDueDate(request.getDateGiven().plusDays(vaccineType.getIntervalDays()));

        return recordRepository.save(record);
    }

    public VaccinationRecord findById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccination record not found: " + id));
    }

    public List<CompleteVaccinationHistoryItem> history(Long petId) {
        if (!petRepository.existsById(petId)) {
            throw new ResourceNotFoundException("Pet not found: " + petId);
        }

        LocalDate today = LocalDate.now();

        return recordRepository.findByPetIdOrderByDateGivenAsc(petId).stream()
                // A vaccination date can never be in the future. This also makes
                // the complete-history range explicitly end at today's date.
                .filter(record -> !record.getDateGiven().isAfter(today))
                .map(record -> new CompleteVaccinationHistoryItem(
                        record.getId(),
                        record.getVaccineType().getName(),
                        record.getDateGiven(),
                        record.getNextDueDate(),
                        record.getVaccineType().getIntervalDays(),
                        "Completed"
                ))
                .toList();
    }

    public List<VaccinationRecord> dueWithinNext7Days() {
        LocalDate today = LocalDate.now();
        return recordRepository.findDueBetween(today, today.plusDays(7));
    }

    public long countDueWithinNext7Days() {
        LocalDate today = LocalDate.now();
        return recordRepository.countByNextDueDateBetween(today, today.plusDays(7));
    }
}
