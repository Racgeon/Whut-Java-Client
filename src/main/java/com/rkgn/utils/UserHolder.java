package com.rkgn.utils;

import com.rkgn.entity.User;

public class UserHolder {
    private static final ThreadLocal<User> tl = new ThreadLocal<>();

    public static User getUser() {
        return tl.get();
    }

    public static void setUser(User user) {
        tl.set(user);
    }

    public static void removeUser() {
        tl.remove();
    }
}
