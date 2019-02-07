package net.myCompany.database.repositories.specifications;

public class GetUserById implements SQLSpecification {
    private int userid;

    public GetUserById(int userid) {
        this.userid = userid;
    }

    @Override
    public String toSQLClauses() {
        return "SELECT userid, username, email FROM users WHERE userid = " + userid + ";";
    }
}
