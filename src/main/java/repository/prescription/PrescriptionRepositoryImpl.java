package repository.prescription;

import config.DBConfig;
import entity.Prescription;
import entity.SimpleDrug;
import entity.enums.PrescriptionStatus;
import repository.BaseRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrescriptionRepositoryImpl implements BaseRepository<Prescription> {
    @Override
    public void save(Prescription prescription) {
        String query = """
                    insert into prescription(patient_id, status)
                    values(?,?)
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, prescription.getPatientId());
            preparedStatement.setString(2, prescription.getStatus().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(long id) {
        String query = """
                    delete from prescription
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

    @Override
    public Prescription load(long id) {
        String query = """
                    select * from prescription
                    where id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            Prescription prescription = new Prescription(resultSet.getLong("patient_id"),
                    PrescriptionStatus.valueOf(resultSet.getString("status")));
            prescription.setId(resultSet.getLong("id"));
            return prescription;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Prescription prescription) {
        String query = """
                    update prescription
                    set status = ?
                    where id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, prescription.getStatus().name());
            preparedStatement.setLong(2, prescription.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(long id, PrescriptionStatus status) {
        String query = """
                    update prescription
                    set status = ?
                    where id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, status.name());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDrug(long id, SimpleDrug drug){
        String query = """
                    insert into prescription_drugs(name, count, prescription_id)
                    values(?,?,?)
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, drug.getName());
            preparedStatement.setInt(2, drug.getCount());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
