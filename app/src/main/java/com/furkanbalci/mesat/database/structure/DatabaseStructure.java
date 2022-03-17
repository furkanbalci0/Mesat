package com.furkanbalci.mesat.database.structure;

import com.furkanbalci.mesat.database.Database;
import com.furkanbalci.mesat.database.object.DatabaseObject;

public abstract class DatabaseStructure<T extends DatabaseObject> {

    protected final Database database;
    protected final String table;

    public DatabaseStructure(Database database, String table) {
        this.database = database;
        this.table = table;
    }

    public Database getDatabase() {
        return this.database;
    }

    public String getTable() {
        return this.table;
    }

    public abstract void insert(T t);

    public abstract void update(T t);

    public abstract void delete(T t);
}
