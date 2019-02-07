package net.myCompany.database.user;


import net.myCompany.database.dialog.Dialog;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserIM extends UserImpl {
    protected int id;

    protected List<Dialog> dialogs;

    public UserIM(int id, @NotNull String name, @NotNull String email) {
        super(name, email);
        this.id = id;
        dialogs = new ArrayList<>();
    }

    public UserIM(int id, @NotNull String name, @NotNull String email, @NotNull List<Dialog> dialogs) {
        super(name, email);
        this.id = id;
        this.dialogs = dialogs;
    }

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public void addNewDialog(Dialog dialog) {
        dialogs.add(dialog);
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
        if (!(o instanceof net.myCompany.database.user.UserIM)) return false;
        net.myCompany.database.user.UserIM userIM = (net.myCompany.database.user.UserIM) o;
        return id == userIM.id &&
                Objects.equals(dialogs, userIM.dialogs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dialogs);
    }

    @Override
    public String toString() {
        return "UserIM{" +
                "id=" + id +
                "name= " + name +
                "email= " + email +
                ", dialogs=" + dialogs +
                "}";
    }
}
