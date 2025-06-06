package com.seiji.dslist.game.interfaces;

import com.seiji.dslist.game.application.dto.GameDTO;
import com.seiji.dslist.game.application.dto.GameMinDTO;
import com.seiji.dslist.game.application.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameMinDTO> findAll(){

        return gameService.findAll();
    }

    @GetMapping(value = "/{id}")
    public GameDTO findById(@PathVariable Long id) {

        return gameService.findById(id);
    }

    @GetMapping(value = "/rating")
    public List<Long> getAllGamesRating() {

        return gameService.findAllGamesRating();
    }

    @GetMapping(value = "/rating/{gameId}")
    public Long getGameRating(@PathVariable Long gameId) {

        return gameService.getGameRating(gameId);
    }

    @PostMapping
    public String createGame(@RequestBody GameDTO gameDTO) {
        gameService.createGame(gameDTO);
        return "Game created successfully.";
    }
} 