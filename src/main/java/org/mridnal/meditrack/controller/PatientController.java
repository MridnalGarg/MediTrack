package org.mridnal.meditrack.controller;

import org.mridnal.meditrack.model.Doctor;
import org.mridnal.meditrack.model.Patient;
import org.mridnal.meditrack.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/patient", "/patients"})
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.createPatient(patient));
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient){
        return ResponseEntity.ok(patientService.updatePatient(patient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id){
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("/{patientId}/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctorsByPatient(@PathVariable Long patientId){
        return patientService.getPatientById(patientId)
                .map(patient -> ResponseEntity.ok(patient.getDoctors()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
