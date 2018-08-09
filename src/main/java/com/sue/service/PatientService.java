package com.sue.service;

import com.sue.biz.PatientBO;
import com.sue.intf.CreatePatientRequest;
import com.sue.intf.PatientDetails;
import com.sue.model.Patient;
import com.sue.service.ErrorHandling.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/patient")
@Service("patientService")
public class PatientService {
    private static final Logger LOGGER = Logger.getLogger(PatientService.class.getName());
    @Autowired
    private PatientBO patientBO;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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


    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatients() {
        LOGGER.info("api_method=getPatients message=get all existing patients");

        List<Patient> patientList = patientBO.getAllPatients();
        if (patientList == null) {
            return Response.status(404).entity(new Error(404, "No Patient Found")).build();
        }
        return Response.ok(patientBO.convertPatientsToPatientDetailsList(patientList)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("id") int id) {
        LOGGER.info("api_method=getPatientById message=get patient with patient id=" + id);
        Patient patient = patientBO.getPatientById(id);
        if (patient == null) {
            return Response.status(404).entity(new Error(404, "Patient Not Found")).build();
        }
        return Response.ok(patientBO.convertPatientToPatientDetails(patient)).build();
    }
}