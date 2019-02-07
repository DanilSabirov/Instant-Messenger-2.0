package net.myCompany.database;

import java.util.Arrays;

public final class AuthenticationData {
    private String login;

    private char[] password;

    public AuthenticationData(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        net.myCompany.database.AuthenticationData that = (net.myCompany.database.AuthenticationData) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        return Arrays.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }
}
