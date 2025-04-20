package views;

import controllers.GameMenuController;
import models.App;
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
        if((matcher = GameMenuCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
            System.out.println(App.getActiveMenu());
        } if((matcher = GameMenuCommands.SHOW_DETAIL.getMatcher(input)) != null) {
            System.out.println(controller.showDetail(matcher.group("country_name")));
        } else if((matcher = GameMenuCommands.EXIT.getMatcher(input)) != null) {
            controller.exit();
        } else if((matcher = GameMenuCommands.TILE_OWNER.getMatcher(input)) != null) {
            System.out.println(controller.tileOwner("index"));
        } else if((matcher = GameMenuCommands.TILE_NEIGHBORS.getMatcher(input)) != null) {
            System.out.println(controller.tileNeighbors("index"));
        } else if((matcher = GameMenuCommands.TILE_SEA_NEIGHBORS.getMatcher(input)) != null) {
            System.out.println(controller.tileSeaNeighbors(matcher.group("index")));
        } else if((matcher = GameMenuCommands.SHOW_WEATHER.getMatcher(input)) != null) {
            System.out.println(controller.showWeather("tile_index"));
        } else if((matcher = GameMenuCommands.SHOW_TERRAIN.getMatcher(input)) != null) {
            System.out.println(controller.showTerrain(matcher.group("tile_index")));
        } else if((matcher = GameMenuCommands.SHOW_BATTALION.getMatcher(input)) != null) {
            System.out.println(controller.showBattalion("tile_index"));
        } else if((matcher = GameMenuCommands.SHOW_FACTORY.getMatcher(input)) != null) {
            System.out.println(controller.showFactories(matcher.group("tile_index")));
        } else if((matcher = GameMenuCommands.SET_TERRAIN.getMatcher(input)) != null) {
            System.out.println(
                    controller.setTerrain(
                            matcher.group("tile_index"),
                            matcher.group("terrain_name")
                    )
            );
        } else if((matcher = GameMenuCommands.ADD_BATTALION.getMatcher(input)) != null) {
            System.out.println(controller.addBattalion(
                    matcher.group("tile_index"),
                    matcher.group("battalion_type"),
                    matcher.group("name")
            ));
        } else if((matcher = GameMenuCommands.MOVE_BATTALION.getMatcher(input)) != null) {
            System.out.println(controller.moveBattalion(
                    matcher.group("tile_index"),
                    matcher.group("battalion_name"),
                    matcher.group("destination_tile_index")
            ));
        } else if((matcher = GameMenuCommands.UPGRADE_BATTALION.getMatcher(input)) != null) {
            System.out.println(controller.upgradeBattalion(
                    matcher.group("tile_index"),
                    matcher.group("battalion_name")
            ));
        } else if((matcher = GameMenuCommands.CREATE_FACTION.getMatcher(input)) != null) {
            System.out.println(controller.createFaction(matcher.group("name")));
        } else if((matcher = GameMenuCommands.JOIN_FACTION.getMatcher(input)) != null) {
            System.out.println(controller.joinFaction("faction_name"));
        } else if((matcher = GameMenuCommands.LEAVE_FACTION.getMatcher(input)) != null) {
            System.out.println(controller.leaveFaction("faction_name"));
        } else if((matcher = GameMenuCommands.BUILD_FACTORY.getMatcher(input)) != null) {
            System.out.println(controller.buildFactory(
                    matcher.group("tile_index"),
                    matcher.group("factory_type"),
                    matcher.group("name")
            ));
        } else if((matcher = GameMenuCommands.RUN_FACTORY.getMatcher(input)) != null) {
            System.out.println(controller.runFactory(
                    matcher.group("tile_index"),
                    matcher.group("name"),
                    matcher.group("man_power_count")
            ));
        } else if((matcher = GameMenuCommands.ATTACK.getMatcher(input)) != null) {
            System.out.println(controller.attack(
                    matcher.group("our_tile_index"),
                    matcher.group("enemy_tile_index"),
                    matcher.group("battalion_type")
            ));
        } else if((matcher = GameMenuCommands.CIVIL_WAR.getMatcher(input)) != null) {
            System.out.println(controller.startCivilWar(
                    matcher.group("tile1"),
                    matcher.group("tile2"),
                    matcher.group("battalion_type")
            ));
        } else if((matcher = GameMenuCommands.PUPPET.getMatcher(input)) != null) {
            System.out.println(controller.puppet(matcher.group("country_name")));
        } else if((matcher = GameMenuCommands.ELECTION.getMatcher(input)) != null) {
            System.out.println(controller.election());
        } else if((matcher = GameMenuCommands.SADAGHALLAH.getMatcher(input)) != null) {
            controller.sadaghalah();
        } else {
            System.out.println("invalid command");
        }
    }
}
