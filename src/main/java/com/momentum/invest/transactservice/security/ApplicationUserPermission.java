package com.momentum.invest.transactservice.security;

import lombok.Getter;

@Getter
public enum ApplicationUserPermission {

    INVESTOR_READ("investor:read"),
    INVESTOR_WRITE("investor:write"),
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }
}
