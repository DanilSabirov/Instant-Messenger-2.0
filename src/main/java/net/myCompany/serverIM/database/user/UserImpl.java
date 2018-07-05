package net.myCompany.serverIM.database.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserImpl implements User {
    protected String name;

    protected String email;

    public UserImpl(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserImpl)) return false;
        UserImpl user = (UserImpl) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
