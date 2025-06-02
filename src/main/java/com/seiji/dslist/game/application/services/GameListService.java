package com.seiji.dslist.game.application.services;

import com.seiji.dslist.game.domain.GameList;
import com.seiji.dslist.game.infrastructure.GameListRepository;
import com.seiji.dslist.game.infrastructure.GameMinProjection;
import com.seiji.dslist.game.infrastructure.GameRepository;
import com.seiji.dslist.game.application.dto.GameListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameListService {
    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional(readOnly = true)
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
            gameListRepository.updateBelongingPosition(listId, list.get(i).getId(), i);
        }
    }
} 