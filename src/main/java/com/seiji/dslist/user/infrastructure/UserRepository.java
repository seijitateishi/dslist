package com.seiji.dslist.user.infrastructure;

import com.seiji.dslist.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUsername(String username);
    @Query(nativeQuery = true, value = "SELECT game_id FROM tb_game_position WHERE list_id = :listId ORDER BY position")
    List<Long> findGameIdsByListId(Long listId);
} 