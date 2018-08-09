package edu.itu.sue.service;

import edu.itu.sue.biz.PatientBO;
import edu.itu.sue.datamodel.entity.Patient;
import edu.itu.sue.intf.patientservice.CreatePatientRequest;
import edu.itu.sue.intf.patientservice.PatientDetails;
import edu.itu.sue.intf.patientservice.PatientService;
import edu.itu.sue.service.ErrorHandling.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Service("patientService")
public class PatientServiceImpl implements PatientService {
    private static final Logger LOGGER = Logger.getLogger(PatientServiceImpl.class.getName());
    @Autowired
    private PatientBO patientBO;

    @Override
    public Response createPatient(CreatePatientRequest request) {
        LOGGER.info("api_method=createPatient message=received createPatient request, createPatientRequest=" + request.toString());

        List<Error> errorList = new ArrayList<>();
        errorList = patientBO.validateCreatePatientRequest(request, errorList);
        if (errorList.size() > 0) {
            return Response.status(400).entity(errorList).build();
        }
        LOGGER.info("api_method=createPatient message=createPatient request is valid");
        Patient patient = patientBO.createPatient(request);
        return Response.ok(patientBO.convertPatientToPatientDetails(patient)).build();
    }

    @Override
    public Response upatePatient(CreatePatientRequest request) {
        LOGGER.info("api_method=updatePatient message=received updatePatient request, updatePatientRequest=" + request.toString());

        List<Error> errorList = new ArrayList<>();
        errorList = patientBO.validateUpdatePatientRequest(request, errorList);
        if (errorList.size() > 0) {
            return Response.status(400).entity(errorList).build();
        }
        Patient patient = patientBO.updatePatient(request);
        PatientDetails patientDetails = patientBO.convertPatientToPatientDetails(patient);
        LOGGER.info("api_method=updatePatient message=after updatePatient, patient=" + patientDetails);

        return Response.ok(patientDetails).build();
    }


    @Override
    public Response getPatients() {
        LOGGER.info("api_method=getPatients message=get all existing patients");

        List<Patient> patientList = patientBO.getAllPatients();
        if (patientList == null) {
            return Response.status(404).entity(new Error(404, "No Patient Found")).build();
        }
        return Response.ok(patientBO.convertPatientsToPatientDetailsList(patientList)).build();
    }

    @Override
    public Response getPatientById(int id) {
        LOGGER.info("api_method=getPatientById message=get patient with patient id=" + id);
        Patient patient = patientBO.getPatientById(id);
        if (patient == null) {
            return Response.status(404).entity(new Error(404, "Patient Not Found")).build();
        }
        return Response.ok(patientBO.convertPatientToPatientDetails(patient)).build();
    }
}