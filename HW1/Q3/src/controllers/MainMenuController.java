package controllers;

import models.*;
import models.enums.CountryType;
import models.enums.Menu;

import java.util.Objects;


public class MainMenuController {
    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }

    public void logout() {
        App.setActiveMenu(Menu.SIGNUP_MENU);
    }

    public void leaderBoard() {
        App.setActiveMenu(Menu.LEADERBOARD_MENU);
    }

    public Result play(String[] users) {
        User activeUser  = App.getActiveUser();
        for (String user : users) {
            if(user!= null && App.getUsernameToUser().get(user) == null)
                return new Result(false, "select between available users");
        }
        for(int i = 0; i < 3; i++) {
            for(int j = i + 1; j < 4; j++) {
                if(users[i] != null && users[i].equals(users[j]))
                    return new Result(false, "users must be distinct");
            }
        }
        User[] players = new User[5];
        players[0] = activeUser;
        int j = 1;
        for (int i = 0; i < 4; i++) {
            if(Objects.equals(users[i], activeUser.getUsername()))
                return new Result(false, "you can't choose urself");
            if(users[i] != null)
                players[i + 1] = App.getUsernameToUser().get(users[i]);
            else
                players[i + 1] = new User("guest" + j++ , "", "");

        }

        App.setActiveGame(new Game(players));
        App.setActiveMenu(Menu.GAME_MENU);
        App.resetTiles();
        return new Result(true, "aghaaz faAliat");
    }

    public Result chooseCountry(String countryName, User user) {
        Game game = App.getActiveGame();
        CountryType type = CountryType.stringToCountry(countryName.trim());
        if(type == null)
            return new Result(false, "wrong country name");
        if(game.getCountryByType(type) != null)
            return new Result(false, "country already taken");
        Country country = new Country(type);
        game.addCountry(type, country);
        user.setPlayingCountry(country);
        return new Result(true, "");
    }
}
