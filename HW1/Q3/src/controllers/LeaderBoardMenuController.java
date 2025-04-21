package controllers;

import models.*;
import models.enums.Menu;

import java.util.ArrayList;

public class LeaderBoardMenuController {
    public void back() {
        App.setActiveMenu(Menu.MAIN_MENU);
    }
    public void exit() {
        App.setActiveMenu(Menu.EXIT_MENU);
    }

    public Result showRanking() {
        StringBuilder sb = new StringBuilder("Leaderboard:");
        for (User user : App.getSortedUsers()) {
            sb.append("\n").append(user.getUsername()).append(" ").append(user.getPoint());
        }
        return new Result(true, sb.toString());
    }

    public Result showHistory() {
        StringBuilder sb = new StringBuilder("History:");
        ArrayList<Game> lastGames = App.getLastGames();
        for (int i = 0; i < lastGames.size(); i++) {
            Game game = lastGames.get(i);
            sb.append("\n").append(printGame(game));
            if(i != lastGames.size() - 1)
                sb.append("\n----");
        }
        return new Result(true, sb.toString());
    }

    private String printGame(Game g) {
        StringBuilder sb = new StringBuilder();
        User[] allPlayers = g.getAllPlayers();
        for (int i = 0; i < 5; i++) {
            User player = allPlayers[i];
            Country country = g.getCountryByIndex(i);
            sb.append(player.getUsername()).append(" ").append(country).append(" ")
                    .append(country.getPoint()).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
