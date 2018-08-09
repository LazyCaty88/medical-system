package com.sue.intf;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Getter
@Setter
@ToString

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "patientDetails")
@JsonRootName(value = "patientDetails")
@XmlType(name = "patientDetails", propOrder = {"id", "name", "defaultEmail", "gender", "phone", "age", "birthDate", "dateAdded", "dateLastUpdated"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDetails implements Serializable {
    private int id;
    private String name;
    private String defaultEmail;
    private int age;
    private String gender;
    private String phone;
    private String birthDate;
    private String dateAdded;
    private String dateLastUpdated;
}
