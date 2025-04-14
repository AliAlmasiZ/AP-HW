package controllers;

import models.*;
import models.enums.*;

import java.util.List;

public class GameMenuController {
    public Result showDetail(String countryName) {
        Country country = Country.stringToCountry(countryName);
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
        return new Result(true, App.getTile(idx).getCountry().getName());
    }

    public Result tileNeighbors(String index) {
        int idx = Integer.parseInt(index);
        return new Result(true, printNeighbor(App.getTile(idx).getLandNeighbors()));
    }

    public Result tileSeaNeighbors(String index) {
        int idx = Integer.parseInt(index);
        return new Result(true, printNeighbor(App.getTile(idx).getSeaNeighbors()));
    }

    public Result showWeather(String index) {
        int idx = Integer.parseInt(index);
        Weather weather = App.getTile(idx).getWeather();
        String formated = weather.name().charAt(0) + weather.name().substring(1).toLowerCase();
        return new Result(true, formated); // check
    }

    public Result showTerrain(String index) {
        int idx = Integer.parseInt(index);
        Terrain terrain = App.getTile(idx).getTerrain();
        String formated = terrain.name().charAt(0) + terrain.name().substring(1).toLowerCase();
        return new Result(true, formated);
    }

    public Result showBattalion(String index) {
        //TODO
        return null;
    }

    public Result showFactories(String index) {
        //TODO
        return null;
    }

    public Result setTerrain(String index, String terrainName) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        Terrain terrain = Terrain.stringToTerrain(terrainName);
        assert tile != null;
        if (!App.getActiveGame().getPlayingUser().getPlayingCountry().equals(tile.getCountry()))
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
        if (!App.getActiveGame().getPlayingUser().getPlayingCountry().equals(tile.getCountry()))
            return new Result(false, "you don't own this tile");
        if (weather == null)
            return new Result(false, "weather doesn't exist");
        tile.setWeather(weather);
        return new Result(true, "weather set successfully");
    }

    public Result addBattalion(String tileIndex, String battalionType, String name) {
        int idx = Integer.parseInt(tileIndex);
        Tile tile = App.getActiveGame().getTile(idx);
        User user = App.getActiveUser();
        Country country = App.getActiveUser().getPlayingCountry();
        BattalionType type = BattalionType.stringToBattalionType(battalionType);
        if (
                tile == null ||
                        (!tile.getCountry().equals(user.getPlayingCountry()) && true /* TODO logic */)
        ) {
            return new Result(false, "tile is unavailable");
        }
        if (type == null) {
            return new Result(false, "you can't use imaginary battalions");
        }
        if (tile.getBattlionByName(name) != null) {
            return new Result(false, "battalion name already taken" );
        }
        boolean isDouble = country.getLeader().getIdeology().equals(Ideology.DEMOCRACY);
        if (
                country.getFuel() < type.getFuelCost(isDouble) ||
                country.getSteel() < type.getSteelCost(isDouble) ||
                country.getSulfur() < type.getSulfurCost(isDouble) ||
                country.getManPower() < type.getManPowerCost(isDouble)
        ) {
            return new Result(false, "daddy USA plz help us");
        }
        if (tile.typeCount(type) >= 3)
            return new Result(false, "you can't add this type of battalion anymore");
        tile.addBattalion(new Battalion(tile, type, name));
        return new Result(true, "battalion set successfully");
    }
    //TODO

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

    public Result puppet(String countryName) {
        Country puppet = Country.stringToCountry(countryName);
        Country myCountry = App.getActiveGame().getPlayingUser().getPlayingCountry();
        if (
                myCountry.getManPower() <= puppet.getManPower() ||

        )
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

}
