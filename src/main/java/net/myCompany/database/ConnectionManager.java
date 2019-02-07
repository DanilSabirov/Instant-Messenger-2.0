package net.myCompany.database;

import net.myCompany.database.repositories.AuthenticationDataRepository;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection getConnection() throws NamingException, SQLException {
        InitialContext cxt = new InitialContext();
        DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");
        return ds.getConnection();
    }
}
