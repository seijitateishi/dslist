package com.seiji.dslist.controllers;

import com.seiji.dslist.dto.GameListDTO;
import com.seiji.dslist.dto.GameMinDTO;
import com.seiji.dslist.dto.ReplacementDTO;
import com.seiji.dslist.services.GameListService;
import com.seiji.dslist.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/lists")
public class GameListController {

    @Autowired
    private GameListService gameListService;
    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameListDTO> findAll() {
        return gameListService.findAll();
    }

    @GetMapping(value = "/{id}/games")
    public List<GameMinDTO> findByList(@PathVariable Long id) {
        return gameService.findByList(id);
    }

    @PostMapping(value = "/{id}/replacement")
    public void move(@PathVariable Long id,
                     @RequestBody ReplacementDTO body) {
        gameListService.move(id, body.getFromIndex(), body.getToIndex());
    }

    }


