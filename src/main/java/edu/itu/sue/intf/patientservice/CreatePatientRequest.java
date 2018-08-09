package edu.itu.sue.intf.patientservice;

import edu.itu.sue.datamodel.entity.common.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreatePatientRequest {
    private String defaultEmail;
    private String name;
    private Date birthDate;
    private String gender;
    private String phone;
}
