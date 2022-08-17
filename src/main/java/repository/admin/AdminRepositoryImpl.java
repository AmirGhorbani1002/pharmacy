package repository.admin;

import config.DBConfig;
import repository.admin.interfaces.AdminRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepositoryImpl implements AdminRepository {
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

}
