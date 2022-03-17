package com.furkanbalci.mesat.models.user;

import java.util.HashMap;
import java.util.Map;

public final class UserHandler {

    private static final Map<Integer, User> users = new HashMap<>();

    public static Map<Integer, User> getUsers() {
        return users;
    }

}
