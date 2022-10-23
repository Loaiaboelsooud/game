package com.example.game.models;

import java.util.List;

public class Game {
   private List<Player> players;
   private Boolean isEndGame;

    public Game(List<Player> players) {
        this.players = players;
        this.isEndGame=Boolean.FALSE;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Boolean getEndGame() {
        return isEndGame;
    }

    public void setEndGame(Boolean endGame) {
        isEndGame = endGame;
    }
}
