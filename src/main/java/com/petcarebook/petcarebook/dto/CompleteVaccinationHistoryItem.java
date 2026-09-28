package com.petcarebook.petcarebook.dto;

import java.time.LocalDate;

public class CompleteVaccinationHistoryItem {
    private Long id;
    private String vaccineName;
    private LocalDate dateGiven;
    private LocalDate nextDueDate;
    private Integer intervalDays;
    private String status;

    public CompleteVaccinationHistoryItem() {}

    public CompleteVaccinationHistoryItem(Long id, String vaccineName, LocalDate dateGiven,
                                          LocalDate nextDueDate, Integer intervalDays, String status) {
        this.id = id;
        this.vaccineName = vaccineName;
        this.dateGiven = dateGiven;
        this.nextDueDate = nextDueDate;
        this.intervalDays = intervalDays;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getVaccineName() { return vaccineName; }
    public LocalDate getDateGiven() { return dateGiven; }
    public LocalDate getNextDueDate() { return nextDueDate; }
    public Integer getIntervalDays() { return intervalDays; }
    public String getStatus() { return status; }
}
