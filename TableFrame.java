import javax.swing.*;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


public class TableFrame extends JFrame
{
	private JTable theTable;
	private Vector scoreData;
	private UnoFrame u;
	private JPanel topPanel; //this is the main panel

	public TableFrame(Vector<Object> theNames, UnoFrame u)
	{	
		super("Scores");
		topPanel = new JPanel();
		
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
		getContentPane().add(topPanel);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		topPanel.add(sp,BorderLayout.CENTER);
		
	}

	public void addData(ArrayList<Player> playerList)
	{
		Vector newestRow = new Vector();//score from most recent round
		Vector totalRow = new Vector();//total score

		//remove previous total
		scoreData.remove(scoreData.size() - 1);

		for (Player p : playerList)
		{
			newestRow.add(p.getScore(p.getScoreSize() - 1));
			totalRow.add(p.getTotal());
		}

		scoreData.add(newestRow);
		scoreData.add(totalRow);
		theTable.revalidate();
	}

}