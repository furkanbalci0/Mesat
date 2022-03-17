package com.furkanbalci.mesat.database.structure.structures;

import com.furkanbalci.mesat.database.Database;
import com.furkanbalci.mesat.database.structure.DatabaseStructure;
import com.furkanbalci.mesat.models.user.User;

public final class UserStructure extends DatabaseStructure<User> {

    public UserStructure(Database database, String table) {
        super(database, table);
    }

    @Override
    public void insert(User user) {
        super.database.executeQuery(user.toInsertQuery());
    }

    @Override
    public void update(User user) {
        super.database.executeQuery(user.toUpdateQuery());
    }

    @Override
    public void delete(User user) {
        super.database.executeQuery(user.toDeleteQuery());
    }
}
