package net.myCompany.serverIM.database.message;

import java.time.ZonedDateTime;

public class ReceivedMessage extends MessageImpl {
    private ZonedDateTime dateReceipt;

    private boolean isRead = false;

    public ReceivedMessage(int autorId, long dialogId, String text, ZonedDateTime dateReceipt) {
        super(autorId, dialogId, text);
        this.dateReceipt = dateReceipt;
    }

    public ZonedDateTime getDateReceipt() {
        return dateReceipt;
    }

    public void setIsReadTrue() {
        isRead = true;
    }
}
