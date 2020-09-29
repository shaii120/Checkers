package BoardG;
import java.awt.Component;
import javax.swing.JPanel;


public class Square extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	final int x,y;
	private boolean empty;
	
	public Square(int x,int y)
	{
		this.x=x;
		this.y=y;
		empty=true;
	}
	public int getX(){return x;}
	public int getY(){return y;}
	public Component add(Component arg0)
	{
		empty=false;
		return super.add(arg0);
	}
	public void removeAll()
	{
		empty=true;
		super.removeAll();
	}
	public boolean isEmpty(){return empty;}
}
