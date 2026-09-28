package com.petcarebook.petcarebook.controller;

import com.petcarebook.petcarebook.dto.VaccinationRequest;
import com.petcarebook.petcarebook.dto.CompleteVaccinationHistoryItem;
import com.petcarebook.petcarebook.entity.VaccinationRecord;
import com.petcarebook.petcarebook.service.VaccinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vaccinations")
public class VaccinationController {
    private final VaccinationService service;
    public VaccinationController(VaccinationService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VaccinationRecord log(@Valid @RequestBody VaccinationRequest request) {
        return service.log(request);
    }

    @GetMapping("/{id}")
    public VaccinationRecord one(@PathVariable Long id) { return service.findById(id); }

    @GetMapping("/pet/{petId}")
    public List<CompleteVaccinationHistoryItem> history(@PathVariable Long petId) { return service.history(petId); }

    @GetMapping("/due-next-7-days")
    public List<VaccinationRecord> dueNext7Days() { return service.dueWithinNext7Days(); }
}
