package controllers;

import models.*;
import models.enums.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameMenuController {
    public Result showDetail(String countryName) {

        Country country = App.getActiveGame().getCountryByType(CountryType.stringToCountry(countryName));
        if (country == null) return new Result(false, "country doesn't exist");
        String string = countryName + "\n" +
                "leader : " + country.getLeader().name().toLowerCase() + "\n" +
                "stability : " + country.getStability() + "\n" +
                "man power : " + country.getManPower() + "\n" +
                "fuel : " + country.getFuel() + "\n" +
                "sulfur : " + country.getSulfur() + "\n" +
                "steel : " + country.getSteel() + "\n" +
                "faction : " + printList(country.getFactionsNames()) + "\n" +
                "puppet : " + printList(country.getPuppets());
        return new Result(true, string);
    }

    public Result tileOwner(String index) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        if(tile == null)
            return new Result(false, "tile doesn't exist");
        return new Result(true, tile.getCountryType().getName());
    }

    public Result tileNeighbors(String index) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        if(tile == null)
            return new Result(false, "tile doesn't exist");
        return new Result(true, printNeighbor(tile.getLandNeighbors()));
    }

    public Result tileSeaNeighbors(String index) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        if(tile == null)
            return new Result(false, "tile doesn't exist");
        return new Result(true, printNeighbor(tile.getSeaNeighbors()));
    }

    public Result showWeather(String index) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        if(tile == null)
            return new Result(false, "tile doesn't exist");
        Weather weather = tile.getWeather();
        String formated = weather.name().charAt(0) + weather.name().substring(1).toLowerCase();
        return new Result(true, formated); // check
    }

    public Result showTerrain(String index) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        if(tile == null)
            return new Result(false, "tile doesn't exist");
        Terrain terrain = tile.getTerrain();
        String formated = terrain.name().charAt(0) + terrain.name().substring(1).toLowerCase();
        return new Result(true, formated);
    }

    public Result showBattalion(String index) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        if(tile == null)
            return new Result(false, "tile doesn't exist");
        Country country = App.getActiveGame().getPlayingUser().getPlayingCountry();
        if(!country.canAccessTile(tile))
            return new Result(false, "can't show battalions");
        return new Result(true, printBattalions(tile));
    }

    public Result showFactories(String index) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        if(tile == null)
            return new Result(false, "tile doesn't exist");
        Country country = App.getActiveGame().getPlayingUser().getPlayingCountry();
        if(!country.canAccessTile(tile))
            return new Result(false, "can't show factories");

        return new Result(true, printFactories(tile));
    }

    public Result setTerrain(String index, String terrainName) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        Terrain terrain = Terrain.stringToTerrain(terrainName);
        assert tile != null;
        if (!App.getActiveGame().getPlayingUser().getPlayingCountry().equals(tile.getCountryType()))
            return new Result(false, "you don't own this tile");
        if (terrain == null)
            return new Result(false, "terrain doesn't exist");
        if (tile.isTerrainChanged())
            return new Result(false, "you can't change terrain twice");
        tile.setTerrain(terrain);
        return new Result(true, "terrain set successfully");
    }

    public Result setWeather(String index, String weatherName) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        Weather weather = Weather.stringToWeather(weatherName);
        assert tile != null;
        if (!App.getActiveGame().getPlayingUser().getPlayingCountry().equals(tile.getCountryType()))
            return new Result(false, "you don't own this tile");
        if (weather == null)
            return new Result(false, "weather doesn't exist");
        tile.setWeather(weather);
        return new Result(true, "weather set successfully");
    }


    public Result addBattalion(String tileIndex, String battalionType, String name) {
        int idx = Integer.parseInt(tileIndex);
        Tile tile = App.getActiveGame().getTile(idx);
        User user = App.getActiveGame().getPlayingUser();
        Country country = App.getActiveGame().getPlayingUser().getPlayingCountry();
        BattalionType type = BattalionType.stringToBattalionType(battalionType);
        if (tile == null || !country.canAccessTile(tile)) {
            return new Result(false, "tile is unavailable");
        }
        if (type == null) {
            return new Result(false, "you can't use imaginary battalions");
        }
        if (tile.getBattalionByName(name) != null) {
            return new Result(false, "battalion name already taken" );
        }

        if (
                country.getFuel() < type.getFuelCost() ||
                country.getSteel() < type.getSteelCost() ||
                country.getSulfur() < type.getSulfurCost() ||
                country.getManPower() < type.getManPowerCost()
        ) {
            return new Result(false, "daddy USA plz help us");
        }
        if (tile.battalionTypeCount(type) >= 3)
            return new Result(false, "you can't add this type of battalion anymore");
        country.handleCosts(type.getSteelCost(), type.getManPowerCost(), type.getSulfurCost(), type.getFuelCost());
        tile.addBattalion(new Battalion(tile, type, name, country.getCountryType()));
        return new Result(true, "battalion set successfully");
    }
    public Result moveBattalion(String tileIndex, String battalionName, String destinationIndex) {
        Country country = App.getActiveGame().getPlayingUser().getPlayingCountry();
        Tile originTile = App.getTile(Integer.parseInt(tileIndex));
        Tile destination = App.getTile(Integer.parseInt(destinationIndex));
        if(originTile == null || !country.canAccessTile(originTile) || destination == null || !country.canAccessTile(destination))
            return new Result(false, "tile is unavailable");
        Battalion battalion = originTile.getBattalionByName(battalionName);
        if(battalion == null)
            return new Result(false, "no battalion with the given name");
        if(destination.battalionTypeCount(battalion.getType()) == 3)
            return new Result(false, "maximum battalion of this type in destination exists");
        if(destination.getBattalionByName(battalionName) != null)
            return new Result(false, "battalion name is already taken in this tile");
        destination.addBattalion(battalion);
        originTile.removeBattalion(battalion);
        battalion.setPosition(destination);
        return new Result(true, "battalion moved successfully");
    }

    public Result upgradeBattalion(String tileIndex, String battalionName) {
        Tile tile = App.getTile(Integer.parseInt(tileIndex));
        Country country = App.getActiveGame().getPlayingUser().getPlayingCountry();
        if(tile == null || !country.canAccessTile(tile))
            return new Result(false, "can't upgrade battalions on this tile");
        Battalion battalion = tile.getBattalionByName(battalionName);
        if(battalion == null)
            return new Result(false, "no battalion with the given name");
        if(battalion.getLevel() == 3)
            return new Result(false, "battalion is on highest level");
        int steel = (int) Math.floor(battalion.getType().getSteelCost() * battalion.getUpgradeCostWeight());
        int manpower = (int) Math.floor(battalion.getType().getManPowerCost() * battalion.getUpgradeCostWeight());
        int sulfur = (int) Math.floor(battalion.getType().getSulfurCost() * battalion.getUpgradeCostWeight());
        int fuel = (int) Math.floor(battalion.getType().getFuelCost() * battalion.getUpgradeCostWeight());
        if(steel > country.getSteel() || manpower > country.getManPower() ||
                sulfur > country.getSulfur() || fuel < country.getFuel())
            return new Result(false, "aww you can't upgrade your battalion");
        country.handleCosts(steel, manpower, sulfur, fuel);
        battalion.upgrade();
        return new Result(true, battalionName + " upgraded to level " + battalion.getLevel());
    }
    public Result createFaction(String name) {
        Game game = App.getActiveGame();
        if(game.getFaction(name) != null) {
            return new Result(false, "faction name already taken");
        }
        Faction faction = new Faction(name);
        game.addFaction(faction);
        game.getPlayingUser().getPlayingCountry().addFaction(faction);
        faction.addCountry(game.getPlayingUser().getPlayingCountry());
        return new Result(true, "faction created successfully");
    }

    public Result joinFaction(String name) {
        Game game = App.getActiveGame();
        Country country = game.getPlayingUser().getPlayingCountry();
        Faction faction = game.getFaction(name);
        if(faction == null)
            return new Result(false, "faction doesn't exist");
        country.addFaction(faction);
        faction.addCountry(country);
        return new Result(true, country + " joined " + name);
    }

    public Result leaveFaction(String name) {
        Country country = App.getActiveGame().getPlayingUser().getPlayingCountry();
        if(!country.getFactionsNames().contains(name))
            return new Result(false, "your country isn't in this faction");
        country.removeFaction(name);
        return new Result(true, country + " left " + name);
    }

    public Result buildFactory(String tileIndex, String factoryType, String name) {
        FactoryType type = FactoryType.stringToFactoryType(factoryType);
        Tile tile = App.getTile(Integer.parseInt(tileIndex));
        Country country = App.getActiveGame().getPlayingUser().getPlayingCountry();
        if(tile == null || !country.canAccessTile(tile))
            return new Result(false, "invalid tile");
        if(type == null)
            return new Result(false, "invalid factory type");
        if(
                type.getManPowerCost(tile) > country.getManPower() ||
                        type.getSteelCost(tile) > country.getSteel()
        )
            return new Result(false, "not enough money to build factory");
        if(tile.factoryTypeCount(type) == 3)
            return new Result(false, "factory limit exceeded");

        country.handleCosts(type.getSteelCost(tile), type.getManPowerCost(tile), 0, 0);
        tile.addFactory(new Factory(tile, name, type));
        return new Result(true, "factory built successfully");
    }

    public Result runFactory(String tileIndex, String name, String manPowerCount) {
        Tile tile = App.getTile(Integer.parseInt(tileIndex));
        Country country = App.getActiveGame().getPlayingUser().getPlayingCountry();
        if(tile == null || !country.canAccessTile(tile))
            return new Result(false, "invalid tile");
        Factory factory = tile.getFactoryByName(name);
        if(factory == null)
            return new Result(false, "this tile doesn't contain this factory");
        int manPower = Integer.parseInt(manPowerCount);
        if(manPower > country.getManPower())
            return new Result(false, "you are poor!");
        int fuel = 0;
        int steel = 0;
        int sulfur = 0;
        String type;
        int production = factory.run(manPower);
        switch (factory.getFactoryType()) {
            case FUEL_REFINERY -> {
                fuel = production;
                type = "fuel";
            }
            case STEEL_FACTORY -> {
                steel = production;
                type = "steel";
            }
            case SULFUR_FACTORY -> {
                sulfur = production;
                type = "sulfur";
            }
            default -> {
                type = "";
            }
        }
        Set<Country> communistAlies = country.alliesByIdeology(Ideology.COMMUNISM);
        fuel /= communistAlies.size() + 1;
        steel /= communistAlies.size() + 1;
        sulfur /= communistAlies.size() + 1;
        country.handleCosts(-steel, manPower, -sulfur, -fuel);
        for (Country aly : communistAlies) {
            aly.handleCosts(-steel, 0, -sulfur, -fuel);
        }
        if(factory.productionLeft() == 0)
            tile.removeFactory(factory);

        return new Result(true, "factory extracted " + production +" of " + type);
    }

    public Result attack(String ourTileIndex, String enemyTileIndex, String battalionType) {
        Country playerCountry = App.getActiveGame().getPlayingUser().getPlayingCountry();
        Tile ourTile = App.getTile(Integer.parseInt(ourTileIndex));
        Tile enemyTile = App.getTile(Integer.parseInt(enemyTileIndex));
        BattalionType type = BattalionType.stringToBattalionType(battalionType);
        if(ourTile == null || !playerCountry.canAttackWithTile(ourTile))
            return new Result(false, "attacker tile unavailable");
        if(type == null || ourTile.getBattalionsByType(type).isEmpty())
            return new Result(false, "selected tile doesn't have this type of battalion");



        if(!canAttack(type, ourTile, enemyTile))
            return new Result(false, "enemy tile unavailable for attacking");
        if(playerCountry.getLeader().getIdeology().equals(Ideology.FASCISM) &&
                App.getActiveGame().getCountryByType(enemyTile.getCountryType()).getLeader().getIdeology().equals(Ideology.FASCISM))
            return new Result(false, "we are rivals , not enemies");
        return handleAttack(ourTile, enemyTile, type);
            
    }

    public Result startCivilWar(String tile1Index, String tile2Index, String battalionType) {
        Country country = App.getActiveGame().getPlayingUser().getPlayingCountry();
        Tile tile1 = App.getTile(Integer.parseInt(tile1Index));
        Tile tile2 = App.getTile(Integer.parseInt(tile2Index));
        BattalionType type = BattalionType.stringToBattalionType(battalionType);
        if(country.getLeader().getIdeology().equals(Ideology.DEMOCRACY))
            return new Result(false, "no civil war for you");
        if(tile1 == null || tile2 == null ||
                !tile1.getCountryType().equals(country.getCountryType()) ||
                !tile2.getCountryType().equals(country.getCountryType()))
            return new Result(false, "invalid tiles for civil war");
        if(type == null || tile1.battalionTypeCount(type) == 0)
            return new Result(false, "invalid battalion type");
        boolean canAttack = false;
        switch (type) {
            case NAVY -> {
                canAttack = tile1.isSeaNeighbor(tile2);
            }
            case INFANTRY, PANZER -> {
                canAttack = tile1.isLandNeighbor(tile2);
            }
            case AIRFORCE -> {
                canAttack = true;
            }
        };
        if(!canAttack)
            return new Result(false, "can't start civil war between these tiles");
        country.multiplyStability(0.1);
        int power1 = 0, power2 = 0;
        for (Battalion battalion : tile1.getBattalionsByType(type)) {
            power1 += battalion.getPower();
        }
        for (Battalion battalion : tile2.getBattalionsByType(type)) {
            power2 += battalion.getPower();
        }
        if(power1 > power2) {
            tile2.getBattalionsByType(type).clear();
            return new Result(true, "civil war ended. " + 1 + " won.");
        } else if(power1 == power2) {
            tile2.getBattalionsByType(type).clear();
            tile1.getBattalionsByType(type).clear();
            return new Result(false, "man dige harfi nadaram.");
        } else {
            tile1.getBattalionsByType(type).clear();
            return new Result(true, "civil war ended. " + 2 + " won.");
        }
    }

    public Result puppet(String countryName) {
        Country puppet = App.getActiveGame().getCountryByType(CountryType.stringToCountry(countryName));
        Country myCountry = App.getActiveGame().getPlayingUser().getPlayingCountry();
        if(puppet == null)
            return new Result(false, "country doesn't exist");
        if (
                !(myCountry.getManPower() > puppet.getManPower()) ||
                !myCountry.canMakePuppet(puppet.getCountryType()) ||
                !(
                        myCountry.getLeader().getIdeology().equals(Ideology.COMMUNISM) ||
                        myCountry.getLeader().getIdeology().equals(Ideology.FASCISM)
                ) ||
                        myCountry.isInFaction(puppet)

        )
            return new Result(false, "you are not allowed to puppet this country");
        myCountry.addPuppet(puppet);
        return new Result(
                true,
                "now " + puppet.getCountryType().getName() +" is my puppet yo ho ha ha ha"
        );
    }

    public Result election() {
        return new Result(false, "");
        //TODO
    }

    public void sadaghalah() {
        App.setActiveMenu(Menu.MAIN_MENU);
    }

    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }
    private <T> String printList(List<T> list) {
        if (list.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        for (T o : list) {
            sb.append(o.toString()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String printNeighbor(List<Integer> list) {
        if (list.isEmpty())
            return "no sea neighbors";
        StringBuilder sb = new StringBuilder();
        list.sort(Integer::compare);
        for (int i : list) {
            sb.append(i).append(" , ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }


    private String printBattalions(Tile tile) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Battalion> infantry = new ArrayList<>(tile.getBattalionsByType(BattalionType.INFANTRY));
        ArrayList<Battalion> panzer = new ArrayList<>(tile.getBattalionsByType(BattalionType.PANZER));
        ArrayList<Battalion> airforce = new ArrayList<>(tile.getBattalionsByType(BattalionType.AIRFORCE));
        ArrayList<Battalion> navy = new ArrayList<>(tile.getBattalionsByType(BattalionType.NAVY));
        infantry.sort((e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.getName(), e2.getName()));
        panzer.sort((e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.getName(), e2.getName()));
        airforce.sort((e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.getName(), e2.getName()));
        navy.sort((e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.getName(), e2.getName()));
        stringBuilder.append("infantry:\n");
        for (Battalion battalion : infantry) {
            stringBuilder
                    .append(battalion.getName()).append(" ")
                    .append(battalion.getLevel()).append(" ")
                    .append(battalion.getPower()).append(" ")
                    .append(battalion.getCaptureRatio()).append("\n");
        }
        stringBuilder.append("\npanzer:\n");
        for (Battalion battalion : panzer) {
            stringBuilder
                    .append(battalion.getName()).append(" ")
                    .append(battalion.getLevel()).append(" ")
                    .append(battalion.getPower()).append(" ")
                    .append(battalion.getCaptureRatio()).append("\n");
        }
        stringBuilder.append("\nairforce:\n");
        for (Battalion battalion : airforce) {
            stringBuilder
                    .append(battalion.getName()).append(" ")
                    .append(battalion.getLevel()).append(" ")
                    .append(battalion.getPower()).append(" ")
                    .append(battalion.getCaptureRatio()).append("\n");
        }
        stringBuilder.append("\nnavy:\n");
        for (Battalion battalion : navy) {
            stringBuilder
                    .append(battalion.getName()).append(" ")
                    .append(battalion.getLevel()).append(" ")
                    .append(battalion.getPower()).append(" ")
                    .append(battalion.getCaptureRatio()).append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public String printFactories(Tile tile) {
        ArrayList<Factory> fuelRefinery = new ArrayList<>(tile.getFactoriesByType(FactoryType.FUEL_REFINERY));
        ArrayList<Factory> steel = new ArrayList<>(tile.getFactoriesByType(FactoryType.STEEL_FACTORY));
        ArrayList<Factory> sulfur = new ArrayList<>(tile.getFactoriesByType(FactoryType.SULFUR_FACTORY));
        fuelRefinery.sort((e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.getName(), e2.getName()));
        steel.sort((e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.getName(), e2.getName()));
        sulfur.sort((e1, e2) -> String.CASE_INSENSITIVE_ORDER.compare(e1.getName(), e2.getName()));

        StringBuilder sb = new StringBuilder();
        sb.append("fuel refinery:\n");
        for (Factory factory : fuelRefinery) {
            sb.append(factory.getName()).append(" ").append(factory.productionLeft()).append("\n");
        }
        sb.append("\nsteel factory:\n");
        for (Factory factory : steel) {
            sb.append(factory.getName()).append(" ").append(factory.productionLeft()).append("\n");
        }
        sb.append("\nsulfur factory:\n");
        for (Factory factory : sulfur) {
            sb.append(factory.getName()).append(" ").append(factory.productionLeft()).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private Result handleAttack(Tile attackerTile, Tile defenderTile, BattalionType type) {
        Country attacker = App.getActiveGame().getCountryByType(attackerTile.getCountryType());
        Country defender = App.getActiveGame().getCountryByType(defenderTile.getCountryType());
        ArrayList<Battalion>attackerBattalions = attackerTile.getBattalionsByType(type);
        ArrayList<Battalion>defenderBattalions = defenderTile.getBattalionsByType(type);
        int attackerPower = 0, defenderPower = 0;
        for(Battalion battalion : attackerBattalions) {
            attackerPower += battalion.getPower();
        }
        for(Battalion battalion : defenderBattalions) {
            defenderPower += battalion.getPower();
        }
        int powerDiff = attackerPower - defenderPower;
        if(powerDiff > 0) {
            int capturePower = 0;
            for (Battalion battalion : defenderBattalions) {
                capturePower += battalion.getRawPower() * battalion.getCaptureRatio() / 100;
            }
            for (Battalion attackerBattalion : attackerBattalions) {
                attackerBattalion.addPower(capturePower / attackerBattalions.size());
            }
            defenderBattalions.clear();
            // ???TODO : should I move attacker battalion to this tile ???
            defender.removeTile(defenderTile);
            attacker.addTile(defenderTile);
            attacker.multiplyStability(1.5);
            defender.multiplyStability(0.5);
            return new Result(true, "war is over \n" +
                    "winner : " + attacker.getCountryType().getName() + "\n" +
                    "loser : " + defender.getCountryType().getName());
        }
        if(powerDiff == 0) {
            defenderBattalions.clear();
            attackerBattalions.clear();
            return new Result(true, "draw");
        }
        int capturePower = 0;
        for (Battalion battalion : attackerBattalions) {
            capturePower += battalion.getRawPower() * battalion.getCaptureRatio() / 100;
        }
        for (Battalion battalion : defenderBattalions) {
            battalion.addPower(capturePower / defenderBattalions.size());
        }
        attackerBattalions.clear();
        //
        defender.multiplyStability(1.5);
        attacker.multiplyStability(0.5);
        powerDiff *= -1;
        defender.handleCosts(powerDiff * 100, 0, powerDiff * 100, powerDiff * 100);
        return new Result(true, "war is over \n" +
                "winner : " + defender.getCountryType().getName() + "\n" +
                "loser : " + attacker.getCountryType().getName());


    }

    private boolean canAttack(BattalionType type, Tile ourTile, Tile enemyTile) {
        Country playerCountry = App.getActiveGame().getPlayingUser().getPlayingCountry();
        boolean canAttack = false;
        switch (type) {
            case NAVY -> {
                canAttack = enemyTile != null && ourTile.isSeaNeighbor(enemyTile);
            }
            case INFANTRY, PANZER -> {
                canAttack = enemyTile != null && ourTile.isLandNeighbor(enemyTile);
            }
            case AIRFORCE -> {
                canAttack = enemyTile != null;
            }
        };
        if(!canAttack)
            return false;
        Country enemy = App.getActiveGame().getCountryByType(enemyTile.getCountryType());
        canAttack =  !playerCountry.isPuppeteer(enemyTile.getCountryType()) && !playerCountry.isInFaction(enemy);
        return canAttack;
    }
}
