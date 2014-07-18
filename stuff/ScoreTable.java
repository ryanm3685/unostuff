import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import javax.swing.*;

public class ScoreTable extends JFrame
{
    //ArrayList<Player> playerList;
    public ScoreTable(ArrayList<Player> playerList)
    {
        super("Scores");
        String [] names = new String[playerList.size()];
        Object [][] scores;
        
        ArrayList<String> namesList = new ArrayList<String>();
        for (Player p : playerList) names[playerList.indexOf(p)] = p.getName();
        //System.out.println("a");
        
        scores = new Object[names.length][playerList.get(0).getScoreSize()];
        //System.out.println("b");

        for (int i = 0; i < names.length; i++)
        {
            System.out.println(playerList.get(i).getName() + "'s scores");
            for (int j = 0; j < playerList.get(0).getScoreSize(); j++)
            {
                scores[i][j] = playerList.get(i).getScore(j);
                System.out.println(scores[i][j]);
            }
        }
        
        //System.out.println("d");
        JTable table = new JTable(scores, names);
        //System.out.println("e");
        Container c = getContentPane();
        //System.out.println("f");
        JScrollPane scrollPane = new JScrollPane(table);
        System.out.println("a");
        table.setFillsViewportHeight(true);
        System.out.println("b");

        c.add(scrollPane);
        System.out.println("c");
        pack();
        System.out.println("d");
    }
}