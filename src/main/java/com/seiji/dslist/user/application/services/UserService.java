package com.seiji.dslist.user.application.services;

import com.seiji.dslist.game.application.services.GameListService;
import com.seiji.dslist.game.domain.GameList;
import com.seiji.dslist.user.domain.User;
import com.seiji.dslist.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameListService gameListService;

    public String createList(String name, User user){
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("List name cannot be null or empty");
        }
        if (user.getGameLists().stream()
                .anyMatch(gameList -> name.equals(gameList.getName()))){
            throw new IllegalArgumentException("List with this name already exists");
        }
        GameList gameList = new GameList(name, user);
        user.getGameLists().add(gameList);
        userRepository.save(user);
        return name;
    }

    public void deleteList(Long id, User user) {
        GameList gameList = user.getGameLists().stream()
                .filter(list -> list.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("List not found"));

        user.getGameLists().remove(gameList);
        userRepository.save(user);
    }


    public void addGameToListOfUser(String listName, Long gameId, User user) {
        GameList gameList = user.getGameLists().stream()
                .filter(list -> list.getName().equals(listName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("List not found or does not exist"));

        List<Long> listOfGameId = userRepository.findGameIdsByListId(gameList.getId());
        if (listOfGameId.contains(gameId)) {
            throw new IllegalArgumentException("Game already exists in the list");
        }
        gameListService.addGameToList(gameList.getId(),gameId);
        userRepository.save(user);
    }
}
