package com.seiji.dslist.game.infrastructure;

import com.seiji.dslist.game.domain.GameList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameListRepository extends JpaRepository<GameList, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE tb_game_position SET position = :newPosition WHERE list_id = :listId AND game_id = :gameId")
    void updateGamePosition(Long listId, Long gameId, Integer newPosition);
}