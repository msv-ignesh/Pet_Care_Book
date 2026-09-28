package com.petcarebook.petcarebook.controller;

import com.petcarebook.petcarebook.entity.VaccineType;
import com.petcarebook.petcarebook.service.VaccineTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vaccine-types")
public class VaccineTypeController {
    private final VaccineTypeService service;
    public VaccineTypeController(VaccineTypeService service) { this.service = service; }

    @GetMapping public List<VaccineType> all() { return service.findAll(); }
    @GetMapping("/{id}") public VaccineType one(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public VaccineType create(@Valid @RequestBody VaccineType type) { return service.save(type); }
}
