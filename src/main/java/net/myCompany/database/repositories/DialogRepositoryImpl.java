package net.myCompany.database.repositories;

import net.myCompany.database.dialog.Dialog;
import net.myCompany.database.repositories.specifications.SQLSpecification;
import net.myCompany.database.user.UserIM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DialogRepositoryImpl implements DialogRepository {
    private Connection db;

    public DialogRepositoryImpl(Connection db) {
        this.db = db;
    }

    @Override
    public void add(Dialog dialog) throws SQLException {
        int id = reserveId();
        String sql = "INSERT INTO dialogs(dialogid, name) " +
                "VALUE(?, ?);";
        PreparedStatement ps = db.prepareStatement(sql);

        ps.setInt(1, id);
        ps.setString(2, dialog.getName());

        ps.execute();
        ps.close();

        StringBuilder sqlBuilder = new StringBuilder();

        for (int i = 0; i < dialog.getUsers().size(); i++) {
            sqlBuilder.append("INSERT INTO user_dialog(userid, dialogid) " +
                    "VALUE(?, ?);\n");
        }

        ps = db.prepareStatement(sqlBuilder.toString());

        int count = 0;
        for (UserIM user: dialog.getUsers()) {
            ps.setInt(count*2+1, user.getId());
            ps.setInt(count*2+2, id);
        }

        ps.execute();
        ps.close();
    }

    private int reserveId() throws SQLException {
        String sql = "SELECT nextval('dialog_dialogid_seq') as val";

        ResultSet rs = db.createStatement().executeQuery(sql);

        rs.next();
        return rs.getInt(1);
    }

    @Override
    public void remove(Dialog dialog) {

    }

    @Override
    public List<Dialog> query(SQLSpecification specification) {
        return null;
    }
}
