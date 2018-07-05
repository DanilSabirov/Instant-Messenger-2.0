package net.myCompany.serverIM.database.user;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserIM extends UserImpl {
    private int id;

    private List<Integer> dialogsId;

    public UserIM(int id, @NotNull String name, @NotNull String email) {
        super(name, email);
        this.id = id;
        dialogsId = new ArrayList<>();
    }

    public UserIM(int id, @NotNull String name, @NotNull String email, @NotNull List<Integer> dialogsId) {
        super(name, email);
        this.id = id;
        this.dialogsId = dialogsId;
    }

    public List<Integer> getDialogs() {
        return dialogsId;
    }

    public void addNewDialog(int dialogId) {
        dialogsId.add(dialogId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserIM)) return false;
        UserIM userIM = (UserIM) o;
        return id == userIM.id &&
                Objects.equals(dialogsId, userIM.dialogsId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dialogsId);
    }

    @Override
    public String toString() {
        return "UserIM{" +
                "id=" + id +
                "name= " + name +
                "email= " + email +
                ", dialogsId=" + dialogsId +
                "}";
    }
}
