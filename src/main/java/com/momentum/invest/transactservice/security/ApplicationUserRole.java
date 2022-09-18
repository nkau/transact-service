package com.momentum.invest.transactservice.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.momentum.invest.transactservice.security.ApplicationUserPermission.*;

@Getter
public enum ApplicationUserRole {

    INVESTOR(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(INVESTOR_READ,INVESTOR_WRITE,PRODUCT_READ,PRODUCT_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        var permissions = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).
                collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }
}
