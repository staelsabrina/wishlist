package br.com.wishlist.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputSanitizer {

    public static String sanitize(String value){
        String regex = "[^a-zA-Z0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);

        String result = matcher.replaceAll("");

        return result;
    }
}
