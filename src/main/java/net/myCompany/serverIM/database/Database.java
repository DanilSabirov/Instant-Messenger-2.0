package net.myCompany.serverIM.database;

import net.myCompany.serverIM.database.dialog.Dialog;
import net.myCompany.serverIM.database.dialog.GroupDialog;
import net.myCompany.serverIM.database.message.Message;
import net.myCompany.serverIM.database.user.User;
import net.myCompany.serverIM.database.user.UserIM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Database {
    boolean connect(File configuration) throws FileNotFoundException, IOException;

    void closeConnection();

    void addUser(User user, AuthenticationData authenticationData);

    void removeUser(String name);

    UserIM getUser(String name);

    UserIM getUser(int id);

    void createDialog(Dialog dialog);

    void addNewUserToDialog(int dialogId, int userId);

    void addMessage(Message message);

    void removeDialog(int id);

    GroupDialog getDialog(int id);

    int searchAuthenticationData(AuthenticationData authenticationData);

    List<User> searchUsers(String namePrefix);
}
