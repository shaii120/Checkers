package BoardG;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DBoard extends Board
{
	private static final long serialVersionUID = 1L;
	
	DT dt;
	DT blackS[],whiteS[];

	public DBoard(int len, int wid) 
	{
		super(len,wid);
		dt=null;
		init_soli();
	}

	private void init_soli() 
	{
		int co=0;
		blackS=new DT[3*Math.round(sq.length/2)];
		whiteS=new DT[3*Math.round(sq.length/2)];
		ImageIcon iRed=new ImageIcon("red.gif"),iWhite=new ImageIcon("white.gif");
		numberB=3*Math.round(sq.length/2);
		numberW=3*Math.round(sq.length/2);
		
		for(int j=0;co<blackS.length && j<sq.length;j++)
			for(int i=0;i<sq[j].length;i++)
			{
				if((i+j)%2==1)
				{
					int len=sq[j].length-i-1,wid=sq.length-j-1;
					
					blackS[co]=new DS(iRed,co,i,j,"black");
					blackS[co].addActionListener(this);
					blackS[co].setBackground(Color.black);
					blackS[co].setActionCommand("black");
					
					whiteS[co]=new DS(iWhite,co,len,wid,"white");
					whiteS[co].addActionListener(this);
					whiteS[co].setBackground(Color.black);
					whiteS[co].setActionCommand("white");
					
					init_mo(blackS[co]);
					init_mo(whiteS[co]);
					VirtualBo[i][j]="black";
					VirtualBo[len][wid]="white";
					
					sq[i][j].add(blackS[co]);
					sq[len][wid].add(whiteS[co]);
					
					co++;
				}
			}
	}

	public boolean needEat(DT[] h, String[][] s)
	{
		boolean ne=false;
		
		for(int i=0;i<h.length;i++)
		{
				h[i].canEat(s);
				if(h[i].ce)
					ne=true;
		}
		return ne;
	}
	
	private boolean Eating(Square p2, Square p1)
	{
		boolean f=false;
		Vector<int[]> vlo=this.getLoEa(p2, p1, VirtualBo);
		
		for(int i=0;i<vlo.size();i++)
		{
			int[] lo=vlo.get(i);
			int x=lo[0],y=lo[1];
			
			if(!sq[x][y].isEmpty())
			{
				DT t=(DT)(sq[x][y].getComponent(0));
				t.moveT(0, 0);
				if(t.clr.equals("black"))
					numberB--;
				else if(t.clr.equals("white"))
					numberW--;
				sq[x][y].removeAll();
				sq[x][y].repaint();
				VirtualBo[x][y]="empty";
				f=true;
			}
		}
		return f;
	}
	
	public Vector<int[]> getLoEa(Square p2, Square p1,String[][] s)
	{
		Vector<int[]> edi=new Vector<int[]>();
		int limit=Math.abs(p1.getX()-p2.getX());		
		int goX=(p1.getX()-p2.getX())/Math.abs(p1.getX()-p2.getX());
		int goY=(p1.getY()-p2.getY())/Math.abs(p1.getY()-p2.getY());
				
		for(int i=0;i<limit;i++)
		{
			int x=p2.getX()+i*goX,y=p2.getY()+i*goY;
			if(!s[x][y].equals("empty"))
			{
				int[] h={x,y};
				edi.add(h);
			}
		}
		return edi;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		String gac=e.getActionCommand();
		if(dt!=null)
			for(JButton b:dt.OpMove)
				b.setBackground(Color.blue);
		if((dt==null || (dt==e.getSource() && !dt.cm)) && ((gac.equals("white") && turn) || (gac.equals("black") && !turn)))
		{
			dt=(DT)e.getSource();
			boolean ne;
			if(turn)
				ne=needEat(whiteS, VirtualBo);
			else
				ne=needEat(blackS, VirtualBo);
			if(dt.cm && ne && dt.ce)
			{
				dt.cm=false;
				int [][] m=dt.getOEat(VirtualBo);
				
				for(int i=0;i<m[0].length;i++)
				{
					int len=m[0][i],wid=m[1][i];
					if(!(len==0 && wid==0))
					{
						sq[len][wid].add(dt.OpMove[i]);
						sq[len][wid].repaint();
					}	
				}
			}
			else if(dt.cm && !ne)
			{
				int [][] m=dt.getOMove(VirtualBo);
				
				for(int i=0;i<m[0].length;i++)
				{
					int len=m[0][i],wid=m[1][i];
					if(!(len==0 && wid==0))
					{
						dt.cm=false;
						sq[len][wid].add(dt.OpMove[i]);
						sq[len][wid].repaint();
					}
				}
				if(dt.cm)
					dt=null;
			}
			
			else
			{
				dt.cm=true;
				int [][] m;
				if(dt.ce)
					m=dt.getOEat(VirtualBo);
				else
					m=dt.getOMove(VirtualBo);;
				
				for(int i=0;i<m[0].length;i++)
				{
					int len=m[0][i],wid=m[1][i];
					if(!(len==0 && wid==0) && (len<sq.length && wid<sq[0].length))
					{
						sq[len][wid].removeAll();
						sq[len][wid].repaint();
					}
				}
				dt=null;				
			}
		}
		
		else if(gac.equals("IsEat"))
		{
			chBa();
			dt.cm=true;
			int [][] m;
			if(dt.ce)
				m=dt.getOEat(VirtualBo);
			else
				m=dt.getOMove(VirtualBo);
			boolean f1=false,f2=false;
			turn=(!turn);
			Square p1=(Square)((Component)e.getSource()).getParent(), p2=(Square)dt.getParent();
			
			VirtualBo[p2.getX()][p2.getY()]="empty";
			VirtualBo[p1.getX()][p1.getY()]=dt.clr;
			dt.moveT(p1.getX(), p1.getY());
			
			p2.removeAll();
			p2.repaint();
			p1.removeAll();
			p1.repaint();
			for(int i=0;i<m[0].length;i++)
			{
				sq[m[0][i]][m[1][i]].removeAll();
				sq[m[0][i]][m[1][i]].repaint();
			}
			p1.add(dt);
			
			if(Math.abs(p1.getX()-p2.getX())>1)
			{
				f1=Eating(p2,p1);
				if(f1)
				{
					m=dt.getOEat(VirtualBo);
					for(int i=0;i<m[0].length;i++)
					{
						int len=m[0][i],wid=m[1][i];
						
						if(!(len==0 && wid==0))
						{
							f2=true;
							turn=(dt.clr.equals("white"));
							sq[len][wid].add(dt.OpMove[i]);
							sq[len][wid].repaint();
						}
					}
				}
			}
			if(!f2)
			{
				if(dt.wid==0 || dt.wid==sq[dt.len].length-1)
					crown(p1,p2);
				dt=null;
			}
		}
		
		else if(gac.equals("Tools can move"))
		{
			boolean[] hcm=getTcm();
			DT[] d;
			if(turn)
				d=this.whiteS;
			else
				d=this.blackS;
			for(int i=0;i<hcm.length;i++)
				if(hcm[i])
					d[i].setBackground(Color.green);
					
		}
		else if(gac.equals("Dangerous move"))
		{			
			if(dt instanceof DT)
			{
				boolean[] d=getDm(dt);
				
				for(int i=0;i<d.length;i++)
					if(d[i])
					{
						dt.OpMove[i].setBackground(Color.red);
						dt.OpMove[i].getParent().repaint();
					}
			}
		}
		else if(gac.equals("Tools in dangerous"))
		{
			DT[] d;
			if(turn)
				d=this.whiteS;
			else
				d=this.blackS;
			boolean[] tid=getDaLo(d, VirtualBo);
			
			for(int i=0;i<tid.length;i++)
				if(tid[i])
				{
					d[i].setBackground(Color.red);
				}	
		}
		else if(gac.equals("Change back"))
			chBa();
		
		if((numberB==0 || numberW==0))
			victory();
		
		Container c=getParent();
		while(c.getParent()!=null)
			c=c.getParent();
		c.setVisible(true);
	}
	
	private void crown(Square p1, Square p2)
	{
		ImageIcon ii=new ImageIcon(dt.clr+"Queen.gif");
		DQ dq=new DQ(ii,dt.numT,dt.len,dt.wid,2*Math.min(sq.length,sq[0].length)-1,dt.clr);
		dq.setEnabled(dt.isEnabled());
		
		if(dt.wid==0 && dt.clr.equals("white"))
		{
			p1.removeAll();
			whiteS[dt.numT]=dq;
			whiteS[dt.numT].addActionListener(this);
			whiteS[dt.numT].setBackground(Color.black);
			whiteS[dt.numT].setActionCommand("white");
			init_mo(whiteS[dt.numT]);
			p1.add(whiteS[dt.numT]);
		}
		else if(dt.wid==7 && dt.clr.equals("black"))
		{
			p1.removeAll();
			blackS[dt.numT]=dq;
			blackS[dt.numT].addActionListener(this);
			blackS[dt.numT].setBackground(Color.black);
			blackS[dt.numT].setActionCommand("black");
			init_mo(blackS[dt.numT]);
			p1.add(blackS[dt.numT]);
		}
	}
	
	public boolean[] getTcm()
	{
		boolean ne;
		if(turn)
			ne=needEat(whiteS, VirtualBo);
		else
			ne=needEat(blackS, VirtualBo);
		boolean[] hcm=new boolean[blackS.length];
		
		if(dt==null)
			if(turn)
				if(ne)
					for(int i=0;i<whiteS.length;i++)
						hcm[i]=whiteS[i].ce;
				else
					for(int i=0;i<whiteS.length;i++)
					{
						int[][] cm=whiteS[i].getOMove(VirtualBo);
						
						if(!(whiteS[i].len==0 && whiteS[i].wid==0))
							for(int j=0;j<cm[0].length;j++)
								if(!(cm[0][j]==0 && cm[1][j]==0))
									hcm[i]=true;
					}
			else
				if(ne)
					for(int i=0;i<blackS.length;i++)
						hcm[i]=blackS[i].ce;
				else
					for(int i=0;i<blackS.length;i++)
					{
						int[][] cm=blackS[i].getOMove(VirtualBo);
						
						if(!(blackS[i].len==0 && blackS[i].wid==0))
							for(int j=0;j<cm[0].length;j++)
								if(!(cm[0][j]==0 && cm[1][j]==0))
									hcm[i]=true;
					}
		return hcm;
	}
	
	public boolean[] getDm(DT d)
	{
		boolean[] dm=null;
		
		if(d!=null)
		{
			dm=new boolean[d.OpMove.length];
			int len=d.len,wid=d.wid, move[][];
			String[][] s=new String[VirtualBo.length][];
			for(int i=0;i<s.length;i++)
				s[i]=this.VirtualBo[i].clone();
			
			if(d.ce)
				move=d.getOEat(VirtualBo);
			else
				move=d.getOMove(VirtualBo);

			for(int i=0;i<move[0].length;i++)
			{
				if(!(move[0][i]==0 && move[1][i]==0))
				{
					s[d.len][d.wid]="empty";
					d.moveT(move[0][i], move[1][i]);
					
					if(d.ce)
					{
						Vector<int[]> h=getLoEa(sq[move[0][i]][move[1][i]],sq[len][wid],s);
						for(int j=0;j<h.size();j++)
							s[h.get(j)[0]][h.get(j)[1]]="empty";
						d.moveT(move[0][i], move[1][i]);
						s[d.len][d.wid]=d.clr;
						d.canEat(s);
					}
					
					if(!d.ce)
					{
						s[d.len][d.wid]=d.clr;
						dm[i]=daLo(d,s);
					}
				}
			}
			d.moveT(len, wid);
			d.canEat(VirtualBo);
		}
		return dm;
	}
	
	public boolean[] getDaLo(DT[] h, String[][] s)
	{
		boolean[] da=new boolean[whiteS.length];
		
		for(int i=0;i<da.length;i++)
			if(!(h[i].len==0 && h[i].wid==0))
				da[i]=daLo(h[i],s);
		
		return da;
	}
	
	private void chBa()
	{
		DT[] d;
		if(turn)
			d=this.whiteS;
		else
			d=this.blackS;
		for(DT i:d)
			i.setBackground(Color.black);
	}
	
	private boolean daLo(DT d, String[][] s)
	{
		int plpl,mimi,plmi,mipl;
		plpl=Math.min(s.length-d.len, s[0].length-d.wid);
		mipl=Math.min(s[0].length-d.wid, d.len+1);
		mimi=Math.min(d.len,d.wid)+1;
		plmi=Math.min(d.wid+1,s.length-d.len);
				
		boolean f=true;
		for(int i=1;i<plpl && f;i++)
			if(!s[d.len+i][d.wid+i].equals("empty"))
			{
				if(!s[d.len+i][d.wid+i].equals(d.clr) && sq[d.len+i][d.wid+i].getComponent(0) instanceof DT)
				{
					DT dt2=(DT) sq[d.len+i][d.wid+i].getComponent(0);
					dt2.canEat(s);
					if(dt2.ce);
					{
						int oe[][]=dt2.getOEat(s), fail[]={0,0};
						Vector<int[]> h=new Vector<int[]>();
						for(int j=0;j<oe[0].length;j++)
						{
							if(!(oe[0][j]==0 && oe[1][j]==0))
								h=getLoEa(sq[oe[0][j]][oe[1][j]],sq[dt2.len][dt2.wid],s);
							else
								h.add(fail);
							for(int k=0;k<h.size();k++)
								if(h.get(0)[0]==d.len && h.get(0)[1]==d.wid)
									return true;
						}
					}
				}
				f=false;
			}
		f=true;
		for(int i=1;i<mimi && f;i++)
			if(!s[d.len-i][d.wid-i].equals("empty"))
			{
				if(!s[d.len-i][d.wid-i].equals(d.clr) && sq[d.len-i][d.wid-i].getComponent(0) instanceof DT)
				{
					DT dt2=(DT) sq[d.len-i][d.wid-i].getComponent(0);
					dt2.canEat(s);
					if(dt2.ce);
					{
						int oe[][]=dt2.getOEat(s), fail[]={0,0};
						Vector<int[]> h=new Vector<int[]>();
						for(int j=0;j<oe[0].length;j++)
						{
							if(!(oe[0][j]==0 && oe[1][j]==0))
								h=getLoEa(sq[oe[0][j]][oe[1][j]],sq[dt2.len][dt2.wid],s);
							else
								h.add(fail);
							for(int k=0;k<h.size();k++)
								if(h.get(0)[0]==d.len && h.get(0)[1]==d.wid)
									return true;
						}
					}
				}
				f=false;
			}
		f=true;
		for(int i=1;i<plmi && f;i++)
			if(!s[d.len+i][d.wid-i].equals("empty"))
			{
				if(!s[d.len+i][d.wid-i].equals(d.clr) && sq[d.len+i][d.wid-i].getComponent(0) instanceof DT)
				{
					DT dt2=(DT) sq[d.len+i][d.wid-i].getComponent(0);
					dt2.canEat(s);
					if(dt2.ce);
					{
						int oe[][]=dt2.getOEat(s), fail[]={0,0};
						Vector<int[]> h=new Vector<int[]>();
						for(int j=0;j<oe[0].length;j++)
						{
							if(!(oe[0][j]==0 && oe[1][j]==0))
								h=getLoEa(sq[oe[0][j]][oe[1][j]],sq[dt2.len][dt2.wid],s);
							else
								h.add(fail);
							for(int k=0;k<h.size();k++)
								if(h.get(0)[0]==d.len && h.get(0)[1]==d.wid)
									return true;
						}
					}
				}
				f=false;
			}
		f=true;
		for(int i=1;i<mipl && f;i++)
			if(!s[d.len-i][d.wid+i].equals("empty"))
			{
				if(!s[d.len-i][d.wid+i].equals(d.clr) && sq[d.len-i][d.wid+i].getComponent(0) instanceof DT)
				{
					DT dt2=(DT) sq[d.len-i][d.wid+i].getComponent(0);
					dt2.canEat(s);
					if(dt2.ce);
					{
						int oe[][]=dt2.getOEat(s), fail[]={0,0};
						Vector<int[]> h=new Vector<int[]>();
						for(int j=0;j<oe[0].length;j++)
						{
							if(!(oe[0][j]==0 && oe[1][j]==0))
								h=getLoEa(sq[oe[0][j]][oe[1][j]],sq[dt2.len][dt2.wid],s);
							else
								h.add(fail);
							for(int k=0;k<h.size();k++)
								if(h.get(0)[0]==d.len && h.get(0)[1]==d.wid)
									return true;
						}
					}
				}
				f=false;
			}
		
		return false;
	}
}