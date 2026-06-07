package org.mridnal.meditrack.controller;

import org.mridnal.meditrack.model.Appointment;
import org.mridnal.meditrack.service.AppointmentService;
import org.mridnal.meditrack.service.DoctorService;
import org.mridnal.meditrack.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.createAppointment(appointment));
    }

    @PutMapping
    public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment){
        return ResponseEntity.ok(appointmentService.updateAppointment(appointment));
    }

    @PatchMapping("/cancel")
    public ResponseEntity<Appointment> cancelAppointment(@RequestBody Appointment appointment){
        return ResponseEntity.ok(appointmentService.cancelAppointment(appointment));
    }

    @PatchMapping("/doctor/{doctorId}/cancel")
    public ResponseEntity<List<Appointment>> cancelAllAppointmentsForDoctor(@PathVariable Long doctorId){
        return doctorService.getDoctorById(doctorId)
                .map(doctor -> ResponseEntity.ok(appointmentService.cancelAllAppointmentsForDoctor(doctor)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAllAppointmentsForDoctor(@PathVariable Long doctorId){
        return doctorService.getDoctorById(doctorId)
                .map(doctor -> ResponseEntity.ok(appointmentService.getAllAppointmentsForDoctor(doctor)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/doctor/{doctorId}/upcoming")
    public ResponseEntity<List<Appointment>> getUpcomingAppointmentsForDoctor(@PathVariable Long doctorId){
        return doctorService.getDoctorById(doctorId)
                .map(doctor -> ResponseEntity.ok(appointmentService.getUpcomingAppointmentsForDoctor(doctor)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAllAppointmentsForPatient(@PathVariable Long patientId){
        return patientService.getPatientById(patientId)
                .map(patient -> ResponseEntity.ok(appointmentService.getAllAppointmentsForPatient(patient)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
