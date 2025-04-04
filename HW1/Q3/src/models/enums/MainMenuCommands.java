package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements Commands{

    SHOW_CURRENT_MENU("show current menu"),
    LOGOUT("logout"),
    LEADERBOARD("leaderboard"),
    PLAY("play(\\s+(?<user1>\\S+))?(\\s+(?<user2>\\S+))?(\\s+(?<user3>\\S+))?(\\s+(?<user4>\\S+))?")


    ;


    private final String regex;

    MainMenuCommands(String regex) {
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
