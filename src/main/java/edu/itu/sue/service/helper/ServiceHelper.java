package edu.itu.sue.service.helper;

import edu.itu.sue.biz.DoctorBO;
import edu.itu.sue.biz.PatientBO;
import edu.itu.sue.biz.ClinicBO;
import edu.itu.sue.intf.treatmentservice.CreateTreatmentRequest;
import edu.itu.sue.datamodel.entity.Doctor;
import edu.itu.sue.datamodel.entity.Patient;
import edu.itu.sue.datamodel.entity.Clinic;
import edu.itu.sue.service.ErrorHandling.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.util.Strings;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceHelper {

    @Autowired
    private PatientBO patientBO;
    @Autowired
    private ClinicBO clinicBO;
    @Autowired
    private DoctorBO doctorBO;

    public List<Error> validateCreateTreatmentRequest(CreateTreatmentRequest request) {
        List<Error> errors = new ArrayList<>();
        int patientId = request.getPatientId();
        int doctorId = request.getDoctorId();
        Error patientIdError = isPatientValid(patientId);
        Error doctorIdError = isDoctorValid(doctorId);
        if (patientIdError != null) {
            errors.add(patientIdError);
        }
        if (doctorIdError != null) {
            errors.add(doctorIdError);
        }

        if(Strings.isNullOrEmpty(request.getDiagnosis())){
            errors.add(new Error(400, "Diagnosis missing"));
        }

        if(Strings.isNullOrEmpty(request.getTreatmentType())){
            errors.add(new Error(400, "TreatmentType missing"));
        }
        return errors;
    }


    public Error isPatientValid(int patientId) {
        Patient patient = patientBO.getPatientById(patientId);
        if (patient != null) {
            return null;
        }
        return new Error(400, "Patient is invalid");
    }

    public Error isDoctorValid(int doctorId) {
        Doctor doctor = doctorBO.getDoctorById(doctorId);
        if (doctor != null) {
            return null;
        }
        return new Error(400, "Doctor is invalid");
    }

    public Error isClinicValid(int clinicId) {
        Clinic clinic = clinicBO.getClinicById(clinicId);
        if (clinic != null) {
            return null;
        }
        return new Error(400, "Clinic is invalid");
    }
}
