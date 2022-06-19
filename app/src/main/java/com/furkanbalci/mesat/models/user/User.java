package com.furkanbalci.mesat.models.user;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * User class.
 */
public final class User {

    private long cachingDate;
    private final String id;
    private final String name;
    private final String surname;
    private String mail;
    private final String password;
    private String city;
    private long credit;
    private final String phone;
    private String profile_photo_id = "1";

    public User(String id, String name, String surname, String mail, String password, String city, String phone) {
        this(id, name, surname, mail, password, city, phone, 0);
    }

    public User(String id, String name, String surname, String mail, String password, String city, String phone, long credit) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
        this.city = city;
        this.phone = phone;
        this.credit = credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public long getCredit() {
        return this.credit;
    }

    public long getCachingDate() {
        return cachingDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfile_photo_id() {
        return profile_photo_id;
    }

    public void setProfile_photo_id(String profile_photo_id) {
        this.profile_photo_id = profile_photo_id;
    }
}
