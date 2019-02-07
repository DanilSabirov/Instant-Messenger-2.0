package net.myCompany.database.repositories;

public interface TransactionSupport {
    void commit();

    void rollback();
}
