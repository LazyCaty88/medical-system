package edu.itu.sue.datamodel.dao;

import edu.itu.sue.datamodel.entity.Treatment;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component("treatmentDao")
public class TreatmentDAO {
    private static final Logger LOGGER = Logger.getLogger( TreatmentDAO.class.getName() );

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Treatment createTreatment(Treatment treatment) {
        LOGGER.info("api_method=createTreatment message=starts to persist treatment to DB, treatment=" + treatment.toString());
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(treatment);
        tx.commit();
        session.close();
        return treatment;
    }

    public Treatment getTreatmentById(int id) {
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM TREATMENT WHERE ID=" + id;
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Treatment.class);
        List<Treatment> results = query.list();

        if (results != null && (results.size() > 0)) {
            return results.get(0);
        }
        session.close();
        return null;
    }

    public List<Treatment> getAllTreatments() {
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM TREATMENT";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Treatment.class);
        List<Treatment> results = query.list();
        session.close();
        return results;
    }

    public List<Treatment> getTreatmentsByPatientId(int patientId) {
        Session session = sessionFactory.openSession();
        Query query = session.getNamedQuery("treatment.getTreatmentsByPatientId");
        query.setInteger("PATIENT_ID", patientId);
        List<Treatment> results = query.list();
        session.close();
        return results;
    }

    public List<Treatment> getTreatmentsByDoctorId(int doctorId) {
        Session session = sessionFactory.openSession();
        Query query = session.getNamedQuery("treatment.getTreatmentsByDoctorId");
        query.setInteger("DOCTOR_ID", doctorId);
        List<Treatment> results = query.list();
        session.close();
        return results;
    }
}
