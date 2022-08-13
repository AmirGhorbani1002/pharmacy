package repository.patient;

import config.DBConfig;
import entity.Patient;

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
            preparedStatement.close();
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
            preparedStatement.close();
            if (!resultSet.next())
                return null;
            Patient patient = new Patient(resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("national_code"));
            patient.setId(resultSet.getLong("id"));
            resultSet.close();
            return patient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
