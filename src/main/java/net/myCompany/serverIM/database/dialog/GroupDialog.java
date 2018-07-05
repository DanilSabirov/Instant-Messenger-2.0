package net.myCompany.serverIM.database.dialog;


import net.myCompany.serverIM.database.message.Message;
import net.myCompany.serverIM.database.message.ReceivedMessage;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface GroupDialog extends Dialog{
    long getId();

    void addMessage(ReceivedMessage message);

    void addUser(int userId);

    List<ReceivedMessage> getMessages();

    List<ReceivedMessage> getMessagesAfter(ZonedDateTime dateTime);
}