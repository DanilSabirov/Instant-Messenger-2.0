package net.myCompany.serverIM;

import net.myCompany.serverIM.database.dialog.GroupDialog;
import net.myCompany.serverIM.database.message.Message;
import net.myCompany.serverIM.database.user.User;

public interface Observable {
    void notifyOfMessage(Message message);

    void notifyOfDialog(GroupDialog dialog);

    User getUser();
}
