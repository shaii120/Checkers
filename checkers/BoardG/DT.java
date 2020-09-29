package BoardG;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class DT  extends Tool
{
	private static final long serialVersionUID = 1L;
	
	boolean ce=false;
	
	public DT(ImageIcon s,int co,int i, int j,int om,String clr) {super(s,co,i,j,om,clr);}
	
	public void copy(Tool h)
	{
		this.setIcon(h.getIcon());
		moveT(h.len,h.wid);
		OpMove=new JButton[h.OpMove.length];
		for(int n=0;n<OpMove.length;n++)
		{
			OpMove[n]=new JButton();
			OpMove[n].setBackground(Color.blue);
		}
	}
	
	abstract public int[][] getOEat(String[][] m);
	abstract public void canEat(String[][] m);
}
