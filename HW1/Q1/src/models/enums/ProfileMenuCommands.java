package models.enums;
/*
Explanation:
- we have commands in our profile menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements Command{
    SHOW_USER_INFO("^show user info$"),
    CHANGE_CURRENCY("^change-currency\\s+-n\\s+(?<newCurrency>\\S+)$"),
    CHANGE_USERNAME("^change-username\\s+-n\\s+(?<newUsername>.+?)$"),
    USERNAME("^[a-zA-Z][\\w.-]{3,9}$"),
    CHANGE_PASSWORD("^change-password\\s+-o\\s+(?<oldPassword>\\S+)\\s+-n\\s+(?<newPassword>.+?)$"),
    PASSWORD("^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*\\d)[A-Za-z\\d!@#$%\\^&*]{6,12}$"),

    BACK("^back$"),
    EXIT("^exit$");;

    private final String pattern;

    ProfileMenuCommands(String pattern) {
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
