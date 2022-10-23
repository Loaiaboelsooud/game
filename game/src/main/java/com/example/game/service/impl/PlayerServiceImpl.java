package com.example.game.service.impl;

import com.example.game.models.Player;
import com.example.game.service.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Override
    public Boolean isPlayerExists(List<Player> players, String playerName, Boolean printErrorMsg) {
        Boolean isPlayerExists = players.stream().anyMatch(player -> ObjectUtils.nullSafeEquals(player.getName(), playerName));
        if (isPlayerExists) {
            if (printErrorMsg) {
                System.out.println(playerName + ": already existing player");
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public List<Player> addNewPlayer(List<Player> players) {
        Scanner sc = new Scanner(System.in);
        String playerName = "";
        while (ObjectUtils.isEmpty(playerName) || !isValidPlayerName(playerName) || isPlayerExists(players, playerName, Boolean.TRUE)) {
            System.out.println("Enter player name: ");
            playerName = sc.nextLine();
        }
        players.add(new Player(playerName));
        System.out.println("players :" + getPlayerNames(players));
        return players;
    }

    private boolean isValidPlayerName(String playerName) {
        if (playerName.contains(" ")) {
            System.out.println("Please enter player name without spaces");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public List<String> getPlayerNames(List<Player> players) {
        return players.stream().map(player -> player.getName()).collect(toList());
    }

    @Override
    public Player getPlayerByName(List<Player> players, String name) {
        return players.stream().filter(player -> ObjectUtils.nullSafeEquals(player.getName(), name)).
                findFirst().get();
    }

    @Override
    public Boolean isPlayerTurn(List<Player> players, String name) {
        Boolean isPlayerTurn = getPlayerByName(players, name).getPlayerTurn();
        if (isPlayerTurn) {
            return isPlayerTurn;
        }
        System.out.println("Not player " + name + " turn");
        return isPlayerTurn;
    }

    @Override
    public void togglePlayersTurn(List<Player> players, Player player) {
        if (players.size() == 2) {
            player.setPlayerTurn(Boolean.FALSE);
            getSecondPlayer(players, player).setPlayerTurn(Boolean.TRUE);
        }
    }

    @Override
    public void penaltyIfSameSpace(List<Player> players, Player player, int posBeforePlaying) {
        if (players.size() == 2) {
            Player secondPLayer = getSecondPlayer(players, player);
            int secondPlayerCurrentPosition = secondPLayer.getCurrentPos();
            if (secondPlayerCurrentPosition == player.getCurrentPos()) {
                getSecondPlayer(players, player).setCurrentPos(posBeforePlaying);
                System.out.println(" On " + secondPlayerCurrentPosition + " There is " + secondPLayer.getName() +
                        ", who returns to " + posBeforePlaying);
            }
        }
    }

    private Player getSecondPlayer(List<Player> players, Player player) {
        return players.stream().filter(p -> !ObjectUtils.nullSafeEquals(p.getName(), player.getName())).
                findFirst().get();
    }
}
