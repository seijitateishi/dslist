package com.seiji.dslist.game.infrastructure;

import com.seiji.dslist.game.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query(nativeQuery = true, value = """
		SELECT tb_game.id, tb_game.title, tb_game.game_year AS gameYear, tb_game.img_url AS imgUrl,
		tb_game.short_description AS shortDescription, tb_game_position.position
		FROM tb_game
		INNER JOIN tb_game_position ON tb_game.id = tb_game_position.game_id
		WHERE tb_game_position.list_id = :listId
		ORDER BY tb_game_position.position
		""")
    List<GameMinProjection> searchByList(Long listId);

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
        FROM tb_game_position gp
        WHERE gp.game_id = :gameId
        """)
	Long getGameRating(Long gameId);
} 