package org.mridnal.meditrack.controller;

import org.mridnal.meditrack.model.Clinic;
import org.mridnal.meditrack.model.Doctor;
import org.mridnal.meditrack.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/clinic", "/clinics"})
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @PostMapping
    public ResponseEntity<Clinic> createClinic(@RequestBody Clinic clinic) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clinicService.createClinic(clinic));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<Clinic> getAllClinics() {
        return clinicService.getAllClinics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clinic> getClinicById(@PathVariable Long id) {
        return clinicService.getClinicById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Clinic> getClinicByName(@RequestParam String name) {
        return clinicService.getClinicByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<Doctor>> getDoctorsByClinicId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clinicService.getDoctorsByClinicId(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
