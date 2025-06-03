package com.seiji.dslist.user.application.services;

import com.seiji.dslist.game.domain.GameList;
import com.seiji.dslist.user.domain.User;
import com.seiji.dslist.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String createList(String name, User user) {
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
}
