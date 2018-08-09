package com.sue.intf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateTreatmentRequest {
    private String treatmentType;
    private String diagnosis;
    private int patientId;
    private int doctorId;
}
