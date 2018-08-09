package edu.itu.sue.intf.clinicservice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clinic")
public interface ClinicService {
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createClinic(CreateClinicRequest request);

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response upateClinic(CreateClinicRequest request);

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    Response getClinics();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getClinicById(@PathParam("id") int id);

    @POST
    @Path("/doctor/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createDoctor(CreateDoctorRequest request);

    @GET
    @Path("/{clinicId}/doctors")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDoctorsByClinicId(@PathParam("clinicId") int clinicId);
}
