package com.sue.service;

import com.sue.biz.DoctorBO;
import com.sue.biz.ClinicBO;
import com.sue.intf.*;
import com.sue.model.Clinic;
import com.sue.model.Doctor;
import com.sue.service.ErrorHandling.Error;
import com.sue.service.helper.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("/clinic")
@Service("clinicService")
public class ClinicService {
    private static final Logger LOGGER = Logger.getLogger(ClinicService.class.getName());

    @Autowired
    private ClinicBO clinicBO;
    @Autowired
    private DoctorBO doctorBO;
    @Autowired
    private ServiceHelper serviceHelper;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClinic(CreateClinicRequest request) {
        LOGGER.info("api_method=createClinic message=received createClinic request, createClinicRequest=" + request);

        List<Error> errorList = new ArrayList<>();
        errorList = clinicBO.validateCreateClinicRequest(request, errorList);
        if (errorList.size() < 1) {
            Clinic clinic = clinicBO.createClinic(request);
            return Response.ok(clinicBO.convertClinicToClinicDetails(clinic)).build();
        }
        return Response.status(errorList.get(0).getStatus()).entity(errorList).build();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upateClinic(CreateClinicRequest request) {
        LOGGER.info("api_method=upateClinic message=received upateClinic request, updateClinicRequest=" + request);

        List<Error> errorList = new ArrayList<>();
        errorList = clinicBO.validateUpdateClinicRequest(request, errorList);
        if (errorList.size() > 0) {
            return Response.status(400).entity(errorList).build();
        }
        Clinic clinic = clinicBO.updateClinic(request);
        ClinicDetails clinicDetails = clinicBO.convertClinicToClinicDetails(clinic);
        LOGGER.info("api_method=upateClinic message=after upateClinic, clinic=" + clinicDetails);

        return Response.ok(clinicDetails).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClinics() {
        LOGGER.info("api_method=getClinics message=get all existing clinics");

        List<Clinic> clinicList = clinicBO.getAllClinics();
        if (clinicList == null) {
            return Response.status(404).entity(new Error(404, "No Clinic Found")).build();
        }
        return Response.ok(clinicBO.convertClinicsToClinicDetailsList(clinicList)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClinicById(@PathParam("id") int id) {
        LOGGER.info("api_method=getClinicById message= get clinic with id=" + id);

        Clinic clinic = clinicBO.getClinicById(id);
        if (clinic != null) {
            return Response.ok(clinicBO.convertClinicToClinicDetails(clinic)).build();
        }
        return Response.status(404).entity(new Error(404, "Clinic Not Found")).build();
    }

    @POST
    @Path("/doctor/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDoctor(CreateDoctorRequest request) {
        LOGGER.info("api_method=createDoctor message=received createDoctor request, createDoctorRequest=" + request.toString());

        List<Error> errorList = new ArrayList<>();
        errorList = doctorBO.validateCreateDoctorRequest(request, errorList);
        if (errorList.size() < 1) {
            Doctor doctor = doctorBO.createDoctor(request);
            return Response.ok(doctorBO.convertDoctorToDoctorDetails(doctor)).build();
        }
        return Response.status(400).entity(errorList).build();
    }

    @PUT
    @Path("/doctor/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upateDoctor(CreateDoctorRequest request) {
        LOGGER.info("api_method=upateDoctor message=received upateDoctor request, upateDoctorRequest=" + request);

        List<Error> errorList = new ArrayList<>();
        errorList = doctorBO.validateUpdateDoctorRequest(request, errorList);
        if (errorList.size() > 0) {
            return Response.status(400).entity(errorList).build();
        }
        Doctor doctor = doctorBO.updateDoctor(request);
        DoctorDetails doctorDetails = doctorBO.convertDoctorToDoctorDetails(doctor);
        LOGGER.info("api_method=upateDoctor message=after upateDoctor, doctor=" + doctorDetails);

        return Response.ok(doctorDetails).build();
    }

    @GET
    @Path("/{clinicId}/doctors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctorsByClinicId(@PathParam("clinicId") int clinicId) {
        LOGGER.info("api_method=getDoctorsByClinicId message=get doctors with clinicId=" + clinicId);

        Error error = serviceHelper.isClinicValid(clinicId);
        if (error != null) {
            return Response.status(400).entity(error).build();
        }

        List<Doctor> doctors = doctorBO.getDoctorsByClinicId(clinicId);
        return Response.ok(doctorBO.convertDoctorsToDoctorDetailsList(doctors)).build();
    }
}