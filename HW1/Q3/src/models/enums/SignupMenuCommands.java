package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignupMenuCommands implements Commands{
    SHOW_CURRENT_MENU("show\\s+current\\s+menu"),
    EXIT("exit"),
    REGISTER("^register\\s+-username\\s+(?<username>\\S+?)\\s+-password\\s+(?<password>.+?)\\s+-email\\s+(?<email>\\S+?)$"),
    USERNAME("^[a-zA-Z\\d][a-zA-Z\\d_]*$"),
    PASSWORD_LENGTH(".{8,20}"),
    PASSWORD_WHITESPACE("\\S{8,20}"),
    PASSWORD_STARTS("^[a-zA-Z].*"),
    PASSWORD_SPECIAL_LETTERS("(?=.*[%@#$^&!]).+"),
    EMAIL("^(?!.*[.].*[.].*@)[a-zA-Z.\\d]+@[\\d\\w]+\\.com$"),
    LOGIN("login"),

    ;


    private final String regex;

    SignupMenuCommands(String regex) {
        this.regex = regex;
    }


    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }
}
