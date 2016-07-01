package com.nearsoft.neardocs.security;

import com.nearsoft.neardocs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

/**
 * Created by arturo on 6/27/16.
 */
public class OpenIdUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public OpenIdUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String openIdIdentifier) throws UsernameNotFoundException {
        return null;
    }
}
