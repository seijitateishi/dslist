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

    @Query(nativeQuery = true, value = """
        SELECT 
            COALESCE(SUM(
                CASE 
                    WHEN gp.position = 0 THEN 5
                    WHEN gp.position = 1 THEN 4
                    WHEN gp.position = 2 THEN 3
                    WHEN gp.position = 3 THEN 2
                    WHEN gp.position = 4 THEN 1
                    ELSE 0
                END
            ), 0) AS rating
        FROM tb_game g
        LEFT JOIN tb_game_position gp ON g.id = gp.game_id
        GROUP BY g.id
        ORDER BY g.id
        """)
    List<Long> findAllGamesRating();

}