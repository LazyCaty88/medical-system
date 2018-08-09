package edu.itu.sue.biz;

import edu.itu.sue.datamodel.dao.DoctorDAO;
import edu.itu.sue.intf.clinicservice.CreateDoctorRequest;
import edu.itu.sue.intf.clinicservice.DoctorDetails;
import edu.itu.sue.datamodel.entity.Clinic;
import edu.itu.sue.datamodel.entity.Doctor;
import edu.itu.sue.service.ErrorHandling.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.util.Strings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DoctorBO {
    private static final Logger LOGGER = Logger.getLogger(DoctorBO.class.getName());

    @Autowired
    private DoctorDAO doctorDao;
    @Autowired
    private BizHelper bizHelper;
    @Autowired
    private ClinicBO clinicBO;

    public boolean isDoctorDefaultEmailExist(String defaultEmail) {
        return doctorDao.getDoctorByDefaultEmail(defaultEmail) != null;
    }

    public List<Error> validateCreateDoctorRequest(CreateDoctorRequest request, List<Error> errorList) {
        String defaultEmail = request.getDefaultEmail();
        if (defaultEmail == null || !bizHelper.isValideEmail(defaultEmail)) {
            errorList.add(new Error(400, "Bad request: defaultEmail error"));
        }

        if (isDoctorDefaultEmailExist(defaultEmail)) {
            errorList.add(new Error(400, "Bad request: defaultEmail already used"));
        }

        if (request.getName() == null) {
            errorList.add(new Error(400, "Bad request: name missing"));
        }

        if (request.getPhone() == null) {
            errorList.add(new Error(400, "Bad request: phone number missing"));
        }

        if (request.getGender() == null) {
            errorList.add(new Error(400, "Bad request: gender missing"));
        }

        if (request.getTitle() == null) {
            errorList.add(new Error(400, "Bad request: gender missing"));
        }

        if (request.getSpecialties() == null) {
            errorList.add(new Error(400, "Bad request: specialties missing"));
        }

        int clinicId = request.getClinicId();
        if (clinicBO.getClinicById(clinicId) == null) {
            errorList.add(new Error(400, "Bad request: specialties error"));
        }
        return errorList;
    }

    public Doctor createDoctor(CreateDoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setDefaultEmail(request.getDefaultEmail().trim().toLowerCase());
        doctor.setName(request.getName());
        doctor.setGender(request.getGender());
        doctor.setPhone(request.getPhone());
        doctor.setTitle(request.getTitle());
        doctor.setSpecialties(request.getSpecialties());
        Calendar currentDate = Calendar.getInstance();
        doctor.setDateAdded(currentDate);
        doctor.setDateLastUpdated(currentDate);
        doctor.setClinic(clinicBO.getClinicById(request.getClinicId()));
        doctorDao.createDoctor(doctor);
        return doctor;
    }

    public Doctor getDoctorById(int doctorId) {
        return doctorDao.getDoctorById(doctorId);
    }

    public List<Doctor> getDoctorsByClinicId(int clinicId) {
        return doctorDao.getDoctorByClinicId(clinicId);
    }

    public DoctorDetails convertDoctorToDoctorDetails(Doctor doctor) {
        DoctorDetails doctorDetails = new DoctorDetails();
        doctorDetails.setId(doctor.getId());
        doctorDetails.setName(doctor.getName());
        doctorDetails.setDefaultEmail(doctor.getDefaultEmail());
        doctorDetails.setGender(doctor.getGender());
        doctorDetails.setTitle(doctor.getTitle());
        doctorDetails.setPhone(doctor.getPhone());
        doctorDetails.setSpecialties(doctor.getSpecialties());
        doctorDetails.setClinicName(doctor.getClinic().getName());
        doctorDetails.setDateAdded(doctor.getDateAdded().getTime().toString());
        doctorDetails.setDateLastUpdated(doctor.getDateLastUpdated().getTime().toString());
        return doctorDetails;
    }

    public List<DoctorDetails> convertDoctorsToDoctorDetailsList(List<Doctor> doctorList) {
        List<DoctorDetails> doctorDetailsList = new ArrayList<>();
        for(Doctor doctor : doctorList){
            doctorDetailsList.add(convertDoctorToDoctorDetails(doctor));
        }
        return doctorDetailsList;
    }

    public List<Error> validateUpdateDoctorRequest(CreateDoctorRequest request, List<Error> errorList) {
        if (request.getDefaultEmail() == null || !bizHelper.isValideEmail(request.getDefaultEmail())) {
            errorList.add(new Error(400, "Bad request: email error"));
        }
        if (!isEmailExist(request.getDefaultEmail())) {
            errorList.add(new Error(400, "Bad request: clinic with this email does not exist"));
        }
        return errorList;
    }

    public boolean isEmailExist(String defaultEmail) {
        return doctorDao.getDoctorByDefaultEmail(defaultEmail) != null;
    }

    public Doctor updateDoctor(CreateDoctorRequest request) {
        String defaultEmail = request.getDefaultEmail().toLowerCase();
        Doctor doctor = doctorDao.getDoctorByDefaultEmail(defaultEmail);

        LOGGER.info("api_method=updateDoctor message=before update Doctor=" + convertDoctorToDoctorDetails(doctor));

        if(!Strings.isNullOrEmpty(request.getName())){
            doctor.setName(request.getName());
        }
        if(!Strings.isNullOrEmpty(request.getGender())){
            doctor.setGender(request.getGender());
        }
        if(!Strings.isNullOrEmpty(request.getPhone())){
            doctor.setPhone(request.getPhone());
        }
        if(!Strings.isNullOrEmpty(request.getTitle())){
            doctor.setTitle(request.getTitle());
        }
        if(!Strings.isNullOrEmpty(request.getSpecialties())){
            doctor.setSpecialties(request.getSpecialties());
        }
        Clinic clinic = clinicBO.getClinicById(request.getClinicId());
        if(clinic != null){
            doctor.setClinic(clinic);
        }

        Calendar currentDate = Calendar.getInstance();
        doctor.setDateLastUpdated(currentDate);
        doctorDao.updateDoctor(doctor);
        return doctor;
    }
}
