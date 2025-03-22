package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our sign-up menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum SignUpMenuCommands {
//    REGISTER_USER("^\\s*register\\s+-u\\s+(?<username>\\b[A-Za-z][A-Za-z\\d\\-_.]{3,9})\\s+" +
//            "-p\\s+(?<password>(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*\\d)[A-Za-z\\d!@#$%\\^&*]{6,12})\\s+" +
//            "-e\\s+(?<email>\\b[A-Za-z][A-Za-z\\d\\-_.]{3,9}@[a-z][a-z.\\-]{1,5}[a-z].(org|com|net|edu))\\s+" +
//            "-n\\s+(?<name>[a-zA-Z][a-zA-Z\\-]*[a-zA-Z])\\s*"),

    REGISTER("^register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+" +
            "-e\\s+(?<email>\\S+)\\s+-n\\s+(?<name>\\S+)$"),
    USERNAME("^[a-zA-Z][\\w.-]{3,9}$"),
    PASSWORD("^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*\\d)[A-Za-z\\d!@#$%\\^&*]{6,12}$"),
    EMAIL("^[A-Za-z][\\w.-]{3,9}@[a-z](?![a-z]*[-.][a-z]*[-.][a-z]*[a-z]\\.)[a-z.\\-]{1,5}[a-z]\\.(org|com|net|edu)$"),
    NAME("^[a-zA-Z][a-zA-Z\\-]*[a-zA-Z]$");


    private final String regex;

    SignUpMenuCommands(String regex) {
        this.regex = regex;
    }



    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.regex).matcher(input);
        if(matcher.matches()) {
            return matcher;
        } else {
            return null;
        }
    }

}
