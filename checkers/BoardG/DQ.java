package BoardG;
import javax.swing.ImageIcon;


public class DQ extends DT
{
	private static final long serialVersionUID = 1L;
	
	int plpl,mimi,plmi,mipl;
	public DQ(ImageIcon s,int co,int i, int j,int om,String clr) {super(s,co,i,j,om,clr);}
	
	private void NumMove(int length, int width)
	{
		plpl=Math.min(length-len, width-wid);
		mipl=Math.min(width-wid, len+1);
		mimi=Math.min(len,wid)+1;
		plmi=Math.min(wid+1,length-len);
	}
	
	public int[][] getOMove(String[][] m) 
	{
		int ind=0;
		int[][] re=new int[2][this.OpMove.length];
		boolean f=true;
		NumMove(m.length, m[0].length);
		
		for(int i=1;i<plpl && f;i++)
		{
			if(m[len+i][wid+i].equals(clr))
				f=false;
			else if(m[len+i][wid+i].equals("empty"))
			{
				re[0][ind]=len+i;
				re[1][ind]=wid+i;
			}
			else if(i+1<plpl)
				while(++i<plpl && f)
				{
					if(m[len+i][wid+i].equals("empty"))
					{
						re[0][ind]=len+i;
						re[1][ind]=wid+i;
						ind++;
					}
					else
						f=false;
				}
			ind++;
		}
		
		f=true;
		for(int i=1;i<mimi && f;i++)
		{
			if(m[len-i][wid-i].equals(clr))
				f=false;
			else if(m[len-i][wid-i].equals("empty"))
			{
				re[0][ind]=len-i;
				re[1][ind]=wid-i;
			}
			else if(i+1<mimi)
				while(++i<mimi && f)
				{
					if(m[len-i][wid-i].equals("empty"))
					{
						re[0][ind]=len-i;
						re[1][ind]=wid-i;
						ind++;
					}
					else
						f=false;
				}
			ind++;
		}
		
		f=true;
		for(int i=1;i<plmi && f;i++)
		{
			if(m[len+i][wid-i].equals(clr))
				f=false;
			else if(m[len+i][wid-i].equals("empty"))
			{
				re[0][ind]=len+i;
				re[1][ind]=wid-i;
			}
			else if(i+1<plmi)
				while(++i<plmi && f)
				{
					if(m[len+i][wid-i].equals("empty"))
					{
						re[0][ind]=len+i;
						re[1][ind]=wid-i;
						ind++;
					}
					else
						f=false;
				}
			ind++;
		}
		
		f=true;
		for(int i=1;i<mipl && f;i++)
		{
			if(m[len-i][wid+i].equals(clr))
				f=false;
			else if(m[len-i][wid+i].equals("empty"))
			{
				re[0][ind]=len-i;
				re[1][ind]=wid+i;
			}
			else if(i+1<mipl)
				while(++i<mipl && f)
				{
					if(m[len-i][wid+i].equals("empty"))
					{
						re[0][ind]=len-i;
						re[1][ind]=wid+i;
						ind++;
					}
					else
						f=false;
				}
			ind++;
		}
		
		return re;
	}

	public int[][] getOEat(String[][] m)
	{
		int ind=0;
		int[][] re=new int[2][this.OpMove.length];
		boolean f=true;
		NumMove(m.length, m[0].length);
		
		for(int i=1;i<plpl && f;i++)
		{
			if(m[len+i][wid+i].equals(clr))
				f=false;
			else if(!m[len+i][wid+i].equals("empty"))
				if(i+1<plpl)
				{
					while(++i<plpl && f)
					{ 
						if(m[len+i][wid+i].equals("empty"))
						{
							re[0][ind]=len+i;
							re[1][ind++]=wid+i;
						}
						else
							f=false;
					}
					f=true;
				}
		}
		
		f=true;
		for(int i=1;i<mimi && f;i++)
		{
			if(m[len-i][wid-i].equals(clr))
				f=false;
			else if(!m[len-i][wid-i].equals("empty"))
				if(i+1<mimi)
				{
					while(++i<mimi && f)
					{
						if(m[len-i][wid-i].equals("empty"))
						{
							ind++;
							re[0][ind]=len-i;
							re[1][ind++]=wid-i;
						}
						else
							f=false;
					}
					f=true;
				}
		}
		
		f=true;
		for(int i=1;i<plmi && f;i++)
		{
			if(m[len+i][wid-i].equals(clr))
				f=false;
			else if(!m[len+i][wid-i].equals("empty"))
				if(i+1<plmi)
				{
					while(++i<plmi && f)
					{
						if(m[len+i][wid-i].equals("empty"))
						{
							re[0][ind]=len+i;
							re[1][ind++]=wid-i;
						}
						else
							f=false;
					}
					f=true;
				}
		}
		
		f=true;
		for(int i=1;i<mipl && f;i++)
		{
			if(m[len-i][wid+i].equals(clr))
				f=false;
			else if(!m[len-i][wid+i].equals("empty"))
				if(i+1<mipl)
				{
					while(++i<mipl && f)
					{
						if(m[len-i][wid+i].equals("empty"))
						{
							ind++;
							re[0][ind]=len-i;
							re[1][ind++]=wid+i;
						}
						else
							f=false;
					}
					f=true;
				}
		}
		
		return re;
	}
	
	public void canEat(String[][] m)
	{
		boolean f=true;
		ce=false;
		NumMove(m.length, m[0].length);
		
		for(int i=1;i<plpl && f;i++)
		{
			if(m[len+i][wid+i].equals(clr))
				f=false;
			else if(!m[len+i][wid+i].equals("empty"))
				if(i+1<plpl)
				{
					while(++i<plpl && f)
						if(m[len+i][wid+i].equals("empty"))
							ce=true;
						else
							f=false;
					f=true;
				}
		}
		
		f=true;
		for(int i=1;i<mimi && f;i++)
		{
			if(m[len-i][wid-i].equals(clr))
				f=false;
			else if(!m[len-i][wid-i].equals("empty"))
				if(i+1<mimi)
				{
					while(++i<mimi && f)
						if(m[len-i][wid-i].equals("empty"))
							ce=true;
						else
							f=false;
					f=true;
				}
		}
		
		f=true;
		for(int i=1;i<plmi && f;i++)
		{
			if(m[len+i][wid-i].equals(clr))
				f=false;
			else if(!m[len+i][wid-i].equals("empty"))
				if(i+1<plmi)
				{
					while(++i<plmi && f)
						if(m[len+i][wid-i].equals("empty"))
							ce=true;
						else
							f=false;
					f=true;
				}
		}
		
		f=true;
		for(int i=1;i<mipl && f;i++)
		{
			if(m[len-i][wid+i].equals(clr))
				f=false;
			else if(!m[len-i][wid+i].equals("empty"))
				if(i+1<mipl)
				{
					while(++i<mipl && f)
						if(m[len-i][wid+i].equals("empty"))
							ce=true;
						else
							f=false;
					f=true;
				}
		}
	}
	
}
