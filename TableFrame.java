import javax.swing.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


public class TableFrame extends JFrame implements ActionListener
{
	private JTable theTable;
	private Vector scoreData;
	private UnoFrame u;

	public TableFrame(Vector<Object> theNames, UnoFrame u)
	{
		super("whyyyyyy");
		this.u = u;
		Vector totals = new Vector();
		Vector second = new Vector();
		
		scoreData = new Vector();
		int i = 0;
		for (Object n : theNames)
		{
			System.out.println(n);
			second.add(i);
			totals.add(0);
			i++;
		}
		scoreData.add(totals);
		scoreData.add(second);
		theTable = new JTable(scoreData, theNames);
		theTable.setPreferredScrollableViewportSize(new Dimension(200,100));

		JScrollPane sp = new JScrollPane(theTable);
		Container cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		Container cp1 = new Container();
		Container cp2 = new Container();

		cp1.add(sp,BorderLayout.CENTER);
		JButton continueButton = new JButton("Continue");
		continueButton.addActionListener(this);
		cp2.add(continueButton);

		cp.add(cp1);
		cp.add(cp2);
	}

	public void addData(ArrayList<Player> playerList)
	{
		//remove previous total
		scoreData.remove(scoreData.size() - 1);

		Vector newestRow = new Vector();//score from most recent round
		Vector totalRow = new Vector();//total score
		for (Player p : playerList)
		{
			System.out.println(p);
			System.out.println(p.getScore(p.getScoreSize() - 1));
			System.out.println(p.getTotal());
			newestRow.add(p.getScore(p.getScoreSize() - 1));
			totalRow.add(p.getTotal());
		}

		scoreData.add(newestRow);
		scoreData.add(totalRow);
		theTable.revalidate();
	}

	 public void actionPerformed(ActionEvent e)
    {
        u.restart();
        dispose();
    }
}