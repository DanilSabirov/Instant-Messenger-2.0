package net.myCompany.database.repositories;

import net.myCompany.database.AuthenticationData;
import net.myCompany.database.repositories.specifications.SQLSpecification;
import net.myCompany.database.user.UserIM;

import java.sql.SQLException;
import java.util.List;

public interface AuthenticationDataRepository {
    void add(AuthenticationData data, UserIM user) throws SQLException;

    List<AuthenticationData> query(SQLSpecification specification) throws SQLException;
}
