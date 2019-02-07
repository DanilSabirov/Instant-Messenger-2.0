package net.myCompany.database.repositories.specifications;

public class GetUserByName implements SQLSpecification {
    private String name;

    public GetUserByName(String name) {
        this.name = name;
    }

    @Override
    public String toSQLClauses() {
        return "SELECT userid, username, email FROM users WHERE name = '" + name + "';";
    }
}
