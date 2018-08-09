package edu.itu.sue.intf.treatmentservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@ToString

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "treatmentDetails")
@JsonRootName(value = "treatmentDetails")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TreatmentDetails implements Serializable {
    private int id;
    private String treatmentType;
    private String diagnosis;
    private int patientId;
    private String patientName;
    private int doctorId;
    private String doctorName;
    private String clinicName;
    private String dateAdded;
    private String dateLastUpdated;
}
