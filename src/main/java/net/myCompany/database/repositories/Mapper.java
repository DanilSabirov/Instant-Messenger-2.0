package net.myCompany.database.repositories;

import java.sql.SQLException;

public interface Mapper<From, To> {
    To map(From from) throws SQLException;
}
