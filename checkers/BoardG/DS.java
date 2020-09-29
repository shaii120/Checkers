package BoardG;
import javax.swing.*;


public class DS extends DT
{
	private static final long serialVersionUID = 1L;

	public DS(ImageIcon s,int nm,int i, int j,String clr) {super(s,nm,i,j,4,clr);}
	
	public int[][] getOMove(String[][] m) 
	{
		int[][] re=new int[2][4];
		
		if(clr.equals("white"))
		{
			if((len+1<m.length && wid-1>=0) && m[len+1][wid-1].equals("empty"))
			{
				re[0][0]=len+1;
				re[1][0]=wid-1;
			}
			else if((len+2<m.length && wid-2>=0) && m[len+1][wid-1].equals("black") && m[len+2][wid-2].equals("empty"))
			{
				re[0][0]=len+2;
				re[1][0]=wid-2;
			}
			
			if((len-1>=0 && wid-1>=0) && m[len-1][wid-1].equals("empty"))
			{
				re[0][1]=len-1;
				re[1][1]=wid-1;
			}
			else if((len-2>=0 && wid-2>=0) && m[len-1][wid-1].equals("black") && m[len-2][wid-2].equals("empty"))
			{
				re[0][1]=len-2;
				re[1][1]=wid-2;
			}
		}
		else
		{
			if((len-1>=0 && wid+1<m[0].length) && m[len-1][wid+1].equals("empty"))
			{
				re[0][0]=len-1;
				re[1][0]=wid+1;
			}
			else if((len-2>=0 && wid+2<m[0].length) && m[len-1][wid+1].equals("white") && m[len-2][wid+2].equals("empty"))
			{
				re[0][0]=len-2;
				re[1][0]=wid+2;
			}
			
			if((len+1<m.length && wid+1<m[0].length) && m[len+1][wid+1].equals("empty"))
			{
				re[0][1]=len+1;
				re[1][1]=wid+1;
			}
			else if((len+2<m.length && wid+2<m[0].length) && m[len+1][wid+1].equals("white") && m[len+2][wid+2].equals("empty"))
			{
				re[0][1]=len+2;
				re[1][1]=wid+2;
			}
		}
		
		return re;
	}
	
	public int[][] getOEat(String[][] m)
	{
		int[][] re=new int[2][4];
		
		if(clr.equals("black"))
		{
			if((len+2<m.length && wid-2>=0) && m[len+1][wid-1].equals("white") && m[len+2][wid-2].equals("empty"))
			{
				re[0][0]=len+2;
				re[1][0]=wid-2;
			}
			if((len-2>=0 && wid-2>=0) && m[len-1][wid-1].equals("white") && m[len-2][wid-2].equals("empty"))
			{
				re[0][1]=len-2;
				re[1][1]=wid-2;
			}
			if((len-2>=0 && wid+2<m[0].length) && m[len-1][wid+1].equals("white") && m[len-2][wid+2].equals("empty"))
			{
				re[0][2]=len-2;
				re[1][2]=wid+2;
			}
			if((len+2<m.length && wid+2<m[0].length) && m[len+1][wid+1].equals("white") && m[len+2][wid+2].equals("empty"))
			{
				re[0][3]=len+2;
				re[1][3]=wid+2;
			}
		}
		else if(clr.equals("white"))
		{
			if((len+2<m.length && wid-2>=0) && m[len+1][wid-1].equals("black") && m[len+2][wid-2].equals("empty"))
			{
				re[0][0]=len+2;
				re[1][0]=wid-2;
			}
			if((len-2>=0 && wid-2>=0) && m[len-1][wid-1].equals("black") && m[len-2][wid-2].equals("empty"))
			{
				re[0][1]=len-2;
				re[1][1]=wid-2;
			}
			if((len-2>=0 && wid+2<m[0].length) && m[len-1][wid+1].equals("black") && m[len-2][wid+2].equals("empty"))
			{
				re[0][2]=len-2;
				re[1][2]=wid+2;
			}
			if((len+2<m.length && wid+2<m[0].length) && m[len+1][wid+1].equals("black") && m[len+2][wid+2].equals("empty"))
			{
				re[0][3]=len+2;
				re[1][3]=wid+2;
			}
		}
				
		return re;
	}
	
	public void canEat(String[][] m)
	{
		ce=false;
		if(clr.equals("black"))
		{
			if((len+2<m.length && wid-2>=0) && m[len+1][wid-1].equals("white") && m[len+2][wid-2].equals("empty"))
				ce=true;
			if((len-2>=0 && wid-2>=0) && m[len-1][wid-1].equals("white") && m[len-2][wid-2].equals("empty"))
				ce=true;
			if((len-2>=0 && wid+2<m[0].length) && m[len-1][wid+1].equals("white") && m[len-2][wid+2].equals("empty"))
				ce=true;
			if((len+2<m.length && wid+2<m[0].length) && m[len+1][wid+1].equals("white") && m[len+2][wid+2].equals("empty"))
				ce=true;
		}
		else if(clr.equals("white"))
		{
			if((len+2<m.length && wid-2>=0) && m[len+1][wid-1].equals("black") && m[len+2][wid-2].equals("empty"))
				ce=true;
			if((len-2>=0 && wid-2>=0) && m[len-1][wid-1].equals("black") && m[len-2][wid-2].equals("empty"))
				ce=true;
			if((len-2>=0 && wid+2<m[0].length) && m[len-1][wid+1].equals("black") && m[len-2][wid+2].equals("empty"))
				ce=true;
			if((len+2<m.length && wid+2<m[0].length) && m[len+1][wid+1].equals("black") && m[len+2][wid+2].equals("empty"))
				ce=true;
		}
	}
}
