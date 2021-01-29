package programm;

import mydb.GameEntity;
import mydb.PlayerEntity;
import mydb.StatisticEntity;
import mydb.TeamEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class GUI_main extends JDialog
{
    private JTabbedPane TabbedPanel;
    private JPanel teamPanel;
    private JTextField teamNameTextField;
    private JTextField teamCoachTextField;
    private JTextField teamStadiumTextField;
    private JTextField teamPointsTextField;
    private JPanel playerPanel;
    private JTextField playerKitNumberTextField;
    private JTextField playerNameTextField;
    private JTextField playerLastNameTextField;
    private JTextField playerPositionTextField;
    private JTextField playerTeamTextField;
    private JTextField playerFindListTextField;
    private JButton playerOkButton;
    private JPanel GamePanel;
    private JTextField gameDateTextField;
    private JTextField gameHomeTeamTextField;
    private JTextField gameAwayTeamTextField;
    private JTextField gameStadiumTextField;
    private JTextField gameFindCalendarTextField;
    private JButton calendarOkButton;
    private JTextField gameGamesHeldTextField;
    private JButton gamesHeldOkButton;
    private JTextField gameTicketPriceTextField;
    private JTextField gameResultTextField;
    private JPanel statisticPanel;
    private JTextField statisticIdGameTextField;
    private JTextField statisticIdPlayerTextField;
    private JTextField statisticGoalsTextField;
    private JTextField statisticAssistsTextField;
    private JTextField statisticFindTotalStatisticsTextField;
    private JButton reportButton;
    private JButton exportReportButton;
    private JPanel TablePanel;
    private JScrollPane TableScrollPane;
    private JTable table;
    private JButton renewButton;
    private JTextField countTextField;
    private JButton addUpdateButton;
    private JButton deleteButton;
    private JPanel FreeTablePanel;
    private JPanel MainPanel;

    public static DefaultTableModel table_model;

    public static final SessionFactory ourSessionFactory;
    static
    {
        Configuration configuration = new Configuration();
        configuration.configure();
        ourSessionFactory = configuration.buildSessionFactory();
    }
    public static Session getSession() throws HibernateException
    {
        return ourSessionFactory.openSession();
    }

    TableModelUpdater TbUpdater = new TableModelUpdater();
    Add EntityAdder = new Add();
    Update EntityUpdater = new Update();

    public static int count_of_teams()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select t.idTeam from TeamEntity t").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public static int count_of_players()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select p.idPlayer from PlayerEntity p").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public static int count_of_games()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select g.idGame from GameEntity g").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public static int count_of_statistics()
    {
        EntityManager em = ourSessionFactory.createEntityManager();

        em.getTransaction().begin();
        List count = em.createQuery("select s.idStatistic from StatisticEntity s").getResultList();
        em.getTransaction().commit();

        return(count.size());
    }

    public void CleanGUI()
    {
        addUpdateButton.setText("Add");

        teamNameTextField.setText("");
        teamCoachTextField.setText("");
        teamStadiumTextField.setText("");
        teamPointsTextField.setText("");

        playerKitNumberTextField.setText("");
        playerNameTextField.setText("");
        playerLastNameTextField.setText("");
        playerPositionTextField.setText("");
        playerTeamTextField.setText("");
        playerFindListTextField.setText("");

        gameDateTextField.setText("");
        gameHomeTeamTextField.setText("");
        gameAwayTeamTextField.setText("");
        gameStadiumTextField.setText("");
        gameTicketPriceTextField.setText("");
        gameResultTextField.setText("");
        gameFindCalendarTextField.setText("");
        gameGamesHeldTextField.setText("");

        statisticIdGameTextField.setText("");
        statisticIdPlayerTextField.setText("");
        statisticGoalsTextField.setText("");
        statisticAssistsTextField.setText("");
        statisticFindTotalStatisticsTextField.setText("");

        int tabIndex = TabbedPanel.getSelectedIndex();
        switch (tabIndex)
        {
            case 0:
                TbUpdater.UpdateTeam();
                countTextField.setText(Integer.toString(count_of_teams()));
                break;
            case 1:
                TbUpdater.UpdatePlayer();
                countTextField.setText(Integer.toString(count_of_players()));
                break;
            case 2:
                TbUpdater.UpdateGame();
                countTextField.setText(Integer.toString(count_of_games()));
                break;
            case 3:
                TbUpdater.UpdateStatistic();
                countTextField.setText(Integer.toString(count_of_statistics()));
                break;
        }
        table.setModel(table_model);
    }

    public GUI_main() {
        CleanGUI();
        setContentPane(MainPanel);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                exit();
            }
        });

        TabbedPanel.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                CleanGUI();
            }
        });

        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                addUpdateButton.setText("Update");
                int tabIndex = TabbedPanel.getSelectedIndex();

                switch (tabIndex)
                {
                    case 0:
                        String teamName = table_model.getValueAt(table.getSelectedRow(), 0).toString();
                        String teamCoach = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String teamStadium = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String teamPoints = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        teamNameTextField.setText(teamName);
                        teamCoachTextField.setText(teamCoach);
                        teamStadiumTextField.setText(teamStadium);
                        teamPointsTextField.setText(teamPoints);
                        break;
                    case 1:
                        String playerKitNumber = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String playerName = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String playerLastName = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        String playerPosition = table_model.getValueAt(table.getSelectedRow(), 4).toString();
                        String playerTeam = table_model.getValueAt(table.getSelectedRow(), 5).toString();
                        playerKitNumberTextField.setText(playerKitNumber);
                        playerNameTextField.setText(playerName);
                        playerLastNameTextField.setText(playerLastName);
                        playerPositionTextField.setText(playerPosition);
                        playerTeamTextField.setText(playerTeam);
                        break;
                    case 2:
                        String gameDate = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String gameHomeTeam = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String gameAwayTeam = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        String gameStadium = table_model.getValueAt(table.getSelectedRow(), 4).toString();
                        String gameTicketPrice = table_model.getValueAt(table.getSelectedRow(), 5).toString();
                        String gameResult = table_model.getValueAt(table.getSelectedRow(), 6).toString();
                        gameDateTextField.setText(gameDate);
                        gameHomeTeamTextField.setText(gameHomeTeam);
                        gameAwayTeamTextField.setText(gameAwayTeam);
                        gameStadiumTextField.setText(gameStadium);
                        gameTicketPriceTextField.setText(gameTicketPrice);
                        gameResultTextField.setText(gameResult);
                        break;
                    case 3:
                        String statisticIdGame = table_model.getValueAt(table.getSelectedRow(), 1).toString();
                        String statisticIdPlayer = table_model.getValueAt(table.getSelectedRow(), 2).toString();
                        String statisticGoals = table_model.getValueAt(table.getSelectedRow(), 3).toString();
                        String statisticAssists = table_model.getValueAt(table.getSelectedRow(), 4).toString();
                        statisticIdGameTextField.setText(statisticIdGame);
                        statisticIdPlayerTextField.setText(statisticIdPlayer);
                        statisticGoalsTextField.setText(statisticGoals);
                        statisticAssistsTextField.setText(statisticAssists);
                        break;
                }
            }
        });

        addUpdateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int tabIndex = TabbedPanel.getSelectedIndex();
                int rowIndex = table.getSelectedRow();

                if (rowIndex == -1)
                {
                    switch (tabIndex)
                    {
                        case 0:
                            String teamName = teamNameTextField.getText();
                            String teamCoach = teamCoachTextField.getText();
                            String teamStadium = teamStadiumTextField.getText();
                            String teamPoints = teamPointsTextField.getText();
                            try
                            {
                                EntityAdder.AddTeam(teamName,teamCoach,teamStadium,teamPoints);
                                TbUpdater.UpdateTeam();
                            }
                            catch (MyErrors error)
                            {
                                JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 1:
                            String playerKitNumber = playerKitNumberTextField.getText();
                            String playerName = playerNameTextField.getText();
                            String playerLastName = playerLastNameTextField.getText();
                            String playerPosition = playerPositionTextField.getText();
                            String playerTeam = playerTeamTextField.getText();
                            try
                            {
                                EntityAdder.AddPlayer(playerKitNumber,playerName,playerLastName,playerPosition,playerTeam);
                                TbUpdater.UpdatePlayer();
                            }
                            catch (MyErrors error)
                            {
                                JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 2:
                            String gameDate = gameDateTextField.getText();
                            String gameHomeTeam = gameHomeTeamTextField.getText();
                            String gameAwayTeam = gameAwayTeamTextField.getText();
                            String gameStadium = gameStadiumTextField.getText();
                            String gameTicketPrice = gameTicketPriceTextField.getText();
                            String gameResult = gameResultTextField.getText();
                            try
                            {
                                EntityAdder.AddGame(gameDate,gameHomeTeam,gameAwayTeam,gameStadium,gameTicketPrice,gameResult);
                                TbUpdater.UpdateGame();
                            }
                            catch (MyErrors error)
                            {
                                JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 3:
                            String statisticIdGame = statisticIdGameTextField.getText();
                            String statisticIdPlayer = statisticIdPlayerTextField.getText();
                            String statisticGoals = statisticGoalsTextField.getText();
                            String statisticAssists = statisticAssistsTextField.getText();
                            try
                            {
                                EntityAdder.AddStatistic(statisticIdGame,statisticIdPlayer,statisticGoals,statisticAssists);
                                TbUpdater.UpdateStatistic();
                            }
                            catch (MyErrors error)
                            {
                                JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                }
                else
                {
                    EntityManager em = ourSessionFactory.createEntityManager();
                    switch (tabIndex)
                    {
                        case 0:
                            String teamName = teamNameTextField.getText();
                            String teamCoach = teamCoachTextField.getText();
                            String teamStadium = teamStadiumTextField.getText();
                            String teamPoints = teamPointsTextField.getText();
                            String teamNamePrev = table_model.getValueAt(table.getSelectedRow(), 0).toString();
                            em.getTransaction().begin();
                            List <TeamEntity> team_list = em.createQuery("SELECT t FROM TeamEntity t WHERE t.name = '" + teamNamePrev + "'").getResultList();
                            em.getTransaction().commit();
                            try
                            {
                                EntityUpdater.UpdateTeam(teamName,teamCoach,teamStadium,teamPoints,Integer.toString(team_list.get(0).getIdTeam()));
                                TbUpdater.UpdateTeam();
                            }
                            catch (MyErrors error)
                            {
                                JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 1:
                            String playerKitNumber = playerKitNumberTextField.getText();
                            String playerName = playerNameTextField.getText();
                            String playerLastName = playerLastNameTextField.getText();
                            String playerPosition = playerPositionTextField.getText();
                            String playerTeam = playerTeamTextField.getText();
                            try
                            {
                                EntityUpdater.UpdatePlayer(playerKitNumber,playerName,playerLastName,playerPosition,playerTeam,table_model.getValueAt(table.getSelectedRow(), 0).toString());
                                TbUpdater.UpdatePlayer();
                            }
                            catch (MyErrors error)
                            {
                                JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 2:
                            String gameDate = gameDateTextField.getText();
                            String gameHomeTeam = gameHomeTeamTextField.getText();
                            String gameAwayTeam = gameAwayTeamTextField.getText();
                            String gameStadium = gameStadiumTextField.getText();
                            String gameTicketPrice = gameTicketPriceTextField.getText();
                            String gameResult = gameResultTextField.getText();
                            try
                            {
                                EntityUpdater.UpdateGame(gameDate,gameHomeTeam,gameAwayTeam,gameStadium,gameTicketPrice,gameResult,table_model.getValueAt(table.getSelectedRow(), 0).toString());
                                TbUpdater.UpdateGame();
                            }
                            catch (MyErrors error)
                            {
                                JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case 3:
                            String statisticIdGame = statisticIdGameTextField.getText();
                            String statisticIdPlayer = statisticIdPlayerTextField.getText();
                            String statisticGoals = statisticGoalsTextField.getText();
                            String statisticAssists = statisticAssistsTextField.getText();
                            try
                            {
                                EntityUpdater.UpdateStatistic(statisticIdGame,statisticIdPlayer,statisticGoals,statisticAssists,table_model.getValueAt(table.getSelectedRow(), 0).toString());
                                TbUpdater.UpdateStatistic();
                            }
                            catch (MyErrors error)
                            {
                                JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                }
                table.setModel(table_model);
                CleanGUI();
            }
        });

        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int tabIndex = TabbedPanel.getSelectedIndex();
                int rowIndex = table.getSelectedRow();
                EntityManager em = ourSessionFactory.createEntityManager();

                switch (tabIndex)
                {
                    case 0:
                        String teamName = table_model.getValueAt(table.getSelectedRow(), 0).toString();
                        em.getTransaction().begin();
                        List <TeamEntity> team_list = em.createQuery("SELECT t FROM TeamEntity t WHERE t.name = '" + teamName + "'").getResultList();
                        em.remove(team_list.get(0));
                        em.getTransaction().commit();
                        TbUpdater.UpdateTeam();
                        break;
                    case 1:
                        em.getTransaction().begin();
                        PlayerEntity pe = em.find(PlayerEntity.class, Integer.parseInt(table.getValueAt(rowIndex, 0).toString()));
                        em.remove(pe);
                        em.getTransaction().commit();
                        TbUpdater.UpdatePlayer();
                        break;
                    case 2:
                        em.getTransaction().begin();
                        GameEntity ge = em.find(GameEntity.class, Integer.parseInt(table.getValueAt(rowIndex, 0).toString()));
                        em.remove(ge);
                        em.getTransaction().commit();
                        TbUpdater.UpdateGame();
                        break;
                    case 3:
                        em.getTransaction().begin();
                        StatisticEntity se = em.find(StatisticEntity.class, Integer.parseInt(table.getValueAt(rowIndex, 0).toString()));
                        em.remove(se);
                        em.getTransaction().commit();
                        TbUpdater.UpdateStatistic();
                        break;
                }
                table.setModel(table_model);
                CleanGUI();
            }
        });

        renewButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CleanGUI();
            }
        });

        playerOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String teamName = playerFindListTextField.getText();
                try
                {
                    TbUpdater.FindPlayersByTeam(teamName);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (MyErrors error)
                {
                    JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        calendarOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String teamName = gameFindCalendarTextField.getText();
                try
                {
                    TbUpdater.FindCalendarByTeam(teamName);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (MyErrors error)
                {
                    JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gamesHeldOkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String teamName = gameGamesHeldTextField.getText();
                try
                {
                    TbUpdater.FindGamesHeldByTeam(teamName);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (MyErrors error)
                {
                    JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        reportButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String teamName = statisticFindTotalStatisticsTextField.getText();
                try
                {
                    TbUpdater.MakeReport(teamName);
                    table.setModel(table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (MyErrors error)
                {
                    JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exportReportButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String teamName = statisticFindTotalStatisticsTextField.getText();
                try
                {
                    TbUpdater.MakeReport(teamName);
                    table.setModel(table_model);
                    ExportPdf.createPdf("Reports/" + teamName + ".pdf", new String[]{"Kit number", "Name", "Last Name", "Goals", "Assists"}, table_model);
                    countTextField.setText(Integer.toString(table.getRowCount()));
                }
                catch (MyErrors error)
                {
                    JOptionPane.showMessageDialog(MainPanel, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(MainPanel, "Cant export: " + exception.getClass() + ':' + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void exit()
    {
        dispose();
    }

    public static void main(String[] args)
    {
        GUI_main myGUI = new GUI_main();
        myGUI.setTitle("Football administrator");
        myGUI.setSize(1000, 500);
        myGUI.setLocation(220, 150);
        myGUI.setVisible(true);
    }
}