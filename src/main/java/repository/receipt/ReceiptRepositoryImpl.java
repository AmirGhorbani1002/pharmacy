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

    public Receipt loadPatientReceipt(long id){
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

    public void update(Receipt receipt){
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
