package com.seiji.dslist.user.interfaces;

import com.seiji.dslist.user.application.services.UserService;
import com.seiji.dslist.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/create-list/{name}")
    public String createList(@PathVariable String name, @AuthenticationPrincipal User user) {
        return "List " + userService.createList(name, user) + " created successfully.";
    }

    @DeleteMapping(value = "/{id}/delete-list")
    public String deleteList(@PathVariable Long id, @AuthenticationPrincipal User user) {
        userService.deleteList(id, user);
        return "List with ID " + id + " deleted successfully.";
    }

    @GetMapping(value = "/{id}/lists")
    public String getUserLists(@PathVariable Long id, @AuthenticationPrincipal User user) {
    return "User " + user.getUsername() + " has the following lists: " +
            user.getGameLists().stream()
                .map(list -> list.getName())
                .reduce((list1, list2) -> list1 + ", " + list2)
                .orElse("No lists found.");
    }

    @PostMapping(value = "/{listName}/add-game/{gameId}")
    public String addGameToList(@PathVariable String listName, @PathVariable Long gameId, @AuthenticationPrincipal User user) {
        userService.addGameToListOfUser(listName, gameId, user);
        return "Game with ID " + gameId + " added to list " + listName + " successfully.";
    }
}
