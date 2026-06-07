package org.mridnal.meditrack.repository;

import org.mridnal.meditrack.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Optional<Clinic> findByNameIgnoreCase(String name);
}
