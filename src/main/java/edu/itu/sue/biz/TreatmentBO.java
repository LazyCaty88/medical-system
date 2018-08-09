package edu.itu.sue.biz;

import edu.itu.sue.datamodel.dao.TreatmentDAO;
import edu.itu.sue.intf.treatmentservice.CreateTreatmentRequest;
import edu.itu.sue.intf.treatmentservice.TreatmentDetails;
import edu.itu.sue.datamodel.entity.Doctor;
import edu.itu.sue.datamodel.entity.Patient;
import edu.itu.sue.datamodel.entity.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class TreatmentBO {
    @Autowired
    private TreatmentDAO treatmentDao;
    @Autowired
    private PatientBO patientBO;
    @Autowired
    private DoctorBO doctorBO;

    public Treatment createTreatment(CreateTreatmentRequest request) {
        Treatment treatment = new Treatment();
        treatment.setTreatmentType(request.getTreatmentType().trim());
        treatment.setDiagnosis(request.getDiagnosis().trim());
        Calendar currentDate = Calendar.getInstance();
        treatment.setDateAdded(currentDate);
        treatment.setDateLastUpdated(currentDate);
        treatment.setPatient(patientBO.getPatientById(request.getPatientId()));
        treatment.setDoctor(doctorBO.getDoctorById(request.getDoctorId()));
        return treatmentDao.createTreatment(treatment);
    }

    public TreatmentDetails convertTreatmentToTreatmentDetails(Treatment treatment) {
        TreatmentDetails treatmentDetails = new TreatmentDetails();
        treatmentDetails.setId(treatment.getId());
        treatmentDetails.setTreatmentType(treatment.getTreatmentType());
        treatmentDetails.setDiagnosis(treatment.getDiagnosis());
        Patient patient = treatment.getPatient();
        Doctor doctor = treatment.getDoctor();
        treatmentDetails.setPatientId(patient.getId());
        treatmentDetails.setPatientName(patient.getName());
        treatmentDetails.setDoctorId(doctor.getId());
        treatmentDetails.setDoctorName(doctor.getName());
        treatmentDetails.setClinicName(doctor.getClinic().getName());
        treatmentDetails.setDateAdded(treatment.getDateAdded().getTime().toString());
        treatmentDetails.setDateLastUpdated(treatment.getDateLastUpdated().getTime().toString());
        return treatmentDetails;
    }

    public List<TreatmentDetails> convertTreatmentsToTreatmentDetailsList(List<Treatment> treatmentList){
        List<TreatmentDetails> patientDetailsList = new ArrayList<>();
        for (Treatment treatment : treatmentList) {
            TreatmentDetails treatmentDetails = convertTreatmentToTreatmentDetails(treatment);
            patientDetailsList.add(treatmentDetails);
        }
        return patientDetailsList;
    }

    public List<Treatment> getAllTreatments() {
        return treatmentDao.getAllTreatments();
    }

    public Treatment getTreatmentById(int id) {
        return treatmentDao.getTreatmentById(id);
    }

    public List<Treatment> getTreatmentByPatientId(int patientId) {
        return treatmentDao.getTreatmentsByPatientId(patientId);
    }

    public List<Treatment> getTreatmentByDoctorId(int doctorId) {
        return treatmentDao.getTreatmentsByDoctorId(doctorId);
    }
}
