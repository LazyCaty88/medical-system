package edu.itu.sue.service;

import edu.itu.sue.biz.TreatmentBO;
import edu.itu.sue.datamodel.entity.Treatment;
import edu.itu.sue.intf.treatmentservice.CreateTreatmentRequest;
import edu.itu.sue.intf.treatmentservice.TreatmentService;
import edu.itu.sue.service.ErrorHandling.Error;
import edu.itu.sue.service.helper.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Service("treatmentService")
public class TreatmentServiceImpl implements TreatmentService {
    private static final Logger LOGGER = Logger.getLogger(TreatmentServiceImpl.class.getName());

    @Autowired
    private TreatmentBO treatmentBO;
    @Autowired
    private ServiceHelper serviceHelper;

    @Override
    public Response createTreatment(CreateTreatmentRequest request) {
        LOGGER.info("api_method=createTreatment message=received createTreatment request, createTreatmentRequest=" + request.toString());
        List<Error> errors = serviceHelper.validateCreateTreatmentRequest(request);
        if (errors.size() > 0) {
            return Response.status(400).entity(errors).build();
        }
        Treatment treatment = treatmentBO.createTreatment(request);
        return Response.ok(treatmentBO.convertTreatmentToTreatmentDetails(treatment)).build();
    }

    @Override
    public Response getAllTreatments() {
        LOGGER.info("api_method=getAllTreatments message=get all existing treatments");

        List<Treatment> treatments = treatmentBO.getAllTreatments();
        if (treatments == null) {
            return Response.status(404).entity(new Error(404, "No Treatment Found")).build();
        }
        return Response.ok(treatmentBO.convertTreatmentsToTreatmentDetailsList(treatments)).build();
    }

    @Override
    public Response getTreatmentById(int id) {
        LOGGER.info("api_method=getTreatmentById message= get treatment with id=" + id);

        Treatment treatment = treatmentBO.getTreatmentById(id);
        if (treatment == null) {
            return Response.status(404).entity(new Error(404, "Treatment Not Found")).build();
        }
        return Response.ok(treatmentBO.convertTreatmentToTreatmentDetails(treatment)).build();
    }

    @Override
    public Response getTreatmentByPatientId(int patientId) {
        LOGGER.info("api_method=getTreatmentByPatientId message= get all treatments of patient with id=" + patientId);

        Error error = serviceHelper.isPatientValid(patientId);
        if (error != null) {
            return Response.status(400).entity(error).build();
        }

        List<Treatment> treatments = treatmentBO.getTreatmentByPatientId(patientId);
        return Response.ok(treatmentBO.convertTreatmentsToTreatmentDetailsList(treatments)).build();
    }

    @Override
    public Response getTreatmentByDoctorId(int doctorId) {
        LOGGER.info("api_method=getTreatmentByDoctorId message= get all treatments of doctor with id=" + doctorId);

        Error error = serviceHelper.isDoctorValid(doctorId);
        if (error != null) {
            return Response.status(400).entity(error).build();
        }

        List<Treatment> treatments = treatmentBO.getTreatmentByDoctorId(doctorId);
        return Response.ok(treatmentBO.convertTreatmentsToTreatmentDetailsList(treatments)).build();
    }
}