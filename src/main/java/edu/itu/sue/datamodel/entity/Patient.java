package edu.itu.sue.datamodel.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;

@NamedNativeQueries(value = { @NamedNativeQuery(name = "patient.getPatientByDefaultEmail",
        query = "SELECT * FROM PATIENT WHERE DEFAULT_EMAIL=:DEFAULT_EMAIL",
        resultClass = Patient.class)
})

@Getter
@Setter
@ToString

@Entity
@Table(name = "PATIENT")

@XmlRootElement
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "DEFAULT_EMAIL")
    private String defaultEmail;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    @Column(name = "BIRTH_DATE")
    private Calendar birthDate;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DATE_ADDED")
    private Calendar dateAdded;

    @Column(name = "DATE_LAST_UPDATED")
    private Calendar dateLastUpdated;
}