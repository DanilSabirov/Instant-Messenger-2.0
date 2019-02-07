package net.myCompany.database.repositories.specifications;

import net.myCompany.database.AuthenticationData;

public class GetUserByEmailPassword implements SQLSpecification {
    AuthenticationData authenticationData;

    public GetUserByEmailPassword(AuthenticationData data) {
        authenticationData = data;
    }

    @Override
    public String toSQLClauses() {
        return "SELECT users.userid, username, email " +
                "FROM users, (SELECT userid FROM authentication_data " +
                "              WHERE login = '" + authenticationData.getLogin() + "' AND passwd = '" + String.valueOf(authenticationData.getPassword()) + "' ) as data " +
                "WHERE users.userid = data.userid;";
    }
}
