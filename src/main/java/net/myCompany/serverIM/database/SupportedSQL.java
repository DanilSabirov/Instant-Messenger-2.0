package net.myCompany.serverIM.database;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SupportedSQL {
    ResultSet runSQLScript(File file) throws SQLException;

    ResultSet runSQLScript(String script) throws SQLException;

    void runSQLScriptWithoutResult(File file) throws SQLException;

    void runSQLScriptWithoutResult(String script) throws SQLException;
}
