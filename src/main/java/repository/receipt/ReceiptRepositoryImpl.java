package repository.receipt;

import config.DBConfig;
import entity.Drug;
import entity.Receipt;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReceiptRepositoryImpl {

    public void save(Receipt receipt) {
        String query = """
                    insert into receipt(prescription_id, price, status)
                    values (?,?,?)
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, receipt.getPrescriptionId());
            preparedStatement.setFloat(2, receipt.getPrice());
            preparedStatement.setString(3, receipt.getReceiptStatus().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDrug(long id, Drug drug) {
        String query = """
                    insert into receipt_drugs(name, count, price, receipt_id)
                    values (?,?,?,?)
                """;
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(query);
            preparedStatement.setString(1, drug.getName());
            preparedStatement.setInt(2, drug.getCount());
            preparedStatement.setFloat(3, drug.getPrice());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
