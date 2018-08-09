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
@XmlRootElement(name = "clinicDetails")
@JsonRootName(value = "clinicDetails")
@XmlType(name = "clinicDetails", propOrder = {"id", "name", "email", "phone", "specializationType", "dateAdded", "dateLastUpdated"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinicDetails implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String specializationType;
    private String dateAdded;
    private String dateLastUpdated;
}
