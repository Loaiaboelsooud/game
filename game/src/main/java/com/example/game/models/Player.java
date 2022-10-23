package com.example.game.models;

public class Player {

    private String name;
    private int currentPos;
    private Boolean isPlayerTurn;

    public Player(String name) {
        this.name = name;
        this.isPlayerTurn=Boolean.TRUE;
        this.currentPos=0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public Boolean getPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(Boolean playerTurn) {
        isPlayerTurn = playerTurn;
    }
}
