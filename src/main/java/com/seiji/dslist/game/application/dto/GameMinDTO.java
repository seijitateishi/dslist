package com.seiji.dslist.game.application.dto;

import com.seiji.dslist.game.domain.Game;
import com.seiji.dslist.game.infrastructure.GameMinProjection;

public record GameMinDTO(
        Long id,
        String title,
        Integer year,
        String imgUrl,
        String shortDescription
) {

    public GameMinDTO(Game game) {
        this(
                game.getId(),
                game.getTitle(),
                game.getYear(),
                game.getImgUrl(),
                game.getShortDescription()
        );
    }

    public GameMinDTO(GameMinProjection game) {
        this(
                game.getId(),
                game.getTitle(),
                game.getGameYear(),
                game.getImgUrl(),
                game.getShortDescription()
        );
    }
} 