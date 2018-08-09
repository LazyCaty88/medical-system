package com.sue.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;

@NamedNativeQueries(value = {@NamedNativeQuery(name = "doctor.getDoctorByDefaultEmail",
        query = "SELECT * FROM DOCTOR WHERE DEFAULT_EMAIL=:DEFAULT_EMAIL",
        resultClass = Doctor.class)
})

@Getter
@Setter
@ToString

@Entity
@Table(name = "DOCTOR")
@XmlRootElement
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "DEFAULT_EMAIL")
    private String defaultEmail;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SPECIALTIES")
    private String specialties;

    @ManyToOne
    @JoinColumn(name = "CLINIC_ID", referencedColumnName = "id")
    private Clinic clinic;

    @Column(name = "DATE_ADDED")
    private Calendar dateAdded;

    @Column(name = "DATE_LAST_UPDATED")
    private Calendar dateLastUpdated;
}
