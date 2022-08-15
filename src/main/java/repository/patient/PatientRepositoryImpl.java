package repository.patient;

import config.DBConfig;
import entity.Drug;
import entity.Patient;
import entity.Prescription;
import entity.Receipt;
import entity.enums.PrescriptionStatus;

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
                    where pa.id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            return new Prescription(resultSet.getLong("id"),
                    PrescriptionStatus.valueOf(resultSet.getString("status")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Receipt loadReceipt(long id) {
        Receipt receipt = null;
        String query = """
                select * from receipt_drugs rd
                inner join receipt r on r.id = rd.receipt_id
                inner join prescription p on p.id = r.prescription_id
                where p.patient_id = ? and r.status = 'UNPAID'
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if(receipt == null)
                    receipt = new Receipt(resultSet.getLong(6),
                        resultSet.getLong(7), resultSet.getFloat(8));
                receipt.getDrugs().add(new Drug(-1, resultSet.getString("name"),
                        resultSet.getFloat(4), resultSet.getInt(3)));
            }
            return receipt;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
