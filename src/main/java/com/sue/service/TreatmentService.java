package com.sue.service;

import com.sue.biz.TreatmentBO;
import com.sue.intf.CreateTreatmentRequest;
import com.sue.model.Treatment;
import com.sue.service.ErrorHandling.Error;
import com.sue.service.helper.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/treatment")
@Service("treatmentService")
public class TreatmentService {
    private static final Logger LOGGER = Logger.getLogger(TreatmentService.class.getName());

    @Autowired
    private TreatmentBO treatmentBO;
    @Autowired
    private ServiceHelper serviceHelper;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTreatment(CreateTreatmentRequest request) {
        LOGGER.info("api_method=createTreatment message=received createTreatment request, createTreatmentRequest=" + request.toString());
        List<Error> errors = serviceHelper.validateCreateTreatmentRequest(request);
        if (errors.size() > 0) {
            return Response.status(400).entity(errors).build();
        }
        Treatment treatment = treatmentBO.createTreatment(request);
        return Response.ok(treatmentBO.convertTreatmentToTreatmentDetails(treatment)).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTreatments() {
        LOGGER.info("api_method=getAllTreatments message=get all existing treatments");

        List<Treatment> treatments = treatmentBO.getAllTreatments();
        if (treatments == null) {
            return Response.status(404).entity(new Error(404,"No Treatment Found")).build();
        }
        return Response.ok(treatmentBO.convertTreatmentsToTreatmentDetailsList(treatments)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTreatmentById(@PathParam("id") int id) {
        LOGGER.info("api_method=getTreatmentById message= get treatment with id=" + id);

        Treatment treatment = treatmentBO.getTreatmentById(id);
        if (treatment == null) {
            return Response.status(404).entity(new Error(404,"Treatment Not Found")).build();
        }
        return Response.ok(treatmentBO.convertTreatmentToTreatmentDetails(treatment)).build();
    }

    @GET
    @Path("/patients/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTreatmentByPatientId(@PathParam("patientId") int patientId) {
        LOGGER.info("api_method=getTreatmentByPatientId message= get all treatments of patient with id=" + patientId);

        Error error = serviceHelper.isPatientValid(patientId);
        if (error != null) {
            return Response.status(400).entity(error).build();
        }

        List<Treatment> treatments = treatmentBO.getTreatmentByPatientId(patientId);
        return Response.ok(treatmentBO.convertTreatmentsToTreatmentDetailsList(treatments)).build();
    }

    @GET
    @Path("/doctors/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTreatmentByDoctorId(@PathParam("doctorId") int doctorId) {
        LOGGER.info("api_method=getTreatmentByDoctorId message= get all treatments of doctor with id=" + doctorId);

        Error error = serviceHelper.isDoctorValid(doctorId);
        if (error != null) {
            return Response.status(400).entity(error).build();
        }

        List<Treatment> treatments = treatmentBO.getTreatmentByDoctorId(doctorId);
        return Response.ok(treatmentBO.convertTreatmentsToTreatmentDetailsList(treatments)).build();
    }
}