package com.seiji.dslist.services;

import com.seiji.dslist.dto.GameListDTO;
import com.seiji.dslist.entities.GameList;
import com.seiji.dslist.projections.GameMinProjection;
import com.seiji.dslist.repositories.GameListRepository;
import com.seiji.dslist.repositories.GameRepository;
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
