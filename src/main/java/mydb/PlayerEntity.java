package mydb;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player", schema = "footballdb")
public class PlayerEntity implements Serializable
{
    private int idPlayer;
    private int kitNumber;
    private String name;
    private String lastName;
    private String position;
    private TeamEntity teamByIdTeam;

    @Id
    @Column(name = "idPlayer")
    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    @Basic
    @Column(name = "kitNumber")
    public int getKitNumber() {
        return kitNumber;
    }

    public void setKitNumber(int kitNumber) {
        this.kitNumber = kitNumber;
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
    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerEntity that = (PlayerEntity) o;

        if (idPlayer != that.idPlayer) return false;
        if (kitNumber != that.kitNumber) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPlayer;
        result = 31 * result + kitNumber;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "team", referencedColumnName = "name", nullable = false)
    public TeamEntity getTeamByIdTeam() {
        return teamByIdTeam;
    }

    public void setTeamByIdTeam(TeamEntity teamByIdTeam) {
        this.teamByIdTeam = teamByIdTeam;
    }
}