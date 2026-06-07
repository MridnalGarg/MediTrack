package org.mridnal.meditrack.repository;

import org.mridnal.meditrack.enums.AppointmentStatus;
import org.mridnal.meditrack.model.Appointment;
import org.mridnal.meditrack.model.Doctor;
import org.mridnal.meditrack.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByDoctorAndDateTimeAfterAndAppointmentStatus(
            Doctor doctor,
            LocalDateTime dateTime,
            AppointmentStatus appointmentStatus
    );

    List<Appointment> findByPatient(Patient patient);
}
