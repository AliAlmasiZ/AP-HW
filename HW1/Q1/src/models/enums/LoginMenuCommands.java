package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our login menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum LoginMenuCommands implements Command{

    LOGIN_USER("^login\\s+-u\\s+(?<username>.+?)\\s+-p\\s+(?<password>.+?)$"),
    FORGET_PASSWORD("^forget-password\\s+-u\\s+(?<username>.+?)\\s+-e\\s+(?<email>.+?)$"),
    GOTO_SIGNUP_MENU("^go to signup menu$"),
    EXIT("^exit$");

    private final String pattern;

    LoginMenuCommands(String pattern) {
        this.pattern = pattern;
    }


    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if(matcher.matches()) {
            return matcher;
        } else {
            return null;
        }
    }
}