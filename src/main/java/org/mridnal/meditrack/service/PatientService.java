package org.mridnal.meditrack.service;

import org.mridnal.meditrack.model.Doctor;
import org.mridnal.meditrack.model.Patient;
import org.mridnal.meditrack.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public Patient createPatient(Patient patient){
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient){
        return patientRepository.save(patient);
    }

    public Optional<Patient> getPatientById(long id){
        return patientRepository.findById(id);
    }

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public List<Patient> getAllPatientsByDoctor(Doctor doctor){
        return patientRepository.findByDoctorsContaining(doctor);
    }
}
