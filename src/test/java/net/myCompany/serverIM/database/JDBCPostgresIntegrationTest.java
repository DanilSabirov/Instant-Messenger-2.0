package net.myCompany.serverIM.database;


import jdk.nashorn.internal.ir.annotations.Ignore;
import net.myCompany.serverIM.database.dialog.DialogImpl;
import net.myCompany.serverIM.database.dialog.GroupDialog;
import net.myCompany.serverIM.database.dialog.GroupDialogImpl;
import net.myCompany.serverIM.database.message.Message;
import net.myCompany.serverIM.database.message.MessageImpl;
import net.myCompany.serverIM.database.message.ReceivedMessage;
import net.myCompany.serverIM.database.user.UserIM;
import net.myCompany.serverIM.database.user.UserImpl;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class JDBCPostgresIntegrationTest {
    static JDBCPostgres jdbc = JDBCPostgres.getInstance();

    @BeforeAll
    public static void setUp() throws IOException {
        jdbc.connect(new File("src/test/resources/config.properties"));
    }

    @AfterAll
    public static void tearDown() {
        jdbc.closeConnection();
    }

    @BeforeEach
    void clearDatabase() {
        try {
            jdbc.runSQLScriptWithoutResult(new File("src/test/resources/clear.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addUserAndGetIt() {
        String name = "Вася";
        String email = "ivanov@mail.ru";
        String login = "ivanov";
        String password = "12345678";

        addUser(name, email, login, password.toCharArray());
        UserIM user = getUser(name);
        Assertions.assertTrue(user.getId() >= 0 && user.equals(new UserIM(user.getId(), name, email)));
    }

    void addUser(String name, String email, String login, char[] password) {
        jdbc.addUser(new UserImpl(name, email), new AuthenticationData(login, password));
    }

    UserIM getUser(String name) {
        return jdbc.getUser(name);
    }

    @Test
    void addUserAndRemoveIt() {
        String name = "Вася";
        String email = "ivanov@mail.ru";
        String login = "ivanov";
        String password = "12345678";

        addUser(name, email, login, password.toCharArray());
        UserIM userIM = getUser(name);
        jdbc.removeUser(name);
        Assertions.assertTrue(isRemoved("users", "userid", userIM.getId()));
    }

    boolean isRemoved(String table, String nameId, int id) {
        String script = "SELECT isremoved FROM " + table + " WHERE " + nameId + " = " + id + ";";
        Boolean val = null;
        try {
            ResultSet resultSet = jdbc.runSQLScript(script);
            resultSet.next();
            val = resultSet.getBoolean("isremoved");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    @Test
    void createDialogAndGetIt() {
        String dialogName = "Dialog";

        String nameFirstUser = "First";
        String emailFirstUser = "first@mail.ru";
        String loginFirstUser = "firstt";
        char[] passwdFirstUser = "12345678".toCharArray();

        String nameSecondUser = "Second";
        String emailSecondUser = "Second@mail.ru";
        String loginSecondUser = "Second";
        char[] passwdSecondUser = "12345678".toCharArray();

        addUser(nameFirstUser, emailFirstUser, loginFirstUser, passwdFirstUser);
        addUser(nameSecondUser, emailSecondUser, loginSecondUser, passwdSecondUser);

        List<Integer> usersid = Stream.of(getUser(nameFirstUser).getId(), getUser(nameSecondUser).getId()).collect(Collectors.toList());

        createDialog(dialogName, usersid.stream().collect(Collectors.toSet()));

        int dialogId = jdbc.getUser(nameFirstUser).getDialogs().get(0);
        String textFirst = "Hello 123";
        String textSecond = "Привет 123";

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ReceivedMessage messageFirst = new ReceivedMessage(usersid.get(0), dialogId, textFirst, null);
        ReceivedMessage messageSecond = new ReceivedMessage(usersid.get(1), dialogId, textSecond, null);

        jdbc.addMessage(messageFirst);
        jdbc.addMessage(messageSecond);

        GroupDialog dialog = jdbc.getDialog(dialogId);


        Assertions.assertTrue(dialog.equals(new GroupDialogImpl(dialogId, dialogName, Stream.of(messageFirst, messageSecond).collect(Collectors.toList()), usersid.stream().collect(Collectors.toSet()))));
    }

    void createDialog(String name, Set<Integer> usersid) {
        jdbc.createDialog(new DialogImpl(name, usersid));
    }

    @Ignore
    @Test
    void addNewUserToDialog() {
    }

    @Ignore
    @Test
    void addMessage() {
    }

    @Ignore
    @Test
    void removeDialog() {
    }

    @Ignore
    @Test
    void getDialog() {
    }

    @Ignore
    @Test
    void searchAuthenticationData() {
    }

    @Ignore
    @Test
    void searchUsers() {
    }

    @Ignore
    @Test
    void getSequenceUserId() {
    }

    @Ignore
    @Test
    void getSequenceDialogId() {
    }
}