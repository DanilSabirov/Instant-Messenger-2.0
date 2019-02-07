package net.myCompany.database.dialog;


import net.myCompany.database.message.ReceivedMessage;
import net.myCompany.database.user.User;
import net.myCompany.database.user.UserIM;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public interface Dialog {
    String getName();

    Set<UserIM> getUsers();

    long getId();

    void addMessage(ReceivedMessage message);

    void addUser(UserIM user);

    List<ReceivedMessage> getMessages();

    List<ReceivedMessage> getMessagesAfter(ZonedDateTime dateTime);
}