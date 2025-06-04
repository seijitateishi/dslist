package com.seiji.dslist.user.application.services;

import com.seiji.dslist.game.application.services.GameListService;
import com.seiji.dslist.game.domain.GameList;
import com.seiji.dslist.user.domain.User;
import com.seiji.dslist.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
private UserRepository userRepository;
}
