package net.myCompany.database.repositories.specifications;

public class GetUsersByDialogId implements SQLSpecification {

    long dialogId;

    public GetUsersByDialogId(long dialogId) {
        this.dialogId = dialogId;
    }

    @Override
    public String toSQLClauses() {
        return "SELECT users.userid, users.username, users.email " +
                "FROM users, (SELECT userid FROM user_dialog WHERE dialogid = "+ dialogId+") as friends" +
                "WHERE users.userid = friends.userid;";
    }
}
