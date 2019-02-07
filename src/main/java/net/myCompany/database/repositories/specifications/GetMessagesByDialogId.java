package net.myCompany.database.repositories.specifications;

public class GetMessagesByDialogId implements SQLSpecification {

    long dialogId;

    public GetMessagesByDialogId(long dialogId) {
        this.dialogId = dialogId;
    }

    @Override
    public String toSQLClauses() {
        return "SELECT ms.messageid, ms.autorid, ms.date, ms.textmess " +
                "FROM message as ms, (SELECT messageid FROM dialog_message WHERE dialogid = "+dialogId+") as dialog_ms " +
                "WHERE ms.messageid = dialog_ms.messageid;";
    }
}
