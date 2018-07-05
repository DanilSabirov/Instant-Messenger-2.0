package net.myCompany.serverIM.database.dialog;

import java.util.Objects;
import java.util.Set;

public class DialogImpl implements Dialog {
    protected String name;

    protected Set<Integer> usersId;

    public DialogImpl(Set<Integer> usersId) {
        name = "";
        this.usersId = usersId;
    }

    public DialogImpl(String name, Set<Integer> usersId) {
        this.name = name;
        this.usersId = usersId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<Integer> getUsersId() {
        return usersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DialogImpl dialog = (DialogImpl) o;
        return Objects.equals(name, dialog.name) &&
                Objects.equals(usersId, dialog.usersId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, usersId);
    }

    @Override
    public String toString() {
        return "DialogImpl{" +
                "name='" + name + '\'' +
                ", usersId=" + usersId +
                '}';
    }
}
