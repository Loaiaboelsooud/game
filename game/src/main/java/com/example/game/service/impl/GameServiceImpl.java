package com.example.game.service.impl;

import com.example.game.configuration.Configuration;
import com.example.game.models.Game;
import com.example.game.models.Player;
import com.example.game.service.GameService;
import com.example.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private Configuration configuration;
    private static final List<Integer> GOOSE_PICTURE_SPACE = Arrays.asList(5, 9, 14, 18, 23, 27);
    private static final int BRIDGE_SPACE = 6;
    private static final int BRIDGE_SPACE_JUMP = 12;
    private static final int WIN_SPACE = 63;

    @Override
    public Boolean play(Game game, String userCommand) {
        String[] userCommandSplited = userCommand.split(" ");
        if (!validateCommand(userCommandSplited, game) ||
                !playerService.isPlayerTurn(game.getPlayers(), userCommandSplited[1])) {
            return Boolean.FALSE;
        }
        Player player = playerService.getPlayerByName(game.getPlayers(), userCommandSplited[1]);
        int posBeforePlaying = player.getCurrentPos();
        teleport(game, userCommandSplited, player);
        playerService.togglePlayersTurn(game.getPlayers(), player);
        playerService.penaltyIfSameSpace(game.getPlayers(), player, posBeforePlaying);
        return game.getEndGame();

    }

    private void teleport(Game game, String[] userCommandSplited, Player player) {
        int diceRoll = getDiceRoll(userCommandSplited);
        int currentPos = player.getCurrentPos();
        String playerName = player.getName();
        int newPos = player.getCurrentPos() + diceRoll;
        if (newPos == WIN_SPACE) {
            game.setEndGame(Boolean.TRUE);
            System.out.println(playerName + " rolls " + diceRoll + ". " + playerName
                    + " move from " + currentPos + " to 63. " + playerName + " Wins!!");
        } else if (newPos > WIN_SPACE) {
            newPos = WIN_SPACE - (newPos - WIN_SPACE);
            System.out.println(playerName + " rolls " + diceRoll + ". " + playerName
                    + " move from " + currentPos + " to 63. " + playerName
                    + " bounces! " + playerName + " returns to " + newPos);
            player.setCurrentPos(newPos);
        } else if (newPos == BRIDGE_SPACE) {
            newPos = BRIDGE_SPACE_JUMP;
            System.out.println(player.getName() + " rolls " + diceRoll + ". " + player.getName()
                    + " move from " + currentPos + " to The bridge " + playerName + " jumps to 12");
            player.setCurrentPos(newPos);
        } else if (GOOSE_PICTURE_SPACE.contains(newPos)) {
            System.out.println(player.getName() + " rolls " + diceRoll + ". " + player.getName()
                    + " move from " + currentPos + " to " + newPos + ", The Goose. " + playerName + " moves again and goes to " +
                    (newPos + diceRoll));
            newPos += diceRoll;
            player.setCurrentPos(newPos);
        } else {
            System.out.println(player.getName() + " rolls " + diceRoll + ". " + player.getName()
                    + " move from " + currentPos + " to " + (currentPos + diceRoll));
            player.setCurrentPos(newPos);
        }
    }

    private int getDiceRoll(String[] userCommandSplited) {
        if (userCommandSplited.length == 2) {
            return (new Random().nextInt(6) + 1) + (new Random().nextInt(6) + 1);
        } else {
            return Integer.valueOf(userCommandSplited[2].substring(0, userCommandSplited[2].indexOf(",")))
                    + Integer.valueOf(userCommandSplited[3]);
        }
    }

    private Boolean validateCommand(String[] userCommandSplited, Game game) {
        int userCommandSplitedLength = userCommandSplited.length;
        String moveCommand = configuration.getMove();
        if (userCommandSplitedLength < 2 || userCommandSplitedLength == 3
                || userCommandSplitedLength > 4) {
            System.out.println("Please enter valid command");
            return Boolean.FALSE;
        }
        String action = userCommandSplited[0];
        if (!ObjectUtils.nullSafeEquals(moveCommand, action)) {
            System.out.println("Please enter valid action");
            return Boolean.FALSE;
        }
        String playerName = userCommandSplited[1];
        if (!playerService.isPlayerExists(game.getPlayers(), playerName, Boolean.FALSE)) {
            System.out.println("Please enter valid player name");
            return Boolean.FALSE;
        }
        if (userCommandSplitedLength > 2) {
            if (!userCommandSplited[2].contains(",") || userCommandSplited[3].contains(",")) {
                System.out.println("Please enter valid roll");
                return Boolean.FALSE;
            }

            int firstDiceRoll = Integer.valueOf(userCommandSplited[2].
                    substring(0, userCommandSplited[2].indexOf(",")));
            int secondDiceRoll = Integer.valueOf(userCommandSplited[3]);
            if (firstDiceRoll > 6 || firstDiceRoll < 1 || secondDiceRoll > 6 || secondDiceRoll < 1) {
                System.out.println("Please enter valid dice roll");
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

}
