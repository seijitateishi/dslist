package com.seiji.dslist.game.application.services;

import com.seiji.dslist.game.domain.Game;
import com.seiji.dslist.game.infrastructure.GameMinProjection;
import com.seiji.dslist.game.infrastructure.GameRepository;
import com.seiji.dslist.game.application.dto.GameDTO;
import com.seiji.dslist.game.application.dto.GameMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<GameMinDTO> findAll(){
        List<Game> result = gameRepository.findAll();
        return result.stream().map(GameMinDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public GameDTO findById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
        return new GameDTO(game);
    }

    @Transactional(readOnly = true)
    public List<GameMinDTO> findByList(Long id) {
        List<GameMinProjection> list = gameRepository.searchByList(id);
        return list.stream().map(GameMinDTO::new).toList();
    }

    public List<Long> findAllGamesRating() {
        return gameRepository.findAllGamesRating();
    }

    public Long getGameRating(Long gameId) {
        if (gameId < 1) {
            throw new IllegalArgumentException("Invalid game ID");
        }
        if (!gameRepository.existsById(gameId)) {
            throw new IllegalArgumentException("Game with ID " + gameId + " does not exist");
        }
        return gameRepository.getGameRating(gameId);
    }

    public void createGame(GameDTO gameDTO) {
        if (gameDTO == null || gameDTO.title() == null || gameDTO.title().isEmpty()) {
            throw new IllegalArgumentException("Game name cannot be null or empty");
        }
        Game game = new Game(gameDTO);
        gameRepository.save(game);
    }

} 