package edu.itu.sue.datamodel.dao;

import edu.itu.sue.datamodel.entity.Doctor;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Doctor createDoctor(Doctor doctor) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(doctor);
        tx.commit();
        session.close();
        return doctor;
    }

    public Doctor getDoctorByDefaultEmail(String defaultEmail) {
        Session session = sessionFactory.openSession();
        Query query = session.getNamedQuery("doctor.getDoctorByDefaultEmail");
        query.setString("DEFAULT_EMAIL", defaultEmail);

        List<Doctor> results = query.list();
        if (results != null && (results.size() > 0)) {
            return results.get(0);
        }
        session.close();
        return null;
    }

    public Doctor getDoctorById(int doctorId) {
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM DOCTOR WHERE ID=" + doctorId;
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Doctor.class);
        List<Doctor> results = query.list();

        if (results != null && (results.size() > 0)) {
            return results.get(0);
        }
        session.close();
        return null;
    }

    public List<Doctor> getDoctorByClinicId(int clinicId) {
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM DOCTOR WHERE CLINIC_ID=" + clinicId;
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Doctor.class);
        return query.list();
    }

    public void updateDoctor(Doctor doctor) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(doctor);
        tx.commit();
        session.close();
    }
}
