package edu.itu.sue.service;

import edu.itu.sue.biz.ClinicBO;
import edu.itu.sue.biz.DoctorBO;
import edu.itu.sue.datamodel.entity.Clinic;
import edu.itu.sue.datamodel.entity.Doctor;
import edu.itu.sue.intf.clinicservice.*;
import edu.itu.sue.service.ErrorHandling.Error;
import edu.itu.sue.service.helper.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service("clinicService")
public class ClinicServiceImpl implements ClinicService {
    private static final Logger LOGGER = Logger.getLogger(ClinicServiceImpl.class.getName());

    @Autowired
    private ClinicBO clinicBO;
    @Autowired
    private DoctorBO doctorBO;
    @Autowired
    private ServiceHelper serviceHelper;

    @Override
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

    @Override
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

    @Override
    public Response getClinics() {
        LOGGER.info("api_method=getClinics message=get all existing clinics");

        List<Clinic> clinicList = clinicBO.getAllClinics();
        if (clinicList == null) {
            return Response.status(404).entity(new Error(404, "No Clinic Found")).build();
        }
        return Response.ok(clinicBO.convertClinicsToClinicDetailsList(clinicList)).build();
    }

    @Override
    public Response getClinicById(int id) {
        LOGGER.info("api_method=getClinicById message= get clinic with id=" + id);

        Clinic clinic = clinicBO.getClinicById(id);
        if (clinic != null) {
            return Response.ok(clinicBO.convertClinicToClinicDetails(clinic)).build();
        }
        return Response.status(404).entity(new Error(404, "Clinic Not Found")).build();
    }

    @Override
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

    @Override
    public Response getDoctorsByClinicId(int clinicId) {
        LOGGER.info("api_method=getDoctorsByClinicId message=get doctors with clinicId=" + clinicId);

        Error error = serviceHelper.isClinicValid(clinicId);
        if (error != null) {
            return Response.status(400).entity(error).build();
        }

        List<Doctor> doctors = doctorBO.getDoctorsByClinicId(clinicId);
        return Response.ok(doctorBO.convertDoctorsToDoctorDetailsList(doctors)).build();
    }
}