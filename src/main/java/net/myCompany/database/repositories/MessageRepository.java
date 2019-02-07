package net.myCompany.database.repositories;

import net.myCompany.database.dialog.Dialog;
import net.myCompany.database.message.Message;
import net.myCompany.database.message.ReceivedMessage;
import net.myCompany.database.repositories.specifications.SQLSpecification;

import java.sql.SQLException;
import java.util.List;

public interface MessageRepository {
    void add(Message message, Dialog dialog) throws SQLException;

    void add(List<Message> messages, Dialog dialog) throws SQLException;

    List<ReceivedMessage>  query(SQLSpecification specification);
}
