package com.matt.coffeeapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mattr on 2/16/2018.
 */

public class Validation {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }
public static boolean validateOrder(boolean tea, boolean coffee, boolean expresso) {

    if (tea || coffee || expresso) {
        return true;
    }
    return false;
    }

}
