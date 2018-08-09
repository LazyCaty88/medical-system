package com.sue;

import com.sue.biz.PatientBO;
import com.sue.intf.CreatePatientRequest;
import com.sue.model.Patient;
import com.sue.model.common.Date;
import com.sue.service.PatientService;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.springframework.test.util.ReflectionTestUtils;

public class PatientServiceTest {
	@InjectMocks
	private PatientService patientService = new PatientService();

	@Mock
	private PatientBO patientBO;

	CreatePatientRequest createPatientRequest;

	@BeforeMethod
	public void setUp() {
		initMocks(this);
		patientBO = mock(PatientBO.class);
		ReflectionTestUtils.setField(patientService, "patientBO", patientBO);

		createPatientRequest = new CreatePatientRequest();
		createPatientRequest.setDefaultEmail("test@test.com");
		createPatientRequest.setName("test name");
		createPatientRequest.setBirthDate(new Date(1988, 8, 8));

	}

	@Test
	public void testGetPatientById(){
		int id = 1;
		Patient patient = new Patient();
		patient.setId(id);
		patient.setName("name");

		when(patientBO.getPatientById(any(Integer.class))).thenReturn(patient);
		Response result = patientService.getPatientById(id);

		Assert.assertEquals(200, result.getStatus());
	}

	@Test
	public void testValidateCreatePatientRequest(){

	}
}
