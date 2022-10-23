package com.example.game;

import com.example.game.controllers.GooseGameController;
import com.example.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.objenesis.strategy.SingleInstantiatorStrategy;


@SpringBootApplication
public class GooseApplication {

	public static void main(String[] args) {

		SpringApplication.run(GooseApplication.class, args).getBean(GooseGameController.class).startGame();

	}


}
