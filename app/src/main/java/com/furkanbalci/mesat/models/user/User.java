package com.furkanbalci.mesat.models.user;

import androidx.annotation.NonNull;

import com.furkanbalci.mesat.database.object.DatabaseObject;

import java.util.Objects;

/**
 * User class.
 */
public final class User implements DatabaseObject {

    private final long cachingDate;
    private final int id;
    private final String name;
    private final String surname;

    /**
     * User creator.
     *
     * @param id      Id.
     * @param name    Name.
     * @param surname Surname.
     */
    public User(int id, @NonNull String name, @NonNull String surname) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.surname = Objects.requireNonNull(surname);
        this.cachingDate = System.currentTimeMillis();
    }

    /**
     * Gets caching date.
     *
     * @return Gets caching date.
     */
    public long getCachingDate() {
        return this.cachingDate;
    }

    /**
     * Gets uuid of user.
     *
     * @return Id.
     */
    public int getID() {
        return this.id;
    }

    /**
     * Gets name of user.
     *
     * @return User name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets surname of user.
     *
     * @return User surname.
     */
    @NonNull
    public String getSurname() {
        return this.surname;
    }

    @Override
    public String toInsertQuery() {
        return "INSERT INTO"; //TODO:YAPILACAK
    }

    @Override
    public String toUpdateQuery() {
        return "UPDATE"; //TODO:YAPILACAK
    }

    @Override
    public String toDeleteQuery() {
        return "DELETE"; //TODO:YAPILACAK
    }
}
