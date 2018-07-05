package net.myCompany.serverIM.database.message;


import java.time.ZonedDateTime;
import java.util.Objects;

public class MessageImpl implements Message {
    private int authorId;

    private long dialogId;

    private String text;

    public MessageImpl(int autorId, long dialogId, String text) {
        this.authorId = autorId;
        this.text = text;
        this.dialogId = dialogId;
    }

    @Override
    public int getAuthorId() {
        return authorId;
    }

    @Override
    public long getDialogId() {
        return dialogId;
    }

    @Override
    public String getText() {
        return text;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageImpl message = (MessageImpl) o;
        return authorId == message.authorId &&
                dialogId == message.dialogId &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(authorId, dialogId, text);
    }

    @Override
    public String toString() {
        return "MessageImpl{" +
                "authorId=" + authorId +
                ", dialogId=" + dialogId +
                ", text='" + text  + '\n' +
                '}';
    }
}
