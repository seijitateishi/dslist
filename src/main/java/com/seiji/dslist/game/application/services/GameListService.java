package com.seiji.dslist.game.application.services;

import com.seiji.dslist.game.application.dto.GameListDTO;
import com.seiji.dslist.game.domain.GameList;
import com.seiji.dslist.game.infrastructure.GameListRepository;
import com.seiji.dslist.game.infrastructure.GameMinProjection;
import com.seiji.dslist.game.infrastructure.GameRepository;
import com.seiji.dslist.user.domain.User;
import com.seiji.dslist.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GameListService {
    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;


    public List<GameListDTO> findAll() {
        List<GameList> result = gameListRepository.findAll();
        return result.stream().map(GameListDTO::new).toList();
    }

    public void move(Long listId, int fromIndex, int toIndex) {
        List<GameMinProjection> list = gameRepository.searchByList(listId);
        GameMinProjection obj = list.remove(fromIndex);
        list.add(toIndex, obj);
        int min = Math.min(fromIndex, toIndex);
        int max = Math.max(fromIndex, toIndex);
        for (int i = min; i <= max; i++) {
            gameListRepository.updateGamePosition(listId, list.get(i).getId(), i);
        }
    }

    public String createList(String name, User user) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("List name cannot be null or empty");
        }
        if (user.getGameLists().stream()
                .anyMatch(gameList -> name.equals(gameList.getName()))) {
            throw new IllegalArgumentException("List with this name already exists");
        }
        GameList gameList = new GameList(name, user);
        user.getGameLists().add(gameList);
        userRepository.save(user);
        return name;
    }

    public void deleteListById(Long id, User user) {
        GameList gameList = user.getGameLists().stream()
                .filter(list -> list.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("List not found"));

        user.getGameLists().remove(gameList);
        userRepository.save(user);
    }

    public void addGameToListOfUser(Long listId, Long gameId, User user) {
        if (gameId < 1) {
            throw new IllegalArgumentException("Invalid game ID");
        }
        if (!gameRepository.existsById(gameId)) {
            throw new IllegalArgumentException("Game with ID " + gameId + " does not exist");
        }
        GameList gameList = user.getGameLists().stream()
                .filter(list -> list.getId().equals(listId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("List not found or does not exist"));

        List<Long> listOfGameId = userRepository.findGameIdsByListId(gameList.getId());
        if (listOfGameId.contains(gameId)) {
            throw new IllegalArgumentException("Game already exists in the list");
        }
        addGameToList(gameList.getId(), gameId);
        userRepository.save(user);
    }

    public void addGameToList(Long listId, Long gameId) {
        Optional<GameList> gameListOptional = gameListRepository.findById(listId);
        if (gameListOptional.isPresent()) {
            GameList gameList = gameListOptional.get();
            gameList.addGame(gameId, gameList.getGamePositions().size());
            gameListRepository.save(gameList);
        } else {
            throw new IllegalArgumentException("Game list not found");
        }
    }

    public List<GameListDTO> getListsByUser(User user) {
        return user.getGameLists().stream().map(GameListDTO::new).toList();
    }

    public void updateListName(Long listId, String newName, User user) {
        GameList gameList = user.getGameLists().stream()
                .filter(list -> list.getId().equals(listId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("List not found"));

        if (newName == null || newName.isEmpty()) {
            throw new IllegalArgumentException("New list name cannot be null or empty");
        }

        if (user.getGameLists().stream()
                .anyMatch(list -> list.getName().equals(newName) && !list.getId().equals(listId))) {
            throw new IllegalArgumentException("List with this name already exists");
        }

        gameList.setName(newName);
        gameListRepository.save(gameList);
    }

    public List<Long> findAllGamesRating() {
        return gameListRepository.findAllGamesRating();
    }

}