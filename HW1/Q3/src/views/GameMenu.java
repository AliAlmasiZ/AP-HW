package views;

import controllers.GameMenuController;
import models.App;
import models.Result;
import models.enums.GameMenuCommands;
import models.enums.SignupMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    private final GameMenuController controller = new GameMenuController();
    @Override
    public void checker(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if((matcher = GameMenuCommands.ELECTION.getMatcher(input)) != null) {
            System.out.println(controller.election());
            String name = scanner.nextLine().trim();
            Result result = controller.changeLeader(name);
            while(!result.isSuccessful()) {
                System.out.println(result);
                result = controller.changeLeader(scanner.nextLine().trim());
            }
        } else if((matcher = GameMenuCommands.SWITCH.getMatcher(input)) != null) {
            System.out.println(controller.switchPlayer(matcher.group("username")));
        } else if((matcher = GameMenuCommands.SADAGHALLAH.getMatcher(input)) != null) {
            controller.sadaghalah();
        } else if((matcher = GameMenuCommands.EXIT.getMatcher(input)) != null) {
            controller.exit();
        } else if(App.getActiveGame().getPlayingUser().getPlayingCountry().isLocked()) {
            boolean valid = false;
            for (GameMenuCommands command : GameMenuCommands.values()) {
                if(command.getMatcher(input) != null)
                    valid = true;
            }
            if(valid)
                System.out.println("game is locked");
            else
                System.out.println("invalid command");
        } else if((matcher = GameMenuCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
            System.out.println(App.getActiveMenu());
        } else if((matcher = GameMenuCommands.SHOW_DETAIL.getMatcher(input)) != null) {
            System.out.println(controller.showDetail(matcher.group("countryName")));
        } else if((matcher = GameMenuCommands.TILE_OWNER.getMatcher(input)) != null) {
            System.out.println(controller.tileOwner(matcher.group("index")));
        } else if((matcher = GameMenuCommands.TILE_NEIGHBORS.getMatcher(input)) != null) {
            System.out.println(controller.tileNeighbors(matcher.group("index")));
        } else if((matcher = GameMenuCommands.TILE_SEA_NEIGHBORS.getMatcher(input)) != null) {
            System.out.println(controller.tileSeaNeighbors(matcher.group("index")));
        } else if((matcher = GameMenuCommands.SHOW_WEATHER.getMatcher(input)) != null) {
            System.out.println(controller.showWeather(matcher.group("tileIndex")));
        } else if((matcher = GameMenuCommands.SHOW_TERRAIN.getMatcher(input)) != null) {
            System.out.println(controller.showTerrain(matcher.group("tileIndex")));
        } else if((matcher = GameMenuCommands.SHOW_BATTALION.getMatcher(input)) != null) {
            System.out.println(controller.showBattalion(matcher.group("tileIndex")));
        } else if((matcher = GameMenuCommands.SHOW_FACTORY.getMatcher(input)) != null) {
            System.out.println(controller.showFactories(matcher.group("tileIndex")));
        } else if((matcher = GameMenuCommands.SET_TERRAIN.getMatcher(input)) != null) {
            System.out.println(
                    controller.setTerrain(
                            matcher.group("tileIndex"),
                            matcher.group("terrainName")
                    )
            );
        } else if((matcher = GameMenuCommands.SET_WEATHER.getMatcher(input)) != null) {
            System.out.println(
                    controller.setWeather(
                            matcher.group("tileIndex"),
                            matcher.group("weatherName")
                    )
            );
        } else if((matcher = GameMenuCommands.ADD_BATTALION.getMatcher(input)) != null) {
            System.out.println(controller.addBattalion(
                    matcher.group("tileIndex"),
                    matcher.group("battalionType"),
                    matcher.group("name")
            ));
        } else if((matcher = GameMenuCommands.MOVE_BATTALION.getMatcher(input)) != null) {
            System.out.println(controller.moveBattalion(
                    matcher.group("tileIndex"),
                    matcher.group("battalionName"),
                    matcher.group("destinationTileIndex")
            ));
        } else if((matcher = GameMenuCommands.UPGRADE_BATTALION.getMatcher(input)) != null) {
            System.out.println(controller.upgradeBattalion(
                    matcher.group("tileIndex"),
                    matcher.group("battalionName")
            ));
        } else if((matcher = GameMenuCommands.CREATE_FACTION.getMatcher(input)) != null) {
            System.out.println(controller.createFaction(matcher.group("name")));
        } else if((matcher = GameMenuCommands.JOIN_FACTION.getMatcher(input)) != null) {
            System.out.println(controller.joinFaction(matcher.group("factionName")));
        } else if((matcher = GameMenuCommands.LEAVE_FACTION.getMatcher(input)) != null) {
            System.out.println(controller.leaveFaction(matcher.group("factionName")));
        } else if((matcher = GameMenuCommands.BUILD_FACTORY.getMatcher(input)) != null) {
            System.out.println(controller.buildFactory(
                    matcher.group("tileIndex"),
                    matcher.group("factoryType"),
                    matcher.group("name")
            ));
        } else if((matcher = GameMenuCommands.RUN_FACTORY.getMatcher(input)) != null) {
            System.out.println(controller.runFactory(
                    matcher.group("tileIndex"),
                    matcher.group("name"),
                    matcher.group("manPowerCount")
            ));
        } else if((matcher = GameMenuCommands.ATTACK.getMatcher(input)) != null) {
            System.out.println(controller.attack(
                    matcher.group("ourTileIndex"),
                    matcher.group("enemyTileIndex"),
                    matcher.group("battalionType")
            ));
        } else if((matcher = GameMenuCommands.CIVIL_WAR.getMatcher(input)) != null) {
            System.out.println(controller.startCivilWar(
                    matcher.group("tile1"),
                    matcher.group("tile2"),
                    matcher.group("battalionType")
            ));
        } else if((matcher = GameMenuCommands.PUPPET.getMatcher(input)) != null) {
            System.out.println(controller.puppet(matcher.group("countryName")));
        } else {
            System.out.println("invalid command");
        }
    }
}
