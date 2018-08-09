package com.sue.intf;

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
