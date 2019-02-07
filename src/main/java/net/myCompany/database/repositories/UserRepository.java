package net.myCompany.database.repositories;

import net.myCompany.database.repositories.specifications.SQLSpecification;
import net.myCompany.database.user.User;
import net.myCompany.database.user.UserIM;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    void add(User user) throws SQLException;

    void remove(UserIM user) throws SQLException;

    List<UserIM> query(SQLSpecification specification) throws SQLException;
}
