package com.example.aplikacja.appuser;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
    USER,
    STUDENT,
    ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
