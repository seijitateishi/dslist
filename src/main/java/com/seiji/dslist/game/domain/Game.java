package com.seiji.dslist.game.domain;

import com.seiji.dslist.game.application.dto.GameDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "tb_game")
public class Game {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "game_year")
    private Integer year;
    private String genre;
    private String platforms;
    private Double score;
    private String imgUrl;
    @Column(columnDefinition = "TEXT")
    private String shortDescription;
    @Column(columnDefinition = "TEXT")
    private String longDescription;

    public Game() {
    }

    public Game(Long id, String title, Integer year, String genre, String platforms, Double score, String imgUrl, String shortDescription, String longDescription) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.platforms = platforms;
        this.score = score;
        this.imgUrl = imgUrl;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    public Game(GameDTO gameDTO) {
        this.title = gameDTO.title();
        this.year = gameDTO.year();
        this.genre = gameDTO.genre();
        this.platforms = gameDTO.platforms();
        this.score = gameDTO.score();
        this.imgUrl = gameDTO.imgUrl();
        this.shortDescription = gameDTO.shortDescription();
        this.longDescription = gameDTO.longDescription();
    }
} 