package edu.itu.sue.intf.clinicservice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateClinicRequest {
    private String email;
    private String name;
    private String Phone;
    private String specializationType;
}
