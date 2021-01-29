package mydb;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "statistic", schema = "footballdb")
public class StatisticEntity implements Serializable
{
    private int idStatistic;
    private int goals;
    private int assists;
    private GameEntity gameByIdGame;
    private PlayerEntity playerByIdPlayer;

    @Id
    @Column(name = "idStatistic")
    public int getIdStatistic() {
        return idStatistic;
    }

    public void setIdStatistic(int idStatistic) {
        this.idStatistic = idStatistic;
    }

    @Basic
    @Column(name = "goals")
    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    @Basic
    @Column(name = "assists")
    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatisticEntity that = (StatisticEntity) o;

        if (idStatistic != that.idStatistic) return false;
        if (goals != that.goals) return false;
        if (assists != that.assists) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idStatistic;
        result = 31 * result + goals;
        result = 31 * result + assists;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idGame", referencedColumnName = "idGame", nullable = false)
    public GameEntity getGameByIdGame() {
        return gameByIdGame;
    }

    public void setGameByIdGame(GameEntity gameByIdGame) {
        this.gameByIdGame = gameByIdGame;
    }

    @ManyToOne
    @JoinColumn(name = "idPlayer", referencedColumnName = "idPlayer", nullable = false)
    public PlayerEntity getPlayerByIdPlayer() {
        return playerByIdPlayer;
    }

    public void setPlayerByIdPlayer(PlayerEntity playerByIdPlayer) {
        this.playerByIdPlayer = playerByIdPlayer;
    }
}