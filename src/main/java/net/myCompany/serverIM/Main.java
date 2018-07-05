package net.myCompany.serverIM;


import net.myCompany.serverIM.database.Database;
import net.myCompany.serverIM.database.JDBCPostgres;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Database database = JDBCPostgres.getInstance();
        try {
            if (database.connect(new File("src/main/resources/config.properties"))) {
            //    database.addUser(new UserImpl(-1, "Вася", "Вася@mail.ru"), new AuthenticationData("loginn", "password".toCharArray()));
            //    database.removeUser("Вася");
            //    database.getUser("Вася");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
