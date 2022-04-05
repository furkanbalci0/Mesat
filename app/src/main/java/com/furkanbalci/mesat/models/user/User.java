package com.furkanbalci.mesat.models.user;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * User class.
 */
public final class User {

    private final long cachingDate;

    @SerializedName("id")
    @Expose
    private final int id;

    @SerializedName("name")
    @Expose
    private final String name;

    @SerializedName("surname")
    @Expose
    private final String surname;

    @SerializedName("mail")
    @Expose
    private final String mail;

    @SerializedName("password")
    @Expose
    private final String password;

    @SerializedName("phone")
    @Expose
    private final String phone;

    @SerializedName("profile_photo_id")
    @Expose
    private String profile_photo_id = "1";

    /**
     * User creator.
     *
     * @param id      Id.
     * @param name    Name.
     * @param surname Surname.
     */
    public User(int id, @NonNull String name, @NonNull String surname, @NonNull String mail, @NonNull String phone, @NonNull String password) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.surname = Objects.requireNonNull(surname);
        this.mail = Objects.requireNonNull(mail);
        this.phone = Objects.requireNonNull(phone);
        this.password = Objects.requireNonNull(password);
        this.cachingDate = System.currentTimeMillis();
        this.profile_photo_id = "1";
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

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return this.mail;
    }

    public String getPassword() {
        return this.password;
    }

    public String getProfile_photo_id() {
        return this.profile_photo_id;
    }
}
