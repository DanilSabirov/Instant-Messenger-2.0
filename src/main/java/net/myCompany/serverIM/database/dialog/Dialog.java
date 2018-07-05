package net.myCompany.serverIM.database.dialog;

import java.util.Set;

public interface Dialog {
    String getName();

    Set<Integer> getUsersId();
}
