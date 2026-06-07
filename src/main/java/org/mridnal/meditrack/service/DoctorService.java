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
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private ClinicRepository clinicRepo;

    @Transactional
    public Doctor createDoctor(Doctor doctor){
        Clinic clinic = resolveClinic(doctor.getClinic());
        doctor.setClinic(clinic);
        return doctorRepo.save(doctor);
    }

    private void validateClinicKeepsDoctor(Clinic currentClinic, Clinic newClinic) {
        if (currentClinic == null || currentClinic.getId() == null) {
            return;
        }

        if (newClinic != null && currentClinic.getId().equals(newClinic.getId())) {
            return;
        }

        if (doctorRepo.countByClinicId(currentClinic.getId()) <= 1) {
            throw new IllegalStateException("Cannot remove the only doctor from a clinic");
        }
    }

    private Clinic resolveClinic(Clinic clinic) {
        if (clinic == null) {
            return null;
        }

        if (clinic.getId() == null) {
            return clinicRepo.save(clinic);
        }

        return clinicRepo.findById(clinic.getId())
                .orElseGet(() -> clinicRepo.save(clinic));
    }

    @Transactional
    public Optional<Doctor> updateDoctor(Long id, Doctor doctor){
        return doctorRepo.findById(id)
                .map(existingDoctor -> {
                    Clinic currentClinic = existingDoctor.getClinic();
                    Clinic newClinic = resolveClinic(doctor.getClinic());
                    validateClinicKeepsDoctor(currentClinic, newClinic);

                    existingDoctor.setName(doctor.getName());
                    existingDoctor.setPhoneNumber(doctor.getPhoneNumber());
                    existingDoctor.setEmail(doctor.getEmail());
                    existingDoctor.setGender(doctor.getGender());
                    existingDoctor.setClinic(newClinic);
                    existingDoctor.setPatients(doctor.getPatients());
                    existingDoctor.setAppointments(doctor.getAppointments());
                    return doctorRepo.save(existingDoctor);
                });
    }

    @Transactional
    public boolean deleteDoctor(Long id) {
        Optional<Doctor> doctor = doctorRepo.findById(id);
        if (doctor.isEmpty()) {
            return false;
        }

        Clinic clinic = doctor.get().getClinic();
        if (clinic != null && doctorRepo.countByClinicId(clinic.getId()) <= 1) {
            throw new IllegalStateException("Cannot delete the only doctor in a clinic");
        }

        doctorRepo.deleteById(id);
        return true;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepo.findById(id);
    }

    public Optional<Doctor> getDoctorByName(String name) {
        return doctorRepo.findByNameIgnoreCase(name);
    }

}
