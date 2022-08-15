package repository.admin;

import config.DBConfig;
import entity.Prescription;
import entity.SimpleDrug;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepositoryImpl implements AdminRepository{
    @Override
    public boolean load(String password) {
        String query = """
                    select * from admin
                    where password = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Prescription[] loadAllPrescriptions(){
        Prescription[] prescriptions = new Prescription[1000];
        int index = 0;
        String query = """
                    select * from prescription
                    where status = 'PENDING'
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Prescription prescription = new Prescription(resultSet.getLong("patient_id"));
                prescription.setId(resultSet.getLong("id"));
                prescriptions[index++] = prescription;
            }
            return prescriptions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SimpleDrug[] loadPrescriptionsDrugs(long id){
        SimpleDrug[] drugs = new SimpleDrug[1000];
        int index = 0;
        String query = """
                    select * from prescription_drugs
                    where prescription_id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                SimpleDrug drug = new SimpleDrug(resultSet.getString("name"),resultSet.getInt("count"));
                drugs[index++] = drug;
            }
            return drugs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
