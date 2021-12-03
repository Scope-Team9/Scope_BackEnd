package com.studycollaboproject.scope.util;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String regex = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";

    public static boolean emailValidator(String email) {
        return Pattern.matches(regex, email);
    }
}
