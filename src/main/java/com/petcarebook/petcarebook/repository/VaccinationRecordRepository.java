package com.petcarebook.petcarebook.repository;
import com.petcarebook.petcarebook.entity.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Long> {
    List<VaccinationRecord> findByPetIdOrderByDateGivenAsc(Long petId);

    @Query("select r from VaccinationRecord r join fetch r.pet p join fetch r.vaccineType v where r.nextDueDate between :start and :end order by r.nextDueDate asc")
    List<VaccinationRecord> findDueBetween(LocalDate start, LocalDate end);

    long countByNextDueDateBetween(LocalDate start, LocalDate end);
}
