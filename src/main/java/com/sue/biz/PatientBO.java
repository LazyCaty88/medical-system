package com.sue.biz;

import com.sue.dao.PatientDAO;
import com.sue.intf.CreatePatientRequest;
import com.sue.intf.PatientDetails;
import com.sue.model.Patient;
import com.sue.model.common.Date;
import com.sue.service.ErrorHandling.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

@Component
public class PatientBO {
    private static final Logger LOGGER = Logger.getLogger(PatientBO.class.getName());

    @Autowired
    private PatientDAO patientDao;
    @Autowired
    private BizHelper bizHelper;

    public Patient createPatient(CreatePatientRequest request) {
        Patient patient = new Patient();
        patient.setDefaultEmail(request.getDefaultEmail().toLowerCase());
        patient.setName(request.getName());
        patient.setGender(request.getGender());
        patient.setPhone(request.getPhone());
        Date date = request.getBirthDate();
        Calendar currentDate = Calendar.getInstance();
        patient.setDateAdded(currentDate);
        patient.setDateLastUpdated(currentDate);
        int age = currentDate.get(Calendar.YEAR) - date.getYear();
        patient.setAge(age);
        patient.setBirthDate(new GregorianCalendar(date.getYear(), date.getMonth(), date.getDay()));
        patientDao.createPatient(patient);
        return patient;
    }

    public PatientDetails convertPatientToPatientDetails(Patient patient) {
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setId(patient.getId());
        patientDetails.setName(patient.getName());
        patientDetails.setDefaultEmail(patient.getDefaultEmail().trim().toLowerCase());
        patientDetails.setGender(patient.getGender());
        patientDetails.setPhone(patient.getPhone());
        patientDetails.setAge(patient.getAge());
        Calendar birthDate = patient.getBirthDate();
        String birthDateStr = convertDateToStr(birthDate);
        patientDetails.setBirthDate(birthDateStr);
        patientDetails.setDateAdded(patient.getDateAdded().getTime().toString());
        patientDetails.setDateLastUpdated(patient.getDateLastUpdated().getTime().toString());
        return patientDetails;
    }

    public List<PatientDetails> convertPatientsToPatientDetailsList(List<Patient> patientList) {
        List<PatientDetails> patientDetailsList = new ArrayList<>();
        for(Patient patient : patientList){
            patientDetailsList.add(convertPatientToPatientDetails(patient));
        }
        return patientDetailsList;
    }

    private String convertDateToStr(Calendar birthDate) {
        return birthDate.get(Calendar.YEAR) + "/" + birthDate.get(Calendar.MONTH) + "/" + birthDate.get(Calendar.DATE);
    }

    public List<Patient> getAllPatients() {
        return patientDao.getAllPatients();
    }

    public Patient getPatientById(int id) {
        return patientDao.getPatientById(id);
    }

    public Patient updatePatient(CreatePatientRequest request) {
        String defaultEmail = request.getDefaultEmail().toLowerCase();
        Patient patient = patientDao.getPatientByDefaultEmail(defaultEmail);

        LOGGER.info("api_method=updatePatient message=before update patient=" + convertPatientToPatientDetails(patient));

        if(request.getName() != null){
            patient.setName(request.getName());
        }
        if(request.getGender() != null){
            patient.setGender(request.getGender());
        }
        if(request.getPhone() != null){
            patient.setPhone(request.getPhone());
        }
        Calendar currentDate = Calendar.getInstance();
        if(request.getBirthDate() != null) {
            Date date = request.getBirthDate();
            int age = currentDate.get(Calendar.YEAR) - date.getYear();
            patient.setAge(age);
            patient.setBirthDate(new GregorianCalendar(date.getYear(), date.getMonth(), date.getDay()));
        }
        patient.setDateLastUpdated(currentDate);
        patientDao.updatePaitent(patient);
        return patient;
    }

    public List<Error> validateCreatePatientRequest(CreatePatientRequest request, List<Error> errorList) {
        if (request.getDefaultEmail() == null || !bizHelper.isValideEmail(request.getDefaultEmail())) {
            errorList.add(new Error(400, "Bad request: defaultEmail error"));
        }

        if (isDefaultEmailExist(request.getDefaultEmail())) {
            errorList.add(new Error(400, "Bad request: defaultEmail already used"));
        }
        if (request.getName() == null) {
            errorList.add(new Error(400, "Bad request: name error"));
        }

        if (request.getBirthDate() == null) {
            errorList.add(new Error(400, "Bad request: birthDate error"));
        }

        if (request.getGender() == null) {
            errorList.add(new Error(400, "Bad request: gender missing"));
        }

        if (request.getPhone() == null) {
            errorList.add(new Error(400, "Bad request: phone missing"));
        }
        return errorList;
    }

    public boolean isDefaultEmailExist(String defaultEmail) {
        return patientDao.getPatientByDefaultEmail(defaultEmail) != null;
    }

    public List<Error> validateUpdatePatientRequest(CreatePatientRequest request, List<Error> errorList) {
        if (request.getDefaultEmail() == null || !bizHelper.isValideEmail(request.getDefaultEmail())) {
            errorList.add(new Error(400, "Bad request: defaultEmail error"));
        }
        if (!isDefaultEmailExist(request.getDefaultEmail())) {
            errorList.add(new Error(400, "Bad request: patient with this defaultEmail does not exist"));
        }
        return errorList;
    }
}
