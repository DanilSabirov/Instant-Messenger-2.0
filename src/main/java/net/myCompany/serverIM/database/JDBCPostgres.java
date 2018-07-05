package net.myCompany.serverIM.database;

import net.myCompany.serverIM.database.dialog.Dialog;
import net.myCompany.serverIM.database.dialog.GroupDialog;
import net.myCompany.serverIM.database.dialog.GroupDialogImpl;
import net.myCompany.serverIM.database.message.Message;
import net.myCompany.serverIM.database.message.MessageImpl;
import net.myCompany.serverIM.database.message.ReceivedMessage;
import net.myCompany.serverIM.database.user.User;
import net.myCompany.serverIM.database.user.UserIM;

import java.io.*;
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.*;

public class JDBCPostgres implements Database, SupportedSQL {
    private Connection connection = null;

    private Statement statement = null;

    private static JDBCPostgres instance = null;

    private JDBCPostgres() { }

    public static synchronized JDBCPostgres getInstance() {
        if (instance == null) {
            instance = new JDBCPostgres();
        }
        return instance;
    }

    @Override
    public boolean connect(File configuration) throws IOException{
        FileInputStream fis;
        Properties property = new Properties();
        String url = null;
        String username = null;
        String password = null;

        try {
            fis = new FileInputStream(configuration);
            property.load(fis);

            url = "jdbc:postgresql://" + property.getProperty("db.host") + ":" + property.getProperty("db.port") + "/" + property.getProperty("db.dbname");
            username = property.getProperty("db.login");
            password = property.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("Connect");
            return true;
        }
        return false;
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addUser(User user, AuthenticationData authenticationData) {
        try {
            final int userId = reserveId("users_userid_seq");
            addUserInfo(user, userId);
            addAuthenticationData(authenticationData, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addUserInfo(User user, int userId) throws SQLException {
        String addUserInfo = "INSERT INTO users (userid, username, email) " +
                "VALUES (" + userId + ", '" + user.getName() + "', '" + user.getEmail() + "');";
        runSQLScriptWithoutResult(addUserInfo);
    }

    private int reserveId(String nameSequence) throws SQLException {
        String reserveMessageId = "SELECT nextval('" + nameSequence + "') as val;";

        ResultSet resultSet = runSQLScript(reserveMessageId);

        resultSet.next();
        int val = resultSet.getInt("val");

        return val;
    }

    private int getUserId(User user) {
        String getUserId = "SELECT userid FROM users WHERE username = '" + user.getName() + "';";
        int result = -1;

        try {
            ResultSet resultSet = runSQLScript(getUserId);
            resultSet.next();
            result = resultSet.getInt("userid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void addAuthenticationData(AuthenticationData data, int userId) {
        String addAuthenticationData = "INSERT INTO authentication_data (login, passwd, userid) " +
                "VALUES ('" + data.getLogin() + "', '" + String.valueOf(data.getPassword()) + "', '" + userId + "');";
        try {
            runSQLScriptWithoutResult(addAuthenticationData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(String name) {
        String removeUser = "UPDATE users SET isremoved = true WHERE username = '" + name + "';";
        try {
            runSQLScriptWithoutResult(removeUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserIM getUser(String name) {
        String getUser = "SELECT username, userid, email FROM users WHERE username = '" + name + "';";
        UserIM user = null;
        try {
            ResultSet resultSet = runSQLScript(getUser);

            resultSet.next();
            String username = resultSet.getString("username");
            int userid = resultSet.getInt("userid");
            String email = resultSet.getString("email");
            user = new UserIM(userid, username, email, getUserDialogs(userid));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public UserIM getUser(int id) {
        String getUser = "SELECT username, userid, email FROM users WHERE userid = '" + id + "';";
        UserIM user = null;
        try {
            ResultSet resultSet = runSQLScript(getUser);

            resultSet.next();
            String username = resultSet.getString("username");
            int userid = resultSet.getInt("userid");
            String email = resultSet.getString("email");
            user = new UserIM(userid, username, email, getUserDialogs(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private List<Integer> getUserDialogs(int id) {
        String getUserDialogs = "SELECT dialogid FROM user_dialog WHERE userid = " + id + ";";
        List<Integer> dialogs = new ArrayList<>();

        try {
            ResultSet resultSet = runSQLScript(getUserDialogs);
            while (resultSet.next()) {
                dialogs.add(resultSet.getInt("dialogid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dialogs;
    }

    @Override
    public void createDialog(Dialog dialog) {
        try {
            final int dialogId = reserveId("dialog_dialogid_seq");

            addDialogInfo(dialog, dialogId);

            for (Integer userId: dialog.getUsersId()) {
                addNewUserToDialog(dialogId, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDialogInfo(Dialog dialog, int dialogId) throws SQLException {
        String addDialogInfo = "INSERT INTO dialogs(dialogid, name) " +
                "VALUES (" + dialogId + ", '" +  dialog.getName() + "');";
        runSQLScriptWithoutResult(addDialogInfo);
    }

    @Override
    public void addNewUserToDialog(int dialogId, int userId) {
        String addNewUserToDialog = "INSERT INTO user_dialog(dialogid, userid) " +
                "VALUES (" + dialogId + ", " + userId + ");";
        try {
            runSQLScriptWithoutResult(addNewUserToDialog);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMessage(Message message) {


        try {
            final int messageId = reserveId("message_messageid_seq");
            createMessage(message, messageId);
            addMessageToDialog(message.getDialogId(), messageId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createMessage(Message message, int messageId) throws SQLException {
        String createMessage = "INSERT INTO message(messageid, autorid, textmess)" +
                "VALUES (" + messageId + ", " + message.getAuthorId() + ", '" + message.getText() + "');";
        runSQLScriptWithoutResult(createMessage);
    }

    private void addMessageToDialog(long dialogId, int messageId) throws SQLException {
        String addMessageToDialog = "INSERT INTO dialog_message(dialogid, messageid)" +
                "VALUES (" + dialogId + ", " + messageId + ");";
        runSQLScriptWithoutResult(addMessageToDialog);
    }

    @Override
    public void removeDialog(int id) {
        String removeDialog = "UPDATE users SET isremoved = true WHERE dialogid = " + id + ";";
        try {
            runSQLScriptWithoutResult(removeDialog);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GroupDialog getDialog(int id) {
        GroupDialog dialog = null;

        try {
            String dialogName = getNameDialog(id);
            List<ReceivedMessage> messages = getMessages(id);
            Set<Integer> usersId = getUsersDialog(id);

            dialog = new GroupDialogImpl(id, dialogName, messages, usersId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

      return dialog;
    }

    private String getNameDialog(int dialogId) throws SQLException {
        String getDialogName = "SELECT name FROM dialogs WHERE dialogid = " + dialogId + ";";
        ResultSet resultSet = runSQLScript(getDialogName);

        resultSet.next();
        String dialogName = resultSet.getString("name");
        return dialogName;

    }

    private List<ReceivedMessage> getMessages(int dialogId) {
        String getMessages = "SELECT autorid, date, textmess FROM " +
                "(SELECT messageid FROM dialog_message WHERE  dialogid = " + dialogId + ") as curDialogMessage, message " +
                "WHERE curDialogMessage.messageid = message.messageid;";
        List<ReceivedMessage> messages = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = runSQLScript(getMessages);
            while (resultSet.next()) {
                int autorid = resultSet.getInt("autorid");
                System.out.println(resultSet.getString("date") + '\n' + ZonedDateTime.now());
                ZonedDateTime date = ZonedDateTime.parse(resultSet.getString("date"));
                String text = resultSet.getString("textmess");
                messages.add(new ReceivedMessage(autorid, dialogId, text, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private Set<Integer> getUsersDialog(int dialogId) throws SQLException {
        String getUsers = "SELECT userid FROM user_dialog WHERE dialogid = " + dialogId + ";";
        ResultSet resultSet = runSQLScript(getUsers);
        Set<Integer> users = new TreeSet<>();

        while (resultSet.next()) {
            users.add(resultSet.getInt("userid"));
        }
        return users;
    }

    @Override
    public int searchAuthenticationData(AuthenticationData authenticationData) {
        String authenticationQuery = "SELECT userid FROM authentication_data " +
                "WHERE login = '" + authenticationData.getLogin() + ", passwd = '" + authenticationData.getPassword() + "';";
        int userId = -1;
        try {
            ResultSet resultSet = runSQLScript(authenticationQuery);

            resultSet.next();
            userId = resultSet.getInt("userid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    @Override
    public List<User> searchUsers(String namePrefix) {
        String searchUsers = "SELECT userid, username, email FROM users WHERE username LIKE '" + namePrefix + "%';";
        return null;
    }

    @Override
    public ResultSet runSQLScript(File file) throws SQLException {
        return statement.executeQuery(readSQLScript(file));
    }

    @Override
    public  ResultSet runSQLScript(String script) throws SQLException {
        return statement.executeQuery(script);
    }

    @Override
    public void runSQLScriptWithoutResult(File file) throws SQLException {
        statement.execute(readSQLScript(file));
    }

    @Override
    public void runSQLScriptWithoutResult(String script) throws SQLException {
        statement.execute(script);
    }

    private String readSQLScript(File file) {
        StringBuffer script = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        reader.lines().forEach(x -> script.append(x));
        return script.toString();
    }
}
