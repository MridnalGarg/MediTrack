package org.mridnal.meditrack.repository;

import org.mridnal.meditrack.model.Doctor;
import org.mridnal.meditrack.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDoctorsContaining(Doctor doctor);
}
