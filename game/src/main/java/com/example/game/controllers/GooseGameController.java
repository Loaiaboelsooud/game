package com.example.game.controllers;

import com.example.game.models.Game;
import com.example.game.models.Player;
import com.example.game.service.GameService;
import com.example.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class GooseGameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerService playerService;

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        String userCommand;
        List<Player> players = new ArrayList<>();
        int numberOfPlayers = 0;
        while (numberOfPlayers != 1 && numberOfPlayers != 2) {
            System.out.println("Please enter number of players (1 or 2) ");
            if (sc.hasNextInt()) {
                numberOfPlayers = sc.nextInt();
            } else {
                sc = new Scanner(System.in);
            }
        }
        while (players.size() < Integer.valueOf(numberOfPlayers)) {
            players = playerService.addNewPlayer(players);
        }
        Game game = new Game(players);
        System.out.println("Enter command: ");
        sc = new Scanner(System.in);
        userCommand = sc.nextLine();
        while (!gameService.play(game, userCommand)) {
            System.out.println("Enter command: ");
            userCommand = sc.nextLine();
        }
    }
}
