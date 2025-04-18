package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements Commands {
    SHOW_CURRENT_MENU("^show\\s+current\\s+menu$"),
    SHOW_DETAIL("^show\\s+detail\\s+(?<country_name>.+)$"),
    TILE_OWNER("^tile\\s+owner\\s+(?<index>-?\\d+)$"),
    TILE_NEIGHBORS("^tile\\s+neighbors\\s+(?<index>-?\\d+)$"),
    TILE_SEA_NEIGHBORS("^tile\\s+sea\\s+neighbors\\s+(?<index>-?\\d+)$"),
    SHOW_WEATHER("^show\\s+weather\\s+(?<tile_index>-?\\d+)$"),
    SHOW_TERRAIN("^show\\s+terrain\\s+(?<tile_index>-?\\d+)$"),
    SHOW_BATTALION("^show\\s+battalions\\s+(?<tile_index>-?\\d+)$"),
    SHOW_FACTORY("^show\\s+factories\\s+(?<tile_index>-?\\d+)$"),
    SET_TERRAIN("^set\\s+terrain\\s+(?<tile_index>-?\\d+)\\s+(?<terrain_name>\\S+)$"),
    ADD_BATTALION("^add\\s+battalion\\s+(?<tile_index>-\\d+)\\s+(?<battalion_type>\\S+)\\s+(?<name>\\S+)$"),
    MOVE_BATTALION(
            "^move\\s+battalion\\s+(?<tile_index>.+?)\\s+(?<battalion_name>\\S+?)\\s+(?<destination_tile_index>-?\\d+)$"
    ),//TODO : mobham
    UPGRADE_BATTALION("^upgrade\\s+battalion\\s+(?<tile_index>-?\\d+)\\s+(?<battalion_name>\\S+)$"),
    CREATE_FACTION("^create\\s+faction\\s+(?<name>\\S+)$"),
    JOIN_FACTION("^join\\s+faction\\s+(?<faction_name>\\S+)$"),
    LEAVE_FACTION("^leave\\s+faction\\s+(?<faction_name>\\S+)$"),
    BUILD_FACTORY("^build\\s+factory\\s+(?<tile_index>-?\\d+)\\s+(?<factory_type>.+?)\\s+(?<name>\\S+)$"),
    RUN_FACTORY("^run\\s+factory\\s+(?<tile_index>-?\\d+)\\s+(?<name>\\S+?)\\s+(?<man_power_count>-?\\d+)$"),
    ATTACK("^attack\\s+(?<our_tile_index>-?\\d+)\\s+(?<enemy_tile_index>-?\\d+)\\s+(?<battalion_type>.+)$"),
    CIVIL_WAR("^start\\s+civil\\s+war\\s+(?<tile1>-?\\d+)\\s+(?<tile2>-?\\d+)\\s+(?<battalion_type>.+)$"),
    PUPPET("^puppet\\s+(?<country_name>.+)$"),
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
