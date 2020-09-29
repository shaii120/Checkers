package BoardG;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;


public abstract class Board extends Panel implements ActionListener,Serializable
{
	private static final long serialVersionUID = 1L;
	
	Square[][] sq;
	String[][] VirtualBo;
	String cfn=null; 
	boolean turn;
	int numberB,numberW;
	
	public Board(int len, int wid)
	{
		turn=true;
		
		setSize(len*50,wid*50);
		init_square(len,wid);
		init_VB(len,wid);		
	}
	
	protected void init_mo(Tool pion)
	{
		for(JButton jb:pion.OpMove)
		{
			for(ActionListener al:jb.getActionListeners())
				jb.removeActionListener(al);
			jb.addActionListener((ActionListener) this);
			jb.setActionCommand("IsEat");
		}
	}
	
	private void init_VB(int len, int wid)
	{
		VirtualBo=new String[len][wid];
		for(int i=0;i<len;i++)
			for(int j=0;j<wid;j++)
				VirtualBo[i][j]="empty";
	}
	
	private void init_square(int len,int wid)
	{
		sq=new Square[len][wid];
		Color bl=Color.BLACK, w=Color.WHITE;
		
		setLayout(new GridLayout(len,wid));
		
		for(int j=0;j<len;j++)
			for(int i=0;i<wid;i++)
			{
				sq[i][j]=new Square(i,j);
				sq[i][j].setLayout(new BorderLayout());
				sq[i][j].setSize(50, 50);
				if((j+i)%2==0)
					sq[i][j].setBackground(w);
				else
					sq[i][j].setBackground(bl);
				add(sq[i][j]);
			}
		}
	
	protected void victory() 
	{
		this.removeAll();
		repaint();
		if(numberB==0)
			add(new JLabel("White Win"),BorderLayout.CENTER);
		else if(numberW==0)
			add(new JLabel("Black Win"),BorderLayout.CENTER);
		getParent().getParent().getParent().getParent().setVisible(true);
	}
}