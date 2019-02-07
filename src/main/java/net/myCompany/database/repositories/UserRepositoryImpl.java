package net.myCompany.database.repositories;

import net.myCompany.database.repositories.specifications.SQLSpecification;
import net.myCompany.database.user.User;
import net.myCompany.database.user.UserIM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private Connection db;

    public UserRepositoryImpl(Connection db) {
        this.db = db;
    }

    @Override
    public void add(User user) throws SQLException {
        String sql =  "INSERT INTO users (username, email) " +
                "VALUES (?, ?);";
        PreparedStatement ps = db.prepareStatement(sql);

        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());

        ps.execute();
        ps.close();
    }

    @Override
    public void remove(UserIM user) throws SQLException {
        String sql = "UPDATE users SET isremoved = true WHERE userid = ?;";
        PreparedStatement ps = db.prepareStatement(sql);

        ps.setInt(1, user.getId());

        ps.executeUpdate();
        ps.close();
    }

    @Override
    public List<UserIM> query(SQLSpecification specification) throws SQLException {
        Statement statement = db.createStatement();
        statement.executeQuery(specification.toSQLClauses());

        List<UserIM> users;
        ResultSet rs = statement.getResultSet();

        users = new Mapper<ResultSet, List<UserIM>>() {
            @Override
            public List<UserIM> map(ResultSet resultSet) throws SQLException {
                List<UserIM> list = new ArrayList<>();

                while (resultSet.next()) {
                    int userid = resultSet.getInt("userid");
                    String name = resultSet.getString("username");
                    String email = resultSet.getString("email");

                    list.add(new UserIM(userid, name, email));
                }

                return list;
            }
        }.map(rs);

        return users;
    }
}
