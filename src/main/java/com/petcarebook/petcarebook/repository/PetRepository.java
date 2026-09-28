package com.petcarebook.petcarebook.repository;
import com.petcarebook.petcarebook.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerId(Long ownerId);
}
