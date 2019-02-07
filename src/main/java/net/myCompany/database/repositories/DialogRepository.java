package net.myCompany.database.repositories;

import net.myCompany.database.dialog.Dialog;
import net.myCompany.database.repositories.specifications.SQLSpecification;

import java.sql.SQLException;
import java.util.List;

public interface DialogRepository {
    void add(Dialog dialog) throws SQLException;

    void remove(Dialog dialog);

    List<Dialog> query(SQLSpecification specification);
}
