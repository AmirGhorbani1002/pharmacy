package repository.admin;

import config.DBConfig;

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
            preparedStatement.close();
            if(!resultSet.next()){
                resultSet.close();
                return false;
            }
            resultSet.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}