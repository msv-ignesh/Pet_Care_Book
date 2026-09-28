package com.petcarebook.petcarebook.repository;
import com.petcarebook.petcarebook.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OwnerRepository extends JpaRepository<Owner, Long> {}
