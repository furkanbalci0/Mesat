package com.furkanbalci.mesat.models.user;

import androidx.annotation.NonNull;

import java.util.Objects;
import java.util.UUID;

/**
 * User class.
 */
public final class User {

    private final UUID uid;
    private String name;
    private String surname;

    /**
     * User creator.
     *
     * @param uid UUID.
     */
    public User(@NonNull UUID uid) {
        this.uid = Objects.requireNonNull(uid);
    }

    /**
     * Gets uuid of user.
     *
     * @return UUID.
     */
    @NonNull
    public UUID getUID() {
        return this.uid;
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
     * Sets name of user.
     *
     * @param name User name.
     */
    public void setName(@NonNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    /**
     * Gets surname of user.
     * @return User surname.
     */
    @NonNull
    public String getSurname() {
        return this.surname;
    }

    /**
     * Sets surname of user.
     * @param surname User surname.
     */
    public void setSurname(@NonNull String surname) {
        this.surname = Objects.requireNonNull(surname);
    }
}
