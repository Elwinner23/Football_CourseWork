package programm;

import mydb.*;
import org.hibernate.id.GUIDGenerator;

import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TableModelUpdater
{
    public static String[] columns;
    public static DefaultTableModel model;

    public void UpdateTeam()
    {
        columns = new String[] {"Name", "Coach", "Stadium", "Points"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        EntityManager em = GUI_main.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<TeamEntity> team_list = em.createQuery("SELECT t FROM TeamEntity t ORDER BY t.name").getResultList();
        em.getTransaction().commit();

        for (int i = 0; i < team_list.size(); ++i) {
            TeamEntity te = team_list.get(i);
            String[] row = {te.getName(), te.getCoach(), te.getStadium(), Integer.toString(te.getTeamPoints())};
            model.addRow(row);
        }

        GUI_main.table_model = model;
    }

    public void UpdatePlayer()
    {
        columns = new String[] {"idPlayer", "Kit number", "Name", "Last name", "Position", "Team"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        EntityManager Em = GUI_main.ourSessionFactory.createEntityManager();

        Em.getTransaction().begin();
        List<PlayerEntity> player_list = Em.createQuery("SELECT p FROM PlayerEntity p ORDER BY p.teamByIdTeam.name, p.kitNumber").getResultList();
        Em.getTransaction().commit();

        for (int i = 0; i < player_list.size(); i++)
        {
            PlayerEntity pe = player_list.get(i);
            String[] row = {Integer.toString(pe.getIdPlayer()), Integer.toString(pe.getKitNumber()), pe.getName(), pe.getLastName(), pe.getPosition(), pe.getTeamByIdTeam().getName()};
            model.addRow(row);
        }

        GUI_main.table_model = model;
    }

    public void UpdateGame()
    {
        columns = new String[] {"Id Game", "Date", "HomeTeam", "AwayTeam", "Stadium", "Ticket price", "Result"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        EntityManager em = GUI_main.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<GameEntity> game_list = em.createQuery("SELECT g FROM GameEntity g ORDER BY g.teamByIdTeam.name, g.date").getResultList();
        em.getTransaction().commit();

        for (int i = 0; i < game_list.size(); i++)
        {
            GameEntity ge = game_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(ge.getIdGame()), date.format(ge.getDate()), ge.getTeamByIdTeam().getName(), ge.getAwayTeam(), ge.getStadium(), Integer.toString(ge.getTicketPrice()), ge.getResult()};
            model.addRow(row);
        }

        GUI_main.table_model = model;
    }

    public void UpdateStatistic()
    {
        columns = new String[] {"Id statistic", "Id game", "Id player", "Goals", "Assists"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        EntityManager em = GUI_main.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<StatisticEntity> statistic_list = em.createQuery("SELECT s FROM StatisticEntity s ORDER BY s.playerByIdPlayer.teamByIdTeam.name, s.gameByIdGame.date, s.goals, s.assists").getResultList();
        em.getTransaction().commit();

        for (int i = 0; i < statistic_list.size(); i++)
        {
            StatisticEntity se = statistic_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(se.getIdStatistic()), Integer.toString(se.getGameByIdGame().getIdGame()), Integer.toString(se.getPlayerByIdPlayer().getIdPlayer()), Integer.toString(se.getGoals()), Integer.toString(se.getAssists())};
            model.addRow(row);
        }

        GUI_main.table_model = model;
    }

    public void FindPlayersByTeam(String TeamName) throws  MyErrors
    {
        if (TeamName.isEmpty())
        {
            throw new MyErrors("Enter team name");
        }

        columns = new String[] {"Id player", "Kit number", "Name", "Last name", "Positon"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        EntityManager em = GUI_main.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<TeamEntity> team_list = em.createQuery("SELECT t FROM TeamEntity t WHERE t.name = '" + TeamName + "'").getResultList();
        em.getTransaction().commit();

        if (team_list.isEmpty())
        {
            throw new MyErrors("There are no such teams");
        }

        em.getTransaction().begin();
        List<PlayerEntity> player_list = em.createQuery("SELECT p FROM PlayerEntity p WHERE p.teamByIdTeam.name = '" + TeamName + "'").getResultList();
        em.getTransaction().commit();

        for (int i = 0; i < player_list.size(); ++i)
        {
            String[] row = {Integer.toString(player_list.get(i).getIdPlayer()), Integer.toString(player_list.get(i).getKitNumber()), player_list.get(i).getName(), player_list.get(i).getLastName(), player_list.get(i).getPosition()};
            model.addRow(row);
        }

        GUI_main.table_model = model;
    }

    public void FindCalendarByTeam(String TeamName) throws  MyErrors
    {
        if (TeamName.isEmpty())
        {
            throw new MyErrors("Enter team name");
        }

        columns = new String[] {"Id game", "Date", "Opponent", "Stadium", "Result"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        EntityManager em = GUI_main.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<TeamEntity> team_list = em.createQuery("SELECT t FROM TeamEntity t WHERE t.name = '" + TeamName + "'").getResultList();
        em.getTransaction().commit();

        if (team_list.isEmpty())
        {
            throw new MyErrors("There are no such teams");
        }

        em.getTransaction().begin();
        List<GameEntity> game_list = em.createQuery("SELECT g FROM GameEntity g WHERE g.teamByIdTeam.name = '" + TeamName + "' ORDER BY g.date").getResultList();
        em.getTransaction().commit();

        for (int i = 0; i < game_list.size(); ++i)
        {
            GameEntity ge = game_list.get(i);
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = {Integer.toString(ge.getIdGame()), date.format(ge.getDate()), ge.getAwayTeam(), ge.getStadium(), ge.getResult()};
            model.addRow(row);
        }

        GUI_main.table_model = model;
    }

    public void FindGamesHeldByTeam(String TeamName) throws  MyErrors
    {
        if (TeamName.isEmpty())
        {
            throw new MyErrors("Enter team name");
        }

        columns = new String[] {"Games held", "Games left"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        EntityManager em = GUI_main.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<TeamEntity> team_list = em.createQuery("SELECT t FROM TeamEntity t WHERE t.name = '" + TeamName + "'").getResultList();
        em.getTransaction().commit();

        if (team_list.isEmpty())
        {
            throw new MyErrors("There are no such teams");
        }

        em.getTransaction().begin();
        List<GameEntity> game_list = em.createQuery("SELECT g FROM GameEntity g WHERE g.teamByIdTeam.name = '" + TeamName + "'").getResultList();
        em.getTransaction().commit();

        int count = 0;
        for (int i = 0; i < game_list.size(); ++i)
        {
            GameEntity ge = game_list.get(i);
            count += ge.getResult().isEmpty()?0:1;
        }
        String[] row = {Integer.toString(count),Integer.toString(game_list.size() - count)};
        model.addRow(row);

        GUI_main.table_model = model;
    }

    public void MakeReport(String TeamName) throws  MyErrors
    {
        if (TeamName.isEmpty())
        {
            throw new MyErrors("Enter team name");
        }

        columns = new String[] {"Kit number", "Name", "Last Name", "Goals", "Assists"};
        model = new DefaultTableModel(columns, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        EntityManager em = GUI_main.ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List<TeamEntity> team_list = em.createQuery("SELECT t FROM TeamEntity t WHERE t.name = '" + TeamName + "'").getResultList();
        em.getTransaction().commit();

        if (team_list.isEmpty())
        {
            throw new MyErrors("There are no such teams");
        }

        em.getTransaction().begin();
        List<PlayerEntity> player_list = em.createQuery("SELECT p FROM PlayerEntity p WHERE p.teamByIdTeam.name = '" + TeamName + "' ORDER BY p.kitNumber").getResultList();
        em.getTransaction().commit();

        for (int i = 0; i < player_list.size(); ++i)
        {
            PlayerEntity pe = player_list.get(i);
            em.getTransaction().begin();
            List<StatisticEntity> statistic_list = em.createQuery("SELECT s FROM StatisticEntity s WHERE s.playerByIdPlayer.idPlayer = '" + pe.getIdPlayer() + "'").getResultList();
            em.getTransaction().commit();
            int goals = 0, assists = 0;
            for (int j = 0; j < statistic_list.size(); ++j)
            {
                goals += statistic_list.get(j).getGoals();
                assists += statistic_list.get(j).getAssists();
            }
            String[] row = {Integer.toString(pe.getKitNumber()), pe.getName(), pe.getLastName(), Integer.toString(goals), Integer.toString(assists)};
            model.addRow(row);
        }

        GUI_main.table_model = model;
    }
}
