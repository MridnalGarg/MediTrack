package org.mridnal.meditrack.service;

import org.mridnal.meditrack.enums.AppointmentStatus;
import org.mridnal.meditrack.model.Appointment;
import org.mridnal.meditrack.model.Doctor;
import org.mridnal.meditrack.model.Patient;
import org.mridnal.meditrack.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    public AppointmentRepository  appointmentRepo;

    public Appointment createAppointment(Appointment appointment){
        return appointmentRepo.save(appointment);
    }

    @Transactional
    public Appointment cancelAppointment(Appointment appointment){
        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        return appointmentRepo.save(appointment);
    }

    @Transactional
    public List<Appointment> cancelAllAppointmentsForDoctor(Doctor doctor) {
        List<Appointment> appointments = appointmentRepo.findByDoctor(doctor);
        appointments.forEach(appointment -> appointment.setAppointmentStatus(AppointmentStatus.CANCELLED));
        return appointmentRepo.saveAll(appointments);
    }

    public Appointment updateAppointment(Appointment appointment){
        return appointmentRepo.save(appointment);
    }

    public List<Appointment> getAllAppointmentsForDoctor(Doctor doctor){
        return appointmentRepo.findByDoctor(doctor);
    }

    public List<Appointment> getUpcomingAppointmentsForDoctor(Doctor doctor){
        return appointmentRepo.findByDoctorAndDateTimeAfterAndAppointmentStatus(
                doctor,
                LocalDateTime.now(),
                AppointmentStatus.SCHEDULED
        );
    }

    public List<Appointment> getAllAppointmentsForPatient(Patient patient){
        return appointmentRepo.findByPatient(patient);
    }

}
