package net.myCompany.database.dialog;

import net.myCompany.database.ConnectionManager;
import net.myCompany.database.message.ReceivedMessage;
import net.myCompany.database.repositories.MessageRepositoryImpl;
import net.myCompany.database.repositories.UserRepositoryImpl;
import net.myCompany.database.repositories.specifications.GetMessagesByDialogId;
import net.myCompany.database.repositories.specifications.GetUsersByDialogId;
import net.myCompany.database.user.LazyUserIM;
import net.myCompany.database.user.UserIM;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class LazyDialog extends DialogImpl {

    public LazyDialog(long id, String name) {
        super(id, name);
        messages = null;
        users = null;
    }

    @Override
    public Set<UserIM> getUsers() {
        if (users == null) {
            Connection connection = null;
            try {
                connection = ConnectionManager.getConnection();
                List<UserIM> tmpUser = new UserRepositoryImpl(connection).query(new GetUsersByDialogId(getId()));
                for (int i = 0; i < tmpUser.size(); i++) {
                    UserIM u = tmpUser.get(i);
                    tmpUser.set(i, new LazyUserIM(u.getId(), u.getName(), u.getEmail()));
                }
                users = new TreeSet<>(tmpUser);
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return super.getUsers();
    }

    @Override
    public List<ReceivedMessage> getMessages() {
        if (messages == null) {
            Connection connection = null;
            try {
                connection = ConnectionManager.getConnection();
                messages = new MessageRepositoryImpl(connection).query(new GetMessagesByDialogId(getId()));
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return super.getMessages();
    }

    @Override
    public List<ReceivedMessage> getMessagesAfter(ZonedDateTime dateTime) {
        List<ReceivedMessage> res = new ArrayList<>();
        messages = getMessages();
        for (ReceivedMessage ms: messages) {
            if (ms.getDateReceipt().compareTo(dateTime) > 0) {
                res.add(ms);
            }
            else {
                break;
            }
        }
        return res;
    }
}
