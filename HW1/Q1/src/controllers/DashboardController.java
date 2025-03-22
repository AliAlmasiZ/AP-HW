package controllers;

import models.App;
import models.Group;
import models.Result;
import models.enums.DashboardCommands;
import models.enums.GroupType;

import java.util.regex.Matcher;

/*
Explanation:
- This is a controller class for the dashboard Controller.
- This class will be used to implement functions that do dashboard operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

public class DashboardController {


    public Result createGroup(String input) {
        Matcher matcher = DashboardCommands.CREATE_GROUP.getMatcher(input);
        if (DashboardCommands.NAME.getMatcher(matcher.group("name")) == null) {
            return new Result(false, "group name format is invalid!");
        }
        if (DashboardCommands.TYPE.getMatcher(matcher.group("type")) == null) {
            return new Result(false, "group type is invalid!");
        }
        App.addGroup(new Group(stringToGroupType(matcher.group("type")), matcher.group("name"), App.getActiveUser()));
        return new Result(true, "group created successfully!");
    }



    private GroupType stringToGroupType(String type) {
        switch (type) {
            case "Home" -> {
                return GroupType.HOME;
            }
            case "Trip" -> {
                return GroupType.TRIP;
            }
            case "Zan-o-Bache" -> {
                return GroupType.ZAN_O_BACHE;
            }
            case "Other" -> {
                return GroupType.OTHER;
            }
        }
        return null;
    }
}