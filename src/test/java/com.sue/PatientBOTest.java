package com.sue;

import com.sue.biz.PatientBO;
import com.sue.intf.CreatePatientRequest;
import com.sue.model.common.Date;
import com.sue.service.ErrorHandling.Error;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

public class PatientBOTest {
    @InjectMocks
    private PatientBO patientBO = new PatientBO();

    CreatePatientRequest createPatientRequest;
    List<Error> errorList;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        createPatientRequest = new CreatePatientRequest();
        createPatientRequest.setDefaultEmail("testtest.com");
        createPatientRequest.setName("test name");
        createPatientRequest.setBirthDate(new Date(1988, 8, 8));

        errorList = new ArrayList<>();
    }


}
