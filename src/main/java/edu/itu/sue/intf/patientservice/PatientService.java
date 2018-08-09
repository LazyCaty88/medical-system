package edu.itu.sue.intf.patientservice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/patient")
public interface PatientService {

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createPatient(CreatePatientRequest request);

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response upatePatient(CreatePatientRequest request);

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPatients();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPatientById(@PathParam("id") int id);
}
