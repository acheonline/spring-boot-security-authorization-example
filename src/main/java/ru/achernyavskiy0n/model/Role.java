package ru.achernyavskiy0n.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * 09.08.2020
 *
 * @author a.chernyavskiy0n
 */
public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    SUPERADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
