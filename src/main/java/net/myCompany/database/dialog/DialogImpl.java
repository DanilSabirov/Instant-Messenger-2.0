package net.myCompany.database.dialog;

import net.myCompany.database.message.ReceivedMessage;
import net.myCompany.database.user.User;
import net.myCompany.database.user.UserIM;

import java.time.ZonedDateTime;
import java.util.*;

public class DialogImpl implements Dialog {
    protected long id;

    protected List<ReceivedMessage> messages;

    protected Set<UserIM> users;

    protected String name;

    public DialogImpl(long id) {
        messages = new ArrayList<>();
        users = new TreeSet<>();
        name = "";
    }

    public DialogImpl(long id, String name) {
        this.id = id;
        users = new TreeSet<>();
        messages = new ArrayList<>();
        this.name = name;
    }

    public DialogImpl(long id, String name, Set<UserIM> users) {
        this.id = id;
        this.users = users;
        messages = new ArrayList<>();
        this.name = name;
    }

    public DialogImpl(long id, String name, List<ReceivedMessage> messages, Set<UserIM> users) {
        this.id = id;
        this.messages = messages;
        this.users = users;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<UserIM> getUsers() {
        return users;
    }

    @Override
    public void addMessage(ReceivedMessage message) {
        messages.add(message);
    }

    @Override
    public void addUser(UserIM user) {
        users.add(user);
    }

    @Override
    public List<ReceivedMessage> getMessages() {
        return messages;
    }

    @Override
    public List<ReceivedMessage> getMessagesAfter(ZonedDateTime dateTime) {
        List<ReceivedMessage> res = new ArrayList<>();
        int lastPos = messages.size() - 1;
        for(int i = lastPos; i >= 0; i--) {
            ReceivedMessage m = messages.get(i);
            if (m.getDateReceipt().compareTo(dateTime) == 1){
                res.add(m);
            }
            else {
                break;
            }
        }
        Collections.reverse(res);
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DialogImpl)) return false;
        DialogImpl that = (DialogImpl) o;
        return id == that.id &&
                Objects.equals(messages, that.messages) &&
                Objects.equals(users, that.users) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, messages, users, name);
    }

    @Override
    public String toString() {
        return "DialogImpl{" +
                "id=" + id +
                ", messages=" + messages +
                ", users=" + users +
                ", name='" + name + '\'' +
                '}';
    }
}