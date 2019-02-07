package net.myCompany.database.repositories.specifications;

public class GetUserByEmail implements SQLSpecification {
    private String email;

    public GetUserByEmail(String email) {
        this.email = email;
    }

    @Override
    public String toSQLClauses() {
        return "SELECT userid, username, email FROM users WHERE email = '" + email + "';";
    }
}
