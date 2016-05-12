package br.com.managersystems.guardasaude.util;


import android.util.Patterns;

import org.jsoup.Jsoup;

/**
 * Utility class handles and/or returns String objects.
 * Currently handles:
 * - String to namecase
 * - String to formalcase
 * - Html Strings to plain text
 * - Email validation <b>TODO validate emails based on...</b>
 * - Password validation
 * - Checking if a string is found in an array
 *
 * Authors:
 * @author Jan Somers
 * @author Thanee Stevens
 */
public class StringUtils {

    public static String anyCaseToNameCase(String input) {
        input = input.toLowerCase();
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static String anyCaseToFormalCase(String input) {
        input = input.toLowerCase();
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public static boolean isValidEmail(String email) {
        if (email.isEmpty()) return false;
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{5,}$");
    }

    public static boolean stringInArray(String string, String[] stringArray) {
        boolean inArray = false;
        for (String stringFromArray : stringArray) {
            if (!inArray) {
                inArray = string.equals(stringFromArray);
            }
        }
        return inArray;
    }
}
