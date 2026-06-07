package org.mridnal.meditrack.repository;

import org.mridnal.meditrack.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByNameIgnoreCase(String name);

    List<Doctor> findByClinicId(Long clinicId);

    long countByClinicId(Long clinicId);
}
