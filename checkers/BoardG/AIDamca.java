package BoardG;
import java.awt.Container;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.*;

public class AIDamca extends Thread implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	DBoard db;
	DT[][] dt;
	boolean turn,ne;
	int l=0;
	
	public AIDamca(DBoard dbo,boolean t) 
	{
		db=dbo;
		turn=t;
		dt=new DT[2][];
		if(t)
		{
			dt[0]=db.whiteS;
			dt[1]=db.blackS;
		}
		else
		{
			dt[1]=db.whiteS;
			dt[0]=db.blackS;
		}
		this.setEnabled(false);
	}

	public void move()
	{
		ne=db.needEat(dt[0], db.VirtualBo);
		if(ne)
			eat(dt[0].length);
		else
		{
			int[][] om;
			
			if(db.needEat(dt[1], db.VirtualBo))
			{
				boolean[] tbid;
				boolean mnd=false;
				int ind[]={0,0,dt[0].length},si;
				for(int i=0;i<dt[0].length;i++)
					if(!(dt[0][i].getLen()==0 && dt[0][i].getWid()==0))
					{
						om=dt[0][i].getOMove(db.VirtualBo);
						for(int j=0;j<dt[0][i].OpMove.length;j++)
							if(!(om[0][j]==0 && om[1][j]==0))
							{
								String[][] s=new String[db.VirtualBo.length][db.VirtualBo[0].length];
								for(int h=0;h<s.length;h++)
									s[h]=db.VirtualBo[h].clone();
								s[dt[0][i].getLen()][dt[0][i].getWid()]="empty";
								s[om[0][j]][om[1][j]]=dt[0][i].clr;
								
								si=0;
								tbid=db.getDaLo(dt[0], s);
								
								for(int h=0;h<tbid.length;h++)
									if(tbid[h])
										si++;
								if(si<ind[2])
								{
									ind[0]=i;
									ind[1]=j;
									ind[2]=si;
									mnd=true;
								}
							}
					}
				go(mnd,ind);
			}
			else
			{
				boolean f=false,sp[]=new boolean[dt[0].length];
				int ind[]=new int[2],lim;
				if(dt[0][0].clr.equals("white"))
					lim=db.sq.length;
				else
					lim=0;
				
				for(int i=0;i<sp.length;i++)
				{
					String[][] s=new String[db.VirtualBo.length][db.VirtualBo[0].length];
					for(int h=0;h<s.length;h++)
						s[h]=db.VirtualBo[h].clone();
					
					s[dt[0][i].getLen()][dt[0][i].getWid()]="empty";
					sp[i]=db.needEat(dt[1], s);
				}
				for(int i=0;i<dt[0].length;i++)
					if(!(dt[0][i].len==0 && dt[0][i].wid==0) && !sp[i] && !dt[0][i].getClass().getSimpleName().equals("DQ"))
					{
						om=dt[0][i].getOMove(db.VirtualBo);
						boolean[] dm=db.getDm(dt[0][i]);
						
						for(int j=0;j<dm.length;j++)
							if(!dm[j] && !(om[0][j]==0 && om[1][j]==0))
							{
								int k=dt[0][ind[0]].getOMove(db.VirtualBo)[1][ind[1]];
								if(f)
								{
									if(Math.abs(lim-om[1][j])>Math.abs(lim-k))
									{
										ind[0]=i;
										ind[1]=j;
									}
								}
								else
								{
									f=true;
									ind[0]=i;
									ind[1]=j;
								}
							}
					}
				for(int i=0;i<dt[0].length && !f;i++)
					if(!(dt[0][i].len==0 && dt[0][i].wid==0) && !sp[i] && dt[0][i].getClass().getSimpleName().equals("DQ"))
					{
						om=dt[0][i].getOMove(db.VirtualBo);
						boolean[] dm=db.getDm(dt[0][i]);
						
						for(int j=0;j<dm.length;j++)
							if(!dm[j] && !(om[0][j]==0 && om[1][j]==0))
							{
								if(f)
								{
									if(om[0][j]==0 || om[0][j]==db.sq.length || om[1][j]==0 || om[1][j]==db.sq[0].length)
									{
										ind[0]=i;
										ind[1]=j;
									}
								}
								else
								{
									f=true;
									ind[0]=i;
									ind[1]=j;
								}
							}
					}
				go(f,ind);
			}
		}
	}
	
	private int[] rMove()
	{
		int ind[]=null,lim;
		if(dt[0][0].clr.equals("white"))
			lim=db.sq.length;
		else
			lim=0;
		for(int i=0;i<dt[0].length && ind==null;i++)
			if(!(dt[0][i].len==0 && dt[0][i].wid==0))
			{
				int[][] om;
				boolean f=true;
				
				dt[0][i].canEat(db.VirtualBo);
				if(dt[0][i].ce)
					om=dt[0][i].getOEat(db.VirtualBo);
				else if(!ne)
					om=dt[0][i].getOMove(db.VirtualBo);
				else
				{
					om=null;
					f=false;
				}
				for(int j=0;f && j<om[0].length;j++)
					if(!(om[0][j]==0 && om[1][j]==0))
						if(ind!=null)
						{
							
							Container p1=dt[0][ind[0]].getParent(), p2=(Square) dt[0][i].getParent();
							if(Math.abs(p1.getX()-lim)<Math.abs(p2.getX()-lim))
							{
								ind=new int[2];
								ind[0]=i;
								ind[1]=j;
							}
						}
						else
						{
							
							ind=new int[2];
							ind[0]=i;
							ind[1]=j;
						}
			}
		return ind;
	}
	
	private void go(boolean f, int[] ind)
	{
		if(!f)
		{
			ind=rMove();
			if(ind!=null)
				go(true,ind);
			else
			{
				if(turn)
					db.numberW=0;
				else
					db.numberB=0;
				db.victory();
			}
		}
		else
		{
			JButton button=dt[0][ind[0]].OpMove[ind[1]];
			db.actionPerformed(new ActionEvent(dt[0][ind[0]], 0, dt[0][ind[0]].getActionCommand()));
			db.actionPerformed(new ActionEvent(button, 0, button.getActionCommand()));
			
			if(db.turn==turn && ne && dt[0][ind[0]].ce)
				eat(ind[0]);
		}
	}
	
	private void eat(int a)
	{
		boolean fl=false;
		int ind[]=new int[2];
		if(a==dt[0].length)
		{
			boolean m[]=db.getDaLo(dt[0], db.VirtualBo), f=false;
			for(int j=0;j<m.length && !f;j++)
				if(m[j] && dt[0][j].ce)
				{
					f=true;
					ind[0]=j;
				}
			if(f)
			{
				f=false;
				boolean[] gdm=db.getDm(dt[0][ind[0]]);
				int[][] oe=dt[0][ind[0]].getOEat(db.VirtualBo);
				
				for(int i=0;i<gdm.length && !f;i++)
					if(!gdm[i] && !(oe[0][i]==0 && oe[1][i]==0))
					{
						f=true;
						ind[1]=i;
					}
				if(f)
					go(f,ind);
				else
				{
					for(int i=0;i<oe[0].length && !f;i++)
						if(!(oe[0][i]==0 && oe[1][i]==0))
							ind[1]=i;
					go(true,ind);
				}
			}
			else
			{
				for(int i=0;i<dt[0].length && !f;i++)
					if(!(dt[0][i].len==0 && dt[0][i].wid==0) && dt[0][i].ce)
					{
						boolean[] dm=db.getDm(dt[0][i]);
						int[][] oe=dt[0][i].getOEat(db.VirtualBo);
						Square p1=db.sq[dt[0][i].len][ dt[0][i].wid],p2;
						
						for(int j=0;j<dm.length && !f;j++)
						{
							if(!dm[j] && !(oe[0][j]==0 && oe[1][j]==0))
							{
								p2=db.sq[oe[0][j]][oe[1][j]];
								Vector<int[]> edi=db.getLoEa(p2, p1, db.VirtualBo);
								for(int h=0;h<edi.size();h++)
									if(db.sq[edi.get(h)[0]][edi.get(h)[1]].getComponent(0).getClass().getSimpleName().equals("dq"))
									{
										f=true;
										ind[0]=i;
										ind[1]=j;
									}
								if(!f)
								{
									f=true;
									ind[0]=i;
									ind[1]=j;
								}
							}
						}
					}
				go(f,ind);
			}
		}
		else
		{
			ind[0]=a;
			boolean f=false;
			boolean[] dm=db.getDm(dt[0][a]);
			int[][] om=dt[0][a].getOEat(db.VirtualBo);
			for(int j=0;j<dm.length && !f;j++)
				if(!dm[j] && !(om[0][j]==0 && om[1][j]==0))
				{
					f=true;
					ind[1]=j;
				}
			if(f)
			{
				go(f,ind);
				fl=true;
			}
			else
			{
				for(int j=0;f && j<om[0].length;j++)
					if(!(om[0][j]==0 && om[1][j]==0))
					{
						f=true;
						ind[1]=j;
					}
			}
			if(!fl)
				go(f,ind);
		}
	}
	
	public void run()
	{
		while(db.numberB!=0 && db.numberW!=0)
		{
			while(db.turn==!turn)
			{
				try {sleep(500);} 
				catch (Exception e) {e.printStackTrace();}
			}
			try
			{
				sleep(500);
				if(db.numberB!=0 && db.numberW!=0)
					this.move();
			} 
			catch (Exception e) {e.printStackTrace();}
		}
	}

	public void setEnabled(boolean b)
	{
		for(int i=0;i<dt[0].length;i++)
			dt[0][i].setEnabled(b);
	}
}