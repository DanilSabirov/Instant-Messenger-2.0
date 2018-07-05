package net.myCompany.serverIM.database.dialog;

import net.myCompany.serverIM.database.message.Message;
import net.myCompany.serverIM.database.message.ReceivedMessage;

import java.time.ZonedDateTime;
import java.util.*;

public class GroupDialogImpl implements GroupDialog {
    private long id;

    private List<ReceivedMessage> messages;

    private Set<Integer> users;

    private String name;

    public GroupDialogImpl(int id) {
        messages = new ArrayList<>();
        users = new TreeSet<>();
        name = "";
    }

    public GroupDialogImpl(int id, String name, Set<Integer> usersId) {
        this.id = id;
        this.users = usersId;
        messages = new ArrayList<>();
        this.name = name;
    }

    public GroupDialogImpl(long id, String name, List<ReceivedMessage> messages, Set<Integer> users) {
        this.id = id;
        this.messages = messages;
        this.users = users;
        this.name = name;
    }

    public void setId(int id) {
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
    public Set<Integer> getUsersId() {
        return users;
    }

    @Override
    public void addMessage(ReceivedMessage message) {
        messages.add(message);
    }

    @Override
    public void addUser(int userId) {
        users.add(userId);
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
        if (!(o instanceof GroupDialogImpl)) return false;
        GroupDialogImpl that = (GroupDialogImpl) o;
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
        return "GroupDialogImpl{" +
                "id=" + id +
                ", messages=" + messages +
                ", users=" + users +
                ", name='" + name + '\'' +
                '}';
    }
}