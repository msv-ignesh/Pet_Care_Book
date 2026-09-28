package com.petcarebook.petcarebook.controller;

import com.petcarebook.petcarebook.entity.Owner;
import com.petcarebook.petcarebook.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    private final OwnerService service;
    public OwnerController(OwnerService service) { this.service = service; }

    @GetMapping public List<Owner> all() { return service.findAll(); }
    @GetMapping("/{id}") public Owner one(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Owner create(@Valid @RequestBody Owner owner) { return service.save(owner); }
}
