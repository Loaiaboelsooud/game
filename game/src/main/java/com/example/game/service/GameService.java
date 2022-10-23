package com.example.game.service;

import com.example.game.models.Game;
import com.example.game.models.Player;

import java.util.List;
public interface GameService {
    Boolean play(Game game, String userCommand);
}
