package repository.patient;

import config.DBConfig;
import entity.*;
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
                    select pa.id,pr.id,status from patient pa
                    inner join prescription pr on pa.id = pr.patient_id
                    where pa.id = ? and (status = 'PENDING' or status = 'ACCEPT')
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            return new Prescription(resultSet.getLong(2), resultSet.getLong(1),
                    PrescriptionStatus.valueOf(resultSet.getString("status")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SimpleDrug[] loadPrescriptionsDrugs(long id) {
        SimpleDrug[] drugs = new SimpleDrug[1000];
        int index = 0;
        String query = """
                    select * from prescription_drugs pd
                    where prescription_id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SimpleDrug drug = new SimpleDrug(resultSet.getString("name"), resultSet.getInt("count"));
                drugs[index++] = drug;
            }
            return drugs;
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
                if (receipt == null)
                    receipt = new Receipt(resultSet.getLong(6),
                            resultSet.getLong(7), resultSet.getFloat(8));
                receipt.getDrugs().add(new Drug(resultSet.getLong(1), resultSet.getString("name"),
                        resultSet.getFloat(4), resultSet.getInt(3)));
            }
            return receipt;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeDrugFromReceipt(long id) {
        String query = """
                    delete from receipt_drugs
                    where id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeNumberOfDrug(int count, String name) {
        String query = """
                    update drugs
                    set count = count + ?
                    where name = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, count);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeReceipt(Receipt receipt) {
        String query = """
                   update receipt
                   set status = ?, price = ?
                   where id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, receipt.getReceiptStatus().name());
            preparedStatement.setFloat(2, receipt.getPrice());
            preparedStatement.setLong(3, receipt.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
