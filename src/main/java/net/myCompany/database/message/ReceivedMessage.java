package net.myCompany.database.message;

import net.myCompany.database.user.UserIM;

import java.time.ZonedDateTime;

public class ReceivedMessage extends MessageImpl {
    private ZonedDateTime dateReceipt;

    private boolean isRead = false;

    public ReceivedMessage(UserIM autor, String text, ZonedDateTime dateReceipt) {
        super(autor, text);
        this.dateReceipt = dateReceipt;
    }

    public ZonedDateTime getDateReceipt() {
        return dateReceipt;
    }

    public void setIsReadTrue() {
        isRead = true;
    }
}
