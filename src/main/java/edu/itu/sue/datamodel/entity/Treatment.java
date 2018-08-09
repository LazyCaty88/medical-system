package edu.itu.sue.datamodel.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;

@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "treatment.getTreatmentsByPatientId",
                query = "SELECT * FROM Treatment WHERE PATIENT_ID=:PATIENT_ID",
                resultClass = Treatment.class),
        @NamedNativeQuery(name = "treatment.getTreatmentsByDoctorId",
                query = "SELECT * FROM Treatment WHERE DOCTOR_ID=:DOCTOR_ID",
                resultClass = Treatment.class)
})

@Getter
@Setter
@ToString

@Entity
@Table(name = "TREATMENT")

@XmlRootElement
public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "TREATMENT_TYPE")
    private String treatmentType;

    @Column(name = "DIAGNOSIS")
    private String diagnosis;

    @Column(name = "DATE_ADDED")
    private Calendar dateAdded;

    @Column(name = "DATE_LAST_UPDATED")
    private Calendar dateLastUpdated;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;
}