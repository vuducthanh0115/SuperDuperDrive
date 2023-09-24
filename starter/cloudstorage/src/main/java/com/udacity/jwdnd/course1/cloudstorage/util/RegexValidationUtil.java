package com.udacity.jwdnd.course1.cloudstorage.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexValidationUtil {
    private static final String REGEX_PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*]).{8,}$";

    private RegexValidationUtil(){}

    public static boolean isValidPassword( String password) {
        if (!StringUtils.hasText(password)) {
            return false;
        }

        Pattern pattern = Pattern.compile(REGEX_PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password.trim());
        return matcher.matches();
    }
}
