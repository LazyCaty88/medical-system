package com.sue.biz;

import com.sue.dao.ClinicDAO;
import com.sue.intf.ClinicDetails;
import com.sue.intf.CreateClinicRequest;
import com.sue.model.Clinic;
import com.sue.service.ErrorHandling.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ClinicBO {
    private static final Logger LOGGER = Logger.getLogger(ClinicBO.class.getName());

    @Autowired
    private ClinicDAO clinicDao;
    @Autowired
    private BizHelper bizHelper;

    public Clinic createClinic(CreateClinicRequest request) {
        Clinic clinic = new Clinic();
        clinic.setEmail(request.getEmail());
        clinic.setName(request.getName());
        clinic.setPhone(request.getPhone());
        clinic.setSpecializationType(request.getSpecializationType());
        Calendar currentDate = Calendar.getInstance();
        clinic.setDateAdded(currentDate);
        clinic.setDateLastUpdated(currentDate);
        clinicDao.createClinic(clinic);
        return clinic;
    }

    public ClinicDetails convertClinicToClinicDetails(Clinic clinic) {
        ClinicDetails clinicDetails = new ClinicDetails();
        clinicDetails.setId(clinic.getId());
        clinicDetails.setName(clinic.getName());
        clinicDetails.setEmail(clinic.getEmail().trim().toLowerCase());
        clinicDetails.setPhone(clinic.getPhone());
        clinicDetails.setSpecializationType(clinic.getSpecializationType());
        clinicDetails.setDateAdded(clinic.getDateAdded().getTime().toString());
        clinicDetails.setDateLastUpdated(clinic.getDateLastUpdated().getTime().toString());
        return clinicDetails;
    }

    public List<Clinic> getAllClinics() {
        return clinicDao.getAllClinics();
    }

    public Clinic getClinicById(int id) {
        return clinicDao.getClinicById(id);
    }

    public List<Error> validateCreateClinicRequest(CreateClinicRequest request, List<Error> errorList) {

        if (request.getEmail() == null || !bizHelper.isValideEmail(request.getEmail())) {
            errorList.add(new Error(400, "Bad request: defaultEmail error"));
        }

        if (isClinicEmailExist(request.getEmail())) {
            errorList.add(new Error(400, "Bad request: defaultEmail already used"));
        }

        if (request.getName() == null) {
            errorList.add(new Error(400, "Bad request: name error"));
        }

        if (request.getPhone() == null) {
            errorList.add(new Error(400, "Bad request: phone number error"));
        }

        return errorList;
    }

    public boolean isClinicEmailExist(String email) {
        return clinicDao.getClinicByEmail(email) != null;
    }

    public List<ClinicDetails> convertClinicsToClinicDetailsList(List<Clinic> clinicList) {
        List<ClinicDetails> clinicDetailsList = new ArrayList<>();
        for(Clinic clinic : clinicList){
            clinicDetailsList.add(convertClinicToClinicDetails(clinic));
        }
        return clinicDetailsList;
    }

    public List<Error> validateUpdateClinicRequest(CreateClinicRequest request, List<Error> errorList) {
        if (request.getEmail() == null || !bizHelper.isValideEmail(request.getEmail())) {
            errorList.add(new Error(400, "Bad request: email error"));
        }
        if (!isEmailExist(request.getEmail())) {
            errorList.add(new Error(400, "Bad request: clinic with this email does not exist"));
        }
        return errorList;
    }

    public boolean isEmailExist(String email) {
        return clinicDao.getClinicByEmail(email) != null;
    }

    public Clinic updateClinic(CreateClinicRequest request) {
        String email = request.getEmail().toLowerCase();
        Clinic clinic = clinicDao.getClinicByEmail(email);

        LOGGER.info("api_method=updateClinic message=before update clinic=" + convertClinicToClinicDetails(clinic));

        if(request.getName() != null){
            clinic.setName(request.getName());
        }
        if(request.getPhone() != null){
            clinic.setPhone(request.getPhone());
        }
        if(request.getSpecializationType() != null){
            clinic.setSpecializationType(request.getSpecializationType());
        }
        clinic.setDateLastUpdated(Calendar.getInstance());
        clinicDao.updateClinic(clinic);
        return clinic;
    }
}
