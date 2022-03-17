package com.furkanbalci.mesat.database.object;

public interface DatabaseObject {

    String toInsertQuery();

    String toUpdateQuery();

    String toDeleteQuery();

}
