package com.petcarebook.petcarebook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vaccine_types", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class VaccineType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Vaccine name is required")
    @Size(max = 100)
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @NotNull(message = "Interval is required")
    @Min(value = 1, message = "Interval must be at least 1 day")
    @Column(nullable = false)
    private Integer intervalDays;

    @JsonIgnore
    @OneToMany(mappedBy = "vaccineType")
    private List<VaccinationRecord> vaccinationRecords = new ArrayList<>();

    public VaccineType() {}

    public VaccineType(String name, Integer intervalDays) {
        this.name = name;
        this.intervalDays = intervalDays;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getIntervalDays() { return intervalDays; }
    public List<VaccinationRecord> getVaccinationRecords() { return vaccinationRecords; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setIntervalDays(Integer intervalDays) { this.intervalDays = intervalDays; }
    public void setVaccinationRecords(List<VaccinationRecord> vaccinationRecords) { this.vaccinationRecords = vaccinationRecords; }
}
