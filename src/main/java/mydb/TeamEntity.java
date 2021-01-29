package mydb;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "team", schema = "footballdb")
public class TeamEntity implements Serializable
{
    private int idTeam;
    private String name;
    private String coach;
    private String stadium;
    private int teamPoints;

    @Id
    @Column(name = "idTeam")
    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "coach")
    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
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
    @Column(name = "teamPoints")
    public int getTeamPoints() {
        return teamPoints;
    }

    public void setTeamPoints(int teamPoints) {
        this.teamPoints = teamPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamEntity that = (TeamEntity) o;

        if (idTeam != that.idTeam) return false;
        if (teamPoints != that.teamPoints) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (coach != null ? !coach.equals(that.coach) : that.coach != null) return false;
        if (stadium != null ? !stadium.equals(that.stadium) : that.stadium != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTeam;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (coach != null ? coach.hashCode() : 0);
        result = 31 * result + (stadium != null ? stadium.hashCode() : 0);
        result = 31 * result + teamPoints;
        return result;
    }
}