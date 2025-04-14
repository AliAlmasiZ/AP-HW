package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements Commands {
    SHOW_CURRENT_MENU("^show\\s+current\\s+menu$"),
    SHOW_DETAIL("^show\\s+detail\\s+(?<country-name>.+)$"),
    TILE_OWNER("^tile\\s+owner\\s+(?<index>-?\\d+)$"),
    TILE_NEIGHBORS("^tile\\s+neighbors\\s+(?<index>-?\\d+)$"),
    TILE_SEA_NEIGHBORS("^tile\\s+sea\\s+neighbors\\s+(?<index>-?\\d+)$"),
    SHOW_WEATHER("^show\\s+weather\\s+(?<tile-index>-?\\d+)$"),
    SHOW_TERRAIN("^show\\s+terrain\\s+(?<tile-index>-?\\d+)$"),
    SHOW_BATTALION("^show\\s+battalions\\s+(?<tile-index>-?\\d+)$"),
    SHOW_FACTORY("^show\\s+factories\\s+(?<tile-index>-?\\d+)$"),
    SET_TERRAIN("^set\\s+terrain\\s+(?<tile-index>-?\\d+)\\s+(?<terrain-name>\\S+)$"),
    ADD_BATTALION("^add\\s+battalion\\s+(?<tile-index>-\\d+)\\s+(?<battalion-type>\\S+)\\s+(?<name>.+)$"),
    ///////////////
    CREATE_FACTION("^create\\s+faction\\s+(?<name>.+)$"),
    JOIN_FACTION("^join\\s+faction\\s+(?<faction-name>.+)$"),

    ;


    private final String regex;

    GameMenuCommands(String regex) {
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
