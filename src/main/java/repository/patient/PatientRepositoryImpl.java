package repository.patient;

import config.DBConfig;
import entity.Patient;
import entity.Prescription;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import util.list.MyList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientRepositoryImpl implements PatientRepository {
    @Override
    public void save(Patient patient) {
        String query = """
                    insert into patient(firstname, lastname, national_code)
                    values(?,?,?)
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, patient.getFirstname());
            preparedStatement.setString(2, patient.getLastname());
            preparedStatement.setString(3, patient.getNationalCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Patient load(String nationalCode) {
        String query = """
                    select * from patient
                    where national_code = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, nationalCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            Patient patient = new Patient(resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("national_code"));
            patient.setId(resultSet.getLong("id"));
            return patient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Prescription loadPrescription(long id) {
        String query = """
                    select pr.id,status from patient pa
                    inner join prescription pr on pa.id = pr.patient_id
                    where pr.status = ? and pa.id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, PrescriptionStatus.PENDING.name());
            preparedStatement.setLong(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            return new Prescription(resultSet.getLong("id"),
                    PrescriptionStatus.valueOf(resultSet.getString("status")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MyList<SimpleDrug> loadPrescriptionsDrugs(long id) {
        MyList<SimpleDrug> drugs = new MyList<>();
        String query = """
                    select * from prescription_drugs pd
                    where prescription_id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                SimpleDrug drug = new SimpleDrug(resultSet.getString("name"),resultSet.getInt("count"));
                drugs.add(drug);
            }
            return drugs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
