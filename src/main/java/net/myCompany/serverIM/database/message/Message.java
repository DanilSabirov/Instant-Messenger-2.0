package net.myCompany.serverIM.database.message;

import java.time.ZonedDateTime;

public interface Message {
    int getAuthorId();

    long getDialogId();

    String getText();

}
