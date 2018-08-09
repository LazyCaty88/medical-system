package edu.itu.sue.datamodel.dao;

import edu.itu.sue.datamodel.entity.Patient;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("patientDao")
public class PatientDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Patient createPatient(Patient patient) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(patient);
        tx.commit();
        session.close();
        return patient;
    }

    public Patient getPatientById(int id) {
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM PATIENT WHERE ID=" + id;
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Patient.class);
        List<Patient> results = query.list();

        if (results != null && (results.size() > 0)) {
            return results.get(0);
        }
        session.close();
        return null;
    }

    public List<Patient> getAllPatients() {
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM PATIENT";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Patient.class);
        List<Patient> results = query.list();
        session.close();
        return results;
    }

    public Patient getPatientByDefaultEmail(String defaultEmail) {
        Session session = sessionFactory.openSession();
        Query query = session.getNamedQuery("patient.getPatientByDefaultEmail");
        query.setString("DEFAULT_EMAIL", defaultEmail);

        List<Patient> results = query.list();
        if (results != null && (results.size() > 0)) {
            return results.get(0);
        }
        session.close();
        return null;
    }

    public void updatePaitent(Patient patient) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(patient);
        tx.commit();
        session.close();
    }
}
