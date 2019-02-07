package net.myCompany.database.message;


import net.myCompany.database.user.UserIM;

import java.util.Objects;

public class MessageImpl implements Message {
    private UserIM author;

    private String text;

    public MessageImpl(UserIM author, String text) {
        this.author = author;
        this.text = text;
    }

    @Override
    public UserIM getAuthor() {
        return author;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageImpl)) return false;
        MessageImpl message = (MessageImpl) o;
        return Objects.equals(getAuthor(), message.getAuthor()) &&
                Objects.equals(getText(), message.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor(), getText());
    }

    @Override
    public String toString() {
        return "MessageImpl{" +
                "author=" + author +
                ", text='" + text + '\'' +
                '}';
    }
}
