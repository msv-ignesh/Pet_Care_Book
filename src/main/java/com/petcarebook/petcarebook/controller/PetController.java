package com.petcarebook.petcarebook.controller;

import com.petcarebook.petcarebook.entity.Pet;
import com.petcarebook.petcarebook.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService service;
    public PetController(PetService service) { this.service = service; }

    @GetMapping public List<Pet> all() { return service.findAll(); }
    @GetMapping("/{id}") public Pet one(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pet create(@Valid @RequestBody Pet pet, @RequestParam Long ownerId) {
        return service.save(pet, ownerId);
    }
}
