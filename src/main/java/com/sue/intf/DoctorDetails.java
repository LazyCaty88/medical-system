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
@XmlRootElement(name = "doctorDetails")
@JsonRootName(value = "doctorDetails")
@XmlType(name = "doctorDetails", propOrder = {"id", "name", "defaultEmail", "gender", "title", "specialties", "phone", "clinicName", "dateAdded", "dateLastUpdated"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorDetails implements Serializable {
    private int id;
    private String name;
    private String defaultEmail;
    private String title;
    private String gender;
    private String phone;
    private String specialties;
    private String clinicName;
    private String dateAdded;
    private String dateLastUpdated;
}
