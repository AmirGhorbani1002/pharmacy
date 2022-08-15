package repository.receipt;

import config.DBConfig;
import entity.Drug;
import entity.Receipt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReceiptRepositoryImpl {

    public void save(Receipt receipt) {
        String query = """
                    insert into receipt(prescription_id, price, status)
                    values (?,?,?)
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, receipt.getPrescriptionId());
            preparedStatement.setFloat(2, receipt.getPrice());
            preparedStatement.setString(3, receipt.getReceiptStatus().name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            receipt.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Receipt load(long id){
        String query = """
                    select * from receipt
                    where prescription_id = ?
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next())
                return null;
            return new Receipt(resultSet.getLong("id"),
                    resultSet.getLong("prescription_id"),resultSet.getFloat("price"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDrug(long id, Drug drug, int number) {
        String query = """
                    insert into receipt_drugs(name, count, price, receipt_id)
                    values (?,?,?,?)
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, drug.getName());
            preparedStatement.setInt(2, number);
            preparedStatement.setFloat(3, drug.getPrice() * number);
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
