package net.myCompany.database.repositories;

import com.sun.xml.internal.fastinfoset.util.CharArray;
import net.myCompany.database.AuthenticationData;
import net.myCompany.database.repositories.specifications.SQLSpecification;
import net.myCompany.database.user.UserIM;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthenticationDataRepositoryImpl implements AuthenticationDataRepository {

    Connection db;

    public AuthenticationDataRepositoryImpl(Connection db) {
        this.db = db;
    }

    @Override
    public void add(AuthenticationData data, UserIM user) throws SQLException {
        String sql = "INSERT INTO authentication_data(login, passwd, userid) " +
                "VALUES (?, ?, ?);";
        PreparedStatement ps = db.prepareStatement(sql);

        ps.setString(1, data.getLogin());
        ps.setString(2, String.valueOf(data.getPassword()));
        ps.setInt(3, user.getId());

        ps.execute();
        ps.close();
    }

    @Override
    public List<AuthenticationData> query(SQLSpecification specification) throws SQLException {
        Statement statement = db.createStatement();
        ResultSet rs = statement.executeQuery(specification.toSQLClauses());

        List<AuthenticationData> list = new Mapper<ResultSet, List<AuthenticationData>>() {
            @Override
            public List<AuthenticationData> map(ResultSet resultSet) throws SQLException {
                List<AuthenticationData> list = new ArrayList<>();

                while (resultSet.next()) {
                    String login = resultSet.getString("login");
                    char[] passwd  = resultSet.getString("passwd").toCharArray();

                    list.add(new AuthenticationData(login, passwd));
                }

                return list;
            }
        }.map(rs);

        return list;
    }
}
