package com.sue.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;

@NamedNativeQueries(value = { @NamedNativeQuery(name = "clinic.getClinicByEmail",
        query = "SELECT * FROM CLINIC WHERE EMAIL=:EMAIL",
        resultClass = Clinic.class)
})

@Getter
@Setter
@ToString

@Entity
@Table(name="CLINIC")
@XmlRootElement
public class Clinic implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SPECIALIZATION_TYPE")
    private String specializationType;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "DATE_ADDED")
    private Calendar dateAdded;

    @Column(name = "DATE_LAST_UPDATED")
    private Calendar dateLastUpdated;
}
