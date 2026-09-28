package com.petcarebook.petcarebook.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class VaccinationRequest {
    @NotNull(message = "Pet is required")
    private Long petId;

    @NotNull(message = "Vaccine type is required")
    private Long vaccineTypeId;

    @NotNull(message = "Vaccination date is required")
    @PastOrPresent(message = "Vaccination date cannot be in the future")
    private LocalDate dateGiven;

    public Long getPetId() { return petId; }
    public Long getVaccineTypeId() { return vaccineTypeId; }
    public LocalDate getDateGiven() { return dateGiven; }

    public void setPetId(Long petId) { this.petId = petId; }
    public void setVaccineTypeId(Long vaccineTypeId) { this.vaccineTypeId = vaccineTypeId; }
    public void setDateGiven(LocalDate dateGiven) { this.dateGiven = dateGiven; }
}
