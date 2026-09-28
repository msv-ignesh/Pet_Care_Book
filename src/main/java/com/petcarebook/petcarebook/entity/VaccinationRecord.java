package com.petcarebook.petcarebook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(name = "vaccination_records")
public class VaccinationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vaccine_type_id", nullable = false)
    private VaccineType vaccineType;

    @NotNull(message = "Vaccination date is required")
    @PastOrPresent(message = "Vaccination date cannot be in the future")
    @Column(nullable = false)
    private LocalDate dateGiven;

    @Column(nullable = false)
    private LocalDate nextDueDate;

    public VaccinationRecord() {}

    public Long getId() { return id; }
    public Pet getPet() { return pet; }
    public VaccineType getVaccineType() { return vaccineType; }
    public LocalDate getDateGiven() { return dateGiven; }
    public LocalDate getNextDueDate() { return nextDueDate; }

    public void setId(Long id) { this.id = id; }
    public void setPet(Pet pet) { this.pet = pet; }
    public void setVaccineType(VaccineType vaccineType) { this.vaccineType = vaccineType; }
    public void setDateGiven(LocalDate dateGiven) { this.dateGiven = dateGiven; }
    public void setNextDueDate(LocalDate nextDueDate) { this.nextDueDate = nextDueDate; }
}
