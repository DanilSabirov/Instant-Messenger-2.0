package net.myCompany.database.user;

import net.myCompany.database.ConnectionManager;
import net.myCompany.database.dialog.Dialog;
import net.myCompany.database.repositories.DialogRepositoryImpl;
import net.myCompany.database.repositories.UserRepository;
import net.myCompany.database.repositories.UserRepositoryImpl;
import net.myCompany.database.repositories.specifications.GetDialogByUserid;
import org.jetbrains.annotations.NotNull;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LazyUserIM extends UserIM {

    public LazyUserIM(int id, @NotNull String name, @NotNull String email) {
        super(id, name, email);
        dialogs = null;
    }

    @Override
    public List<Dialog> getDialogs() {
        if (dialogs == null) {
            Connection connection = null;
            try {
                connection = ConnectionManager.getConnection();
                dialogs = new DialogRepositoryImpl(connection).query(new GetDialogByUserid());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.getDialogs();
    }
}
