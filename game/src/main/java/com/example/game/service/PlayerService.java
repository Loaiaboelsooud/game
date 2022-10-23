package com.example.game.service;

import com.example.game.models.Player;

import java.util.List;
public interface PlayerService {

    Boolean isPlayerExists(List<Player> players, String playerName, Boolean printErrorMsg);

    List<Player> addNewPlayer(List<Player> players);
    List<String> getPlayerNames(List<Player> players);
    Player getPlayerByName(List<Player> players,String name);
    Boolean isPlayerTurn (List<Player> players,String name);
    void togglePlayersTurn (List<Player> players,Player player);
    void penaltyIfSameSpace(List<Player> players, Player player, int posBeforePlaying);
}
