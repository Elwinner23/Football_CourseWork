package mydb;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "game", schema = "footballdb")
public class GameEntity implements Serializable
{
    private int idGame;
    private Date date;
    private String awayTeam;
    private String stadium;
    private int ticketPrice;
    private String result;
    private TeamEntity teamByIdTeam;

    @Id
    @Column(name = "idGame")
    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "awayTeam")
    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Basic
    @Column(name = "stadium")
    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    @Basic
    @Column(name = "ticketPrice")
    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Basic
    @Column(name = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameEntity that = (GameEntity) o;

        if (idGame != that.idGame) return false;
        if (ticketPrice != that.ticketPrice) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (awayTeam != null ? !awayTeam.equals(that.awayTeam) : that.awayTeam != null) return false;
        if (stadium != null ? !stadium.equals(that.stadium) : that.stadium != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = idGame;
        result1 = 31 * result1 + (date != null ? date.hashCode() : 0);
        result1 = 31 * result1 + (awayTeam != null ? awayTeam.hashCode() : 0);
        result1 = 31 * result1 + (stadium != null ? stadium.hashCode() : 0);
        result1 = 31 * result1 + ticketPrice;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @ManyToOne
    @JoinColumn(name = "homeTeam", referencedColumnName = "name", nullable = false)
    public TeamEntity getTeamByIdTeam() {
        return teamByIdTeam;
    }

    public void setTeamByIdTeam(TeamEntity teamByIdTeam) {
        this.teamByIdTeam = teamByIdTeam;
    }
}