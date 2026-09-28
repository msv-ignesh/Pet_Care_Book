package com.petcarebook.petcarebook.service;

import com.petcarebook.petcarebook.entity.Owner;
import com.petcarebook.petcarebook.entity.Pet;
import com.petcarebook.petcarebook.exception.ResourceNotFoundException;
import com.petcarebook.petcarebook.repository.OwnerRepository;
import com.petcarebook.petcarebook.repository.PetRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetService(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<Pet> findAll() { return petRepository.findAll(); }
    public Pet findById(Long id) { return petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet not found: " + id)); }

    public Pet save(Pet pet, Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found: " + ownerId));
        pet.setOwner(owner);
        return petRepository.save(pet);
    }
}
