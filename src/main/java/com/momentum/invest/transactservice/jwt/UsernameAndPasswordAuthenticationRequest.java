package com.momentum.invest.transactservice.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class UsernameAndPasswordAuthenticationRequest {

    private String username;
    private String password;
}
