package com.seiji.dslist.game.application.dto;

import com.seiji.dslist.game.domain.GameList;

public record GameListDTO(
        Long id,
        String name
) {

    public GameListDTO(GameList gameList) {
        this(
                gameList.getId(),
                gameList.getName()
        );
    }
} 