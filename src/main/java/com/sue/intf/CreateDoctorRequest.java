package com.sue.intf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateDoctorRequest {
    private String name;
    private String gender;
    private String title;
    private String defaultEmail;
    private String phone;
    private String specialties;
    private int clinicId;
}