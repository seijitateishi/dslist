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
} 