package edu.itu.sue.intf.treatmentservice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/treatment")
public interface TreatmentService {

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createTreatment(CreateTreatmentRequest request);

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllTreatments();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTreatmentById(@PathParam("id") int id);

    @GET
    @Path("/patients/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTreatmentByPatientId(@PathParam("patientId") int patientId);

    @GET
    @Path("/doctors/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTreatmentByDoctorId(@PathParam("doctorId") int doctorId);
}
