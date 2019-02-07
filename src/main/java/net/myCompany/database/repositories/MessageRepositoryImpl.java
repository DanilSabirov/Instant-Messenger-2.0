package net.myCompany.database.repositories;

import net.myCompany.database.dialog.Dialog;
import net.myCompany.database.message.Message;
import net.myCompany.database.message.ReceivedMessage;
import net.myCompany.database.repositories.specifications.SQLSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageRepositoryImpl implements MessageRepository {
    Connection db;

    public MessageRepositoryImpl(Connection db) {
        this.db = db;
    }

    @Override
    public void add(Message message, Dialog dialog) throws SQLException {
        long id = reserveId();
        String sql = "INSERT INTO message(messageid, autorid, textmess) " +
                "VALUE(?, ?, ?);";
        PreparedStatement ps = db.prepareStatement(sql);

        ps.setLong(1, id);
        ps.setInt(2, message.getAuthor().getId());
        ps.setString(3, message.getText());

        ps.execute();
        ps.close();

        sql = "INSERT INTO dialog_message(dialogid, messageid) " +
                "VALUE(?, ?);";
        ps = db.prepareStatement(sql);

        ps.setLong(1, dialog.getId());
        ps.setLong(2, id);

        ps.execute();
        ps.close();
    }

    @Override
    public void add(List<Message> messages, Dialog dialog) throws SQLException {
        ArrayList<Long> listId = reserveId(messages.size());
        StringBuilder sql = new StringBuilder();

        for (int i = 0; i < messages.size(); i++) {
            sql.append("INSERT INTO message(messageid, autorid, textmess) " +
                    "VALUE(?, ?, ?)\n");
        }

        PreparedStatement ps = db.prepareStatement(sql.toString());
        Iterator<Message> it = messages.iterator();

        int cnt = 0;
        while (it.hasNext()) {
            Message curMessage = it.next();

            ps.setLong((cnt*3)+1, listId.get(cnt));
            ps.setInt((cnt*3)+2, curMessage.getAuthor().getId());
            ps.setString((cnt*3)+3, curMessage.getText());

            cnt++;
        }

        ps.execute();
        ps.close();

        sql.delete(0, sql.length());

        for (int i = 0; i < messages.size(); i++) {
            sql.append("INSERT INTO dialog_message(dialogid, messageid) \" +\n" +
                    "                \"VALUE(?, ?)\n");
        }
        ps = db.prepareStatement(sql.toString());

        cnt = 0;
        it = messages.iterator();
        while (it.hasNext()) {
            Message curMessage = it.next();

            ps.setLong((cnt*3)+1, dialog.getId());
            ps.setLong((cnt*3)+2, listId.get(cnt));

            cnt++;
        }
    }

    @Override
    public List<ReceivedMessage> query(SQLSpecification specification) {
        return null;
    }

    private ArrayList<Long> reserveId(int count) throws SQLException {
        ArrayList<Long> list = new ArrayList<>(count);

        while (count-- > 0) {
            list.add(reserveId());
        }
        return list;
    }

    private long reserveId() throws SQLException {
        String sql = "SELECT nextval('message_messageid_seq') as val";

        ResultSet rs = db.createStatement().executeQuery(sql);

        rs.next();
        return rs.getLong(1);
    }
}
