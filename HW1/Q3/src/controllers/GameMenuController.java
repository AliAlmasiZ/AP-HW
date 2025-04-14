package controllers;

import models.App;
import models.Result;
import models.Tile;
import models.enums.Country;
import models.enums.Terrain;
import models.enums.Weather;

import java.util.List;

public class GameMenuController {
    public Result showDetail(String countryName) {
        Country country = Country.stringToCountry(countryName);
        if(country == null) return new Result(false, "country doesn't exist");
        String string = countryName + "\n" +
                "leader : " + country.getLeader().name().toLowerCase() + "\n" +
                "stability : " + country.getStability() + "\n" +
                "man power : " + country.getManPower() + "\n" +
                "fuel : " + country.getFuel() + "\n" +
                "sulfur : " + country.getSulfur() + "\n" +
                "steel : " + country.getSteel() + "\n" +
                "faction : " + printList(country.getFactions()) + "\n" +
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
        if(!App.getActiveGame().getPlayingUser().getPlayingCountry().equals(tile.getCountry()))
            return new Result(false, "you don't own this tile");
        if(terrain == null)
            return new Result(false, "terrain doesn't exist");
        if(tile.isTerrainChanged())
            return new Result(false, "you can't change terrain twice");
        tile.setTerrain(terrain);
        return new Result(true, "terrain set successfully");
    }

    public Result setWeather(String index, String weatherName) {
        int idx = Integer.parseInt(index);
        Tile tile = App.getTile(idx);
        Weather weather = Weather.stringToWeather(weatherName);
        assert tile != null;
        if(!App.getActiveGame().getPlayingUser().getPlayingCountry().equals(tile.getCountry()))
            return new Result(false, "you don't own this tile");
        if(weather == null)
            return new Result(false, "weather doesn't exist");
        tile.setWeather(weather);
        return new Result(true, "weather set successfully");
    }

    public Result addBattalion(String tileIndex, String battalionType, String name) {

    }





    private <T> String printList(List<T> list) {
        if(list.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        for (T o : list) {
            sb.append(o.toString()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String printNeighbor(List<Integer> list) {
        if(list.isEmpty())
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
