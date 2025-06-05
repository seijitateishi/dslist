package com.seiji.dslist.game.interfaces;

import com.seiji.dslist.game.application.dto.GameListDTO;
import com.seiji.dslist.game.application.dto.GameMinDTO;
import com.seiji.dslist.game.application.dto.ReplacementDTO;
import com.seiji.dslist.game.application.services.GameListService;
import com.seiji.dslist.game.application.services.GameService;
import com.seiji.dslist.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping(value = "/{id}")
    public List<GameMinDTO> findByList(@PathVariable Long id) {
        return gameService.findByList(id);
    }

    @PostMapping(value = "/{id}/replacement")
    public void move(@PathVariable Long id, @RequestBody ReplacementDTO body) {
        gameListService.move(id, body.getFromIndex(), body.getToIndex());
    }

    @PostMapping(value = "{name}")
    public String createList(@PathVariable String name, @AuthenticationPrincipal User user) {
        return "List " + gameListService.createList(name, user) + " created successfully.";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteList(@PathVariable Long id, @AuthenticationPrincipal User user) {
        gameListService.deleteListById(id, user);
        return "List with ID " + id + " deleted successfully.";
    }

    @GetMapping(value = "/user")
    public List<GameListDTO> getUserLists(@AuthenticationPrincipal User user) {
        return gameListService.getListsByUser(user);
    }

    @PostMapping(value = "/{listId}/game/{gameId}")
    public String addGameToList(@PathVariable Long listId, @PathVariable Long gameId, @AuthenticationPrincipal User user) {
        gameListService.addGameToListOfUser(listId, gameId, user);
        return "Game with ID " + gameId + " added to list " + listId + " successfully.";
    }

    @PutMapping(value = "/{listId}/name/{newName}")
    public String updateListName(@PathVariable Long listId, @PathVariable String newName, @AuthenticationPrincipal User user) {
        gameListService.updateListName(listId, newName, user);
        return "List with ID " + listId + " updated to name " + newName + " successfully.";
    }

    @GetMapping(value = "/rating")
    public List<Long> getAllGamesRating() {
        return gameListService.findAllGamesRating();
    }
}