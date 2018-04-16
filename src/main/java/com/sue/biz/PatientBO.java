package com.sue.biz;

import com.sue.dao.PatientDAO;
import com.sue.intf.CreatePatientRequest;
import com.sue.intf.Date;
import com.sue.intf.PatientInfo;
import com.sue.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Component
public class PatientBO {
    @Autowired
    private PatientDAO patientDAO;
    public Patient createPatient(CreatePatientRequest request) {
        Patient patient = new Patient();
        patient.setName(request.getName());
        patient.setDefaultEmail(request.getDefaultEmail());
        Calendar currentDate = Calendar.getInstance(); //get current date
        patient.setDateAdded(currentDate);
        patient.setDateLastUpdated(currentDate);
        Date birthday = request.getBirthDate();
        int age = currentDate.get(Calendar.YEAR) - birthday.getYear();
        patient.setAge(age);
        patient.setBirthday(new GregorianCalendar(birthday.getYear(), birthday.getMonth(), birthday.getDay()));
        patient = patientDAO.createPatient(patient);
        return patient;

    }

    public PatientInfo showPatient(Patient patient) {
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setId(patient.getId());
        patientInfo.setName(patient.getName());
        patientInfo.setAge(patient.getAge());
        patientInfo.setDefaultEmail(patient.getDefaultEmail());
        patientInfo.setBirthday(convertCalenderToStr(patient.getBirthday()));
        patientInfo.setDateAdded(patient.getDateAdded().getTime().toString());
        patientInfo.setDateLastUpdated(patient.getDateLastUpdated().getTime().toString());
        return patientInfo;
    }

    private String convertCalenderToStr(Calendar birthday) {
        return birthday.get(Calendar.YEAR) + "/" + birthday.get(Calendar.MONTH) + "/" + birthday.get(Calendar.DATE);
    }


}
