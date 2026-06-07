package org.mridnal.meditrack.service;

import org.mridnal.meditrack.model.Clinic;
import org.mridnal.meditrack.model.Doctor;
import org.mridnal.meditrack.repository.ClinicRepository;
import org.mridnal.meditrack.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Transactional
    public Clinic createClinic(Clinic clinic) {
        if (clinic.getDoctors() == null || clinic.getDoctors().isEmpty()) {
            throw new IllegalArgumentException("Clinic must have at least one doctor");
        }

        clinic.getDoctors().forEach(doctor -> doctor.setClinic(clinic));
        return clinicRepo.save(clinic);
    }

    public List<Clinic> getAllClinics() {
        return clinicRepo.findAll();
    }

    public Optional<Clinic> getClinicById(Long id) {
        return clinicRepo.findById(id);
    }

    public Optional<Clinic> getClinicByName(String name) {
        return clinicRepo.findByNameIgnoreCase(name);
    }

    public List<Doctor> getDoctorsByClinicId(Long clinicId) {
        if (!clinicRepo.existsById(clinicId)) {
            throw new IllegalArgumentException("Clinic not found");
        }
        return doctorRepo.findByClinicId(clinicId);
    }
}
