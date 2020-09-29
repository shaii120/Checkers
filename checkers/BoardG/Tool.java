package BoardG;
import java.awt.Color;

import javax.swing.*;

public abstract class Tool extends JButton
{
	private static final long serialVersionUID = 1L;
	
	protected int len,wid,numT;
	JButton[] OpMove;
	boolean cm=true;
	final String clr;
	
	public Tool(ImageIcon ii,int nm,int i, int j, int om,String s) 
	{
		super(ii);
		numT=nm;
		len=i;
		wid=j;
		clr=s;
		
		OpMove=new JButton[om];
		for(int n=0;n<om;n++)
		{
			OpMove[n]=new JButton();
			OpMove[n].setBackground(Color.blue);
		}
	}
	
	public int getLen(){return len;}
	public int getWid(){return wid;}
	public void moveT(int i, int j)
	{
		len=i;
		wid=j;
	}
	
	abstract public int[][] getOMove(String m[][]);
}
