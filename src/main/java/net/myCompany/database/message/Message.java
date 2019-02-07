package net.myCompany.database.message;

import net.myCompany.database.dialog.Dialog;
import net.myCompany.database.user.UserIM;

public interface Message {
    UserIM getAuthor();

    String getText();

}
