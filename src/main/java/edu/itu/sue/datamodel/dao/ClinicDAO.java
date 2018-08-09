package edu.itu.sue.datamodel.dao;

import edu.itu.sue.datamodel.entity.Clinic;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("clinicDao")
public class ClinicDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Clinic createClinic(Clinic clinic) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(clinic);
        tx.commit();
        session.close();
        return clinic;
    }

    public Clinic getClinicById(int id) {
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM CLINIC WHERE ID=" + id;
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Clinic.class);
        List<Clinic> results = query.list();

        if (results != null && (results.size() > 0)) {
            return results.get(0);
        }
        session.close();
        return null;
    }

    public List<Clinic> getAllClinics() {
        Session session = sessionFactory.openSession();
        String sql = "SELECT * FROM CLINIC";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Clinic.class);
        List<Clinic> results = query.list();
        session.close();
        return results;
    }

    public Clinic getClinicByEmail(String email) {
        Session session = sessionFactory.openSession();
        Query query = session.getNamedQuery("clinic.getClinicByEmail");
        query.setString("EMAIL", email);

        List<Clinic> results = query.list();
        if (results != null && (results.size() > 0)) {
            return results.get(0);
        }
        session.close();
        return null;
    }

    public void updateClinic(Clinic clinic) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(clinic);
        tx.commit();
        session.close();
    }
}
