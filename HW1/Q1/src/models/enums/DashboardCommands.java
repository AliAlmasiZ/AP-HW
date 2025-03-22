package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our dashboard and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum DashboardCommands implements Command {
    CREATE_GROUP("^create-group\\s+-n\\s+(?<name>[\\da-zA-Z!@#$%\\^&*][\\da-zA-Z!@#$%\\^&* ]*[\\da-zA-Z!@#$%\\^&*])\\s+" +
            "-t\\s+(?<type>\\S+)$"),
    NAME("[\\da-zA-Z!@#$%\\^&* ]{4,30}"),
    TYPE("^(Home|Trip|Zan-o-Bache|Other)$"),

    SHOW_MY_GROUPS("^show my groups$"),

    ADD_USER_TO_GROUP("^add-user\\s+-u\\s+(?<username>\\S+)\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<group-id>\\d+)$"),
    EMAIL("^[A-Za-z][\\w.-]{3,9}@[a-z](?![a-z]*[-.][a-z]*[-.][a-z]*[a-z]\\.)[a-z.\\-]{1,5}[a-z]\\.(org|com|net|edu)$"),

    ADD_EXPENSE("^add-expense\\s+-g\\s+(?<group-id>\\d+)\\s+-s\\s+(?<equality>(equally|unequally))\\s+" +
            "-t\\s+(?<total-expense>\\S+)\\s+-n\\s+(?<number-of-users>\\d+)$"),

    SHOW_BALANCE("^show balance\\s+-u\\s+(?<username>\\S+)$"),

    SETTLE_UP("^settle-up\\s+-u\\s+(?<username>\\S+)\\s+-m\\s+(?<input-money>\\S+)"),
    MONEY("^\\d+$"),

    GOTO_PROFILE_MENU("^go to profile menu$"),
    LOGOUT("^logout$");

    String pattern;

    DashboardCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) {
            return matcher;
        } else {
            return null;
        }
    }
}
