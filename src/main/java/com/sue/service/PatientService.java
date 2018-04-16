package com.sue.service;

import com.sue.biz.PatientBO;
import com.sue.intf.CreatePatientRequest;
import com.sue.intf.PatientInfo;
import com.sue.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/patient")
@Service("patientService")
public class PatientService {
    @Autowired
    private PatientBO patientBO;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPatient(CreatePatientRequest request) {
        Patient patient = patientBO.createPatient(request);
        PatientInfo patientInfo = patientBO.showPatient(patient);
        return Response.status(200).entity(patientInfo).build();
    }

}
