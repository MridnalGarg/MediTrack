package org.mridnal.meditrack.controller;

import org.mridnal.meditrack.model.Doctor;
import org.mridnal.meditrack.model.Patient;
import org.mridnal.meditrack.service.DoctorService;
import org.mridnal.meditrack.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/doctor", "/doctors"})
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    PatientService patientService;

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        try {
            return doctorService.updateDoctor(id, doctor)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        try {
            if (!doctorService.deleteDoctor(id)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Doctor> getDoctorByName(@RequestParam String name) {
        return doctorService.getDoctorByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<Patient>> getAllPatientsByDoctor(@PathVariable Long doctorId){
        return doctorService.getDoctorById(doctorId)
                .map(doctor -> ResponseEntity.ok(patientService.getAllPatientsByDoctor(doctor)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
