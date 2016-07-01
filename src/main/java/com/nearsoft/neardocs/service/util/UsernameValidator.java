package com.nearsoft.neardocs.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN =
        "^[_A-Za-z\\+]+(\\.[_A-Za-z]+)*@"
            + "nearsoft\\.com$";

    public UsernameValidator() {
        pattern = Pattern.compile(USERNAME_PATTERN);
    }

    public boolean validate(final String username) {

        matcher = pattern.matcher(username);
        return matcher.matches();

    }
}
