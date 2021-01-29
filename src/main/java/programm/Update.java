package programm;

import mydb.*;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.List;

public class Update
{
    public void UpdateTeam(String Name, String Coach, String Stadium, String Points, String IdTeam) throws MyErrors
    {
        if (Name.isEmpty() || Coach.isEmpty() || Stadium.isEmpty())
        {
            throw new MyErrors("Enter all fields");
        }

        EntityManager Em = GUI_main.ourSessionFactory.createEntityManager();

        Em.getTransaction().begin();
        List<TeamEntity> team_list = Em.createQuery("SELECT t FROM TeamEntity t WHERE t.name = '" + Name + "'").getResultList();
        Em.getTransaction().commit();

        if (!team_list.isEmpty()&&(team_list.get(0).getIdTeam()!=Integer.parseInt(IdTeam)))
        {
            throw new MyErrors("This team already exists");
        }

        Em.getTransaction().begin();
        TeamEntity te = Em.find(TeamEntity.class, Integer.parseInt(IdTeam));
        te.setName(Name);
        te.setCoach(Coach);
        te.setStadium(Stadium);
        te.setTeamPoints(Points.isEmpty()?0:Integer.parseInt(Points));
        Em.getTransaction().commit();
    }

    public void UpdatePlayer(String KitNumber, String Name, String LastName, String Position, String Team, String IdPlayer) throws MyErrors
    {
        if (KitNumber.isEmpty() || Name.isEmpty() || LastName.isEmpty() || Position.isEmpty() || Team.isEmpty())
        {
            throw new MyErrors("Enter all fields");
        }

        EntityManager Em = GUI_main.ourSessionFactory.createEntityManager();

        Em.getTransaction().begin();
        List<TeamEntity> team_list = Em.createQuery("SELECT t FROM TeamEntity t WHERE t.name = '" + Team + "'").getResultList();
        Em.getTransaction().commit();

        if (team_list.isEmpty())
        {
            throw new MyErrors("There are no such teams");
        }

        Em.getTransaction().begin();
        List<PlayerEntity> player_list = Em.createQuery("SELECT p FROM PlayerEntity p WHERE p.teamByIdTeam.name = '" + Team + "' AND p.kitNumber = '" + KitNumber + "'").getResultList();
        Em.getTransaction().commit();

        if (!player_list.isEmpty()&&(player_list.get(0).getIdPlayer()!=Integer.parseInt(IdPlayer)))
        {
            throw new MyErrors("Team already has a player with this Kit number");
        }

        Em.getTransaction().begin();
        PlayerEntity pe = Em.find(PlayerEntity.class, Integer.parseInt(IdPlayer));
        pe.setKitNumber(Integer.parseInt(KitNumber));
        pe.setName(Name);
        pe.setLastName(LastName);
        pe.setPosition(Position);
        pe.setTeamByIdTeam(team_list.get(0));
        Em.getTransaction().commit();
    }

    public void UpdateGame(String Date, String HomeTeam, String AwayTeam, String Stadium, String TicketPrice, String Result, String IdGame) throws MyErrors
    {
        if (Date.isEmpty() || HomeTeam.isEmpty() || AwayTeam.isEmpty() || Stadium.isEmpty())
        {
            throw new MyErrors("Enter all fields");
        }

        EntityManager Em = GUI_main.ourSessionFactory.createEntityManager();

        Em.getTransaction().begin();
        List<TeamEntity> team_list = Em.createQuery("SELECT t FROM TeamEntity t WHERE t.name = '" + HomeTeam + "'").getResultList();
        Em.getTransaction().commit();

        if (team_list.isEmpty())
        {
            throw new MyErrors("There are no such teams");
        }

        Em.getTransaction().begin();
        List<GameEntity> game_list = Em.createQuery("SELECT g FROM GameEntity g WHERE g.date = '" + java.sql.Date.valueOf(Date) + "' AND g.teamByIdTeam.name = '" + HomeTeam + "' AND g.awayTeam = '" + AwayTeam + "'").getResultList();
        Em.getTransaction().commit();

        if (!game_list.isEmpty()&&(game_list.get(0).getIdGame()!=Integer.parseInt(IdGame)))
        {
            throw new MyErrors("This game already exists");
        }

        Em.getTransaction().begin();
        GameEntity ge = Em.find(GameEntity.class, Integer.parseInt(IdGame));
        ge.setDate(java.sql.Date.valueOf(Date));
        ge.setTeamByIdTeam(team_list.get(0));
        ge.setAwayTeam(AwayTeam);
        ge.setStadium(Stadium);
        ge.setTicketPrice(TicketPrice.isEmpty()?0:Integer.parseInt(TicketPrice));
        ge.setResult(Result);
        Em.getTransaction().commit();
    }

    public void UpdateStatistic(String IdGame, String IdPlayer, String Goals, String Assists, String IdStatistic) throws MyErrors
    {
        if (IdGame.isEmpty() || IdPlayer.isEmpty())
        {
            throw new MyErrors("Enter all fields");
        }

        EntityManager Em = GUI_main.ourSessionFactory.createEntityManager();

        Em.getTransaction().begin();
        List<GameEntity> game_list = Em.createQuery("SELECT g FROM GameEntity g WHERE g.idGame = '" + Integer.parseInt(IdGame) + "'").getResultList();
        Em.getTransaction().commit();

        if (game_list.isEmpty())
        {
            throw new MyErrors("There are no such games");
        }

        Em.getTransaction().begin();
        List<PlayerEntity> player_list = Em.createQuery("SELECT p FROM PlayerEntity p WHERE p.idPlayer = '" + Integer.parseInt(IdPlayer) + "'").getResultList();
        Em.getTransaction().commit();

        if (player_list.isEmpty())
        {
            throw new MyErrors("There are no such players");
        }

        if (player_list.get(0).getTeamByIdTeam().getName() != game_list.get(0).getTeamByIdTeam().getName())
        {
            throw new MyErrors("Player's club didn't play at this game");
        }

        Em.getTransaction().begin();
        List<StatisticEntity> statistic_list = Em.createQuery("SELECT s FROM StatisticEntity s WHERE s.gameByIdGame.idGame = '" + Integer.parseInt(IdGame) + "' AND s.playerByIdPlayer.idPlayer = '" + Integer.parseInt(IdPlayer) + "'").getResultList();
        Em.getTransaction().commit();

        if (!statistic_list.isEmpty()&&(statistic_list.get(0).getIdStatistic()!=Integer.parseInt(IdStatistic)))
        {
            throw new MyErrors("This statistic already exists");
        }

        Em.getTransaction().begin();
        StatisticEntity se = Em.find(StatisticEntity.class, Integer.parseInt(IdStatistic));
        se.setGameByIdGame(game_list.get(0));
        se.setPlayerByIdPlayer(player_list.get(0));
        se.setGoals(Goals.isEmpty()?0:Integer.parseInt(Goals));
        se.setAssists(Assists.isEmpty()?0:Integer.parseInt(Assists));
        Em.getTransaction().commit();
    }
}
