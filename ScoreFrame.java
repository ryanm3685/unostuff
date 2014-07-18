import javax.swing.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.*;
import java.awt.Dimension;
import java.util.ArrayList;

public class ScoreFrame extends JFrame implements ActionListener
{
    JLabel [] nameLabel;
    JLabel [] scoreLabel;
    JLabel [] totalLabel;
    JButton continueButton = new JButton("Continue");
    UnoFrame u;
    
    
    public ScoreFrame(ArrayList<Player> playerList, UnoFrame u)
    {
        super("Scores");
        this.u = u;
        
        continueButton.addActionListener(this);
        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        
        Container namesContainer = new Container();
        Container scoresContainer = new Container();
        Container totalContainer = new Container();

        
        namesContainer.setLayout(new BoxLayout(namesContainer, BoxLayout.X_AXIS));
        scoresContainer.setLayout(new BoxLayout(scoresContainer, BoxLayout.X_AXIS));
        totalContainer.setLayout(new BoxLayout(totalContainer, BoxLayout.X_AXIS));

        c.add(namesContainer);
        
        c.add(scoresContainer);
        c.add(totalContainer);
        
        nameLabel = new JLabel[playerList.size()];
        scoreLabel = new JLabel[playerList.size()];
        totalLabel = new JLabel[playerList.size()];
        
        //add data to the labels and add those labels to the containers
        for (int i = 0; i < playerList.size(); i++)
        {
            nameLabel[i] = new JLabel(playerList.get(i).getName());
            scoreLabel[i] = new JLabel(playerList.get(i).printScores());
            totalLabel[i] = new JLabel(playerList.get(i).getTotal() + "");
            
            namesContainer.add(nameLabel[i]);
            scoresContainer.add(scoreLabel[i]);
            totalContainer.add(totalLabel[i]);
            c.add(Box.createRigidArea(new Dimension(200, 0)));
            c.add(Box.createHorizontalGlue());
        }
        c.add(continueButton);
        pack();

    }
    
    public void actionPerformed(ActionEvent e)
    {
        u.restart();
        dispose();
    }
}