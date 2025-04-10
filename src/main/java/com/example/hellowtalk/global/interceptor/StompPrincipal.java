package com.example.hellowtalk.global.interceptor;


import com.example.hellowtalk.global.auth.AuthUser;

import java.security.Principal;
import java.util.Objects;

public class StompPrincipal implements Principal {

    private final String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        StompPrincipal that = (StompPrincipal) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "StompPrincipal{" +
                "name='" + name + '\'' +
                '}';
    }
}
