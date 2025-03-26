package models.enums;

import models.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements Commands {
    /* common commands */
    GO_TO("^go\\s+to\\s+-m\\s+(?<nameOfTheMenu>.+)$"),
    GO_BACK("^go back$"),
    EXIT("^exit$"),
    /* LoginMenu commands */
    CREATE_USER(
            "^create\\s+a\\s+user\\s+account\\s+" +
                    "-fn\\s+(?<firstName>.+?)\\s+" +
                    "^-ln\\s+(?<lastName>.+?)\\s+" +
                    "-p\\s+(?<password>.+?)\\s+" +
                    "-rp\\s+(?<reEnteredPassword>.+?)\\s+" +
                    "-e\\s+(?<email>.+?)$"
    ),
    NAME("[A-Z][a-z]+"), //length check in controller
    PASSWORD("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+"),
    EMAIL("^(?!\\S*[.]\\S*[.]\\S*@)[a-zA-Z\\d.]+@[a-z]+\\.com$"),
    CREATE_STORE(
            "^create\\s+a\\s+store\\s+account\\s+" +
                    "-b\\s+\"(?<brand>.+?)\"\\s+" +
                    "-p\\s+(?<password>.+?)\\s+" +
                    "-rp\\s+(?<reEnterPassword>.+?)\\s+" +
                    "-e\\s+(?<email>.+?)$"
    ),
    LOGIN_USER("^login\\s+as\\s+user\\s+-e\\s+(?<email>.+?)\\s+-p\\s+(?<password>.+?)$"),
    LOGIN_STORE("^login\\s+as\\s+store\\s+-e\\s+(?<email>.+?)\\s+-p\\s+(?<password>.+?)$"),
    LOGOUT("logout"),
    DELETE_ACOUNT("^delete\\s+account\\s+-p\\s+(?<password>.+?)\\s+-rp\\s+(?<reEnterPassword>.+?)$"),


    ;

    LoginMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    private final String pattern;

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        else return null;
    }
}
