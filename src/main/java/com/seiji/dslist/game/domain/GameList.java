package com.seiji.dslist.game.domain;

import com.seiji.dslist.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "tb_game_list")
public class GameList {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true, name = "user_id")
    private User user;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GamePosition> gamePositions = new ArrayList<>();

    public GameList(String name,User user) {
        this.name = name;
        this.user = user;
    }

    public void addGame(Long gameId, Integer position) {
        GamePosition gamePosition = new GamePosition(new GamePositionId(this.id, gameId), position);
        this.gamePositions.add(gamePosition);

    }



    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        GameList gameList = (GameList) o;
        return getId() != null && Objects.equals(getId(), gameList.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public void removeGame(Long gameId) {
        this.gamePositions.removeIf(gamePosition -> gamePosition.getId().getGameId().equals(gameId));
    }
}