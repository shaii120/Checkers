package BoardG;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class UI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	Board board;
	AIDamca ai;
	int x,y;
	
	public UI(Board b) 
	{
		x=b.sq.length;
		y=b.sq[0].length;
		board=b;
		init_bar();
		
		setBackground(Color.BLACK);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		add(BorderLayout.CENTER, board);
		setSize(board.getWidth(), board.getHeight()+58);
	}
	
	private void init_bar()
	{
		MenuBar jmb=new MenuBar();
		Menu op=new Menu("Options"),fi=new Menu("File"),ai=new Menu("AI Options");
		MenuItem[][] mimi=
			{
				{new MenuItem("Change back"),new MenuItem("Tools can move"),new MenuItem("Dangerous move"),new MenuItem("Tools in dangerous")},
				{new MenuItem("New"),new MenuItem("Open"),new MenuItem("Save"),new MenuItem("Save as")},
				{new MenuItem("Add AI"),new MenuItem("Remove AI")}
			};
		
		for(MenuItem m:mimi[0])
		{
			m.addActionListener(board);
			op.add(m);
		}
		for(MenuItem m:mimi[1])
		{
			m.addActionListener(this);
			fi.add(m);
		}
		for(MenuItem m:mimi[2])
		{
			m.addActionListener(this);
			ai.add(m);
		}
		
		jmb.add(fi);
		jmb.add(op);
		jmb.add(ai);
		this.setMenuBar(jmb);
	}
	
	public void processWindowEvent(WindowEvent e)
	{
		if(e.getID()==WindowEvent.WINDOW_CLOSING)
			System.exit(0);
	}
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e)
	{
		String gac=e.getActionCommand();
		if(gac.equals("Save"))
			save();
		else if(gac.equals("Save as"))
			save_as();
		else if(gac.equals("Open"))
			open();
		else if(gac.equals("New"))
		{
			ai=null;
			this.remove(board);
			board=new DBoard(x,y);
			init_bar();
			add(board, BorderLayout.CENTER);
		}
		else if(gac.equals("Add AI") && ai==null)
		{
			ai=new AIDamca((DBoard) board,board.turn);
			ai.start();
		}
		else if(gac.equals("Remove AI"))
		{
			ai.setEnabled(true);
			ai.stop();
			ai=null;
		}
		setVisible(true);
	}
	
	private void svf(String s)
	{
		try
		{
			FileOutputStream fos=new FileOutputStream(s);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(board);
			oos.writeObject(ai);
			oos.flush();
			oos.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void save()
	{
		if(board.cfn==null)
			save_as();
		else
			svf(board.cfn);
	}
	
	@SuppressWarnings("deprecation")
	private void save_as() 
	{
		FileDialog fd=new FileDialog(this, "שמור מצב",FileDialog.SAVE);
		fd.setFile("דמקה.da");
		fd.show();
		if(fd.getFile()!=null)
		{
			board.cfn=fd.getDirectory()+fd.getFile();
			svf(board.cfn);
		}
	}

	@SuppressWarnings("deprecation")
	private void open()
	{
		FileDialog fd=new FileDialog(this, "שמור מצב",FileDialog.LOAD);
		fd.show();
		
		if(fd.getFile()!=null)
		{
			board.cfn=fd.getDirectory()+fd.getFile();
			try
			{
				FileInputStream fis=new FileInputStream(board.cfn);
				ObjectInputStream ois=new ObjectInputStream(fis);
				
				remove(board);
				board=(Board) ois.readObject();
				ai=(AIDamca) ois.readObject();
				ois.close();
				
				if(ai!=null)
					ai.start();
				
				this.add(board, BorderLayout.CENTER);
				init_bar();
			}
			catch(Exception e){e.printStackTrace();}
		}
	}
	
	public static void main(String[] args)
	{
		UI u=new UI(new DBoard(8,8));
		u.setVisible(true);
	}
}