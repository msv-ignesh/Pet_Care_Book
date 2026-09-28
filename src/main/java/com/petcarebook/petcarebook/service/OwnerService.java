package com.petcarebook.petcarebook.service;

import com.petcarebook.petcarebook.entity.Owner;
import com.petcarebook.petcarebook.repository.OwnerRepository;
import com.petcarebook.petcarebook.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerService {
    private final OwnerRepository repository;
    public OwnerService(OwnerRepository repository) { this.repository = repository; }
    public List<Owner> findAll() { return repository.findAll(); }
    public Owner findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Owner not found: " + id)); }
    public Owner save(Owner owner) { return repository.save(owner); }
}
