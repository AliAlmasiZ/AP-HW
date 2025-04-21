package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements Commands {
    SHOW_CURRENT_MENU("^show\\s+current\\s+menu$"),
    SWITCH("^switch\\s+player\\s+(?<username>\\S+)$"),
    SHOW_DETAIL("^show\\s+detail\\s+(?<countryName>.+)$"),
    EXIT("exit"),
    TILE_OWNER("^tile\\s+owner\\s+(?<index>-?\\d+)$"),
    TILE_NEIGHBORS("^tile\\s+neighbors\\s+(?<index>-?\\d+)$"),
    TILE_SEA_NEIGHBORS("^tile\\s+sea\\s+neighbors\\s+(?<index>-?\\d+)$"),
    SHOW_WEATHER("^show\\s+weather\\s+(?<tileIndex>-?\\d+)$"),
    SHOW_TERRAIN("^show\\s+terrain\\s+(?<tileIndex>-?\\d+)$"),
    SHOW_BATTALION("^show\\s+battalions\\s+(?<tileIndex>-?\\d+)$"),
    SHOW_FACTORY("^show\\s+factories\\s+(?<tileIndex>-?\\d+)$"),
    SET_TERRAIN("^set\\s+terrain\\s+(?<tileIndex>-?\\d+)\\s+(?<terrainName>\\S+)$"),
    SET_WEATHER("^set\\s+weather\\s+(?<tileIndex>-?\\d+)\\s+(?<weatherName>.+)$"),
    ADD_BATTALION("^add\\s+battalion\\s+(?<tileIndex>-?\\d+)\\s+(?<battalionType>\\S+)\\s+(?<name>\\S+)$"),
    MOVE_BATTALION(
            "^move\\s+battalion\\s+(?<tileIndex>-?\\d+?)\\s+(?<battalionName>\\S+?)\\s+(?<destinationTileIndex>-?\\d+)$"
    ),//TODO : mobham
    UPGRADE_BATTALION("^upgrade\\s+battalion\\s+(?<tileIndex>-?\\d+)\\s+(?<battalionName>\\S+)$"),
    CREATE_FACTION("^create\\s+faction\\s+(?<name>\\S+)$"),
    JOIN_FACTION("^join\\s+faction\\s+(?<factionName>\\S+)$"),
    LEAVE_FACTION("^leave\\s+faction\\s+(?<factionName>\\S+)$"),
    BUILD_FACTORY("^build\\s+factory\\s+(?<tileIndex>-?\\d+)\\s+(?<factoryType>.+?)\\s+(?<name>\\S+)$"),
    RUN_FACTORY("^run\\s+factory\\s+(?<tileIndex>-?\\d+)\\s+(?<name>\\S+?)\\s+(?<manPowerCount>-?\\d+)$"),
    ATTACK("^attack\\s+(?<ourTileIndex>-?\\d+)\\s+(?<enemyTileIndex>-?\\d+)\\s+(?<battalionType>.+)$"),
    CIVIL_WAR("^start\\s+civil\\s+war\\s+(?<tile1>-?\\d+)\\s+(?<tile2>-?\\d+)\\s+(?<battalionType>.+)$"),
    PUPPET("^puppet\\s+(?<countryName>.+)$"),
    ELECTION("^start\\s+election$"),
    SADAGHALLAH("^sadagha\\s+allah\\s+ol\\s+aliol\\s+azim$");


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
