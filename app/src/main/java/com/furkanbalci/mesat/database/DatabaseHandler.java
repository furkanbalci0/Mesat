package com.furkanbalci.mesat.database;

public final class DatabaseHandler {

    private static final Database database = new Database(
            "185.255.92.92",
            3306,
            "oyunlar",
            "root",
            "25@!N9jDJQkf-r8");

    public static Database getDatabase() {
        return database;
    }
}



