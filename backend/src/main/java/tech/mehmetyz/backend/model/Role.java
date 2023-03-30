package tech.mehmetyz.backend.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class Role {
    private final String name;
    private final List<GrantedAuthority> authorities;

    public Role(String name, List<GrantedAuthority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
