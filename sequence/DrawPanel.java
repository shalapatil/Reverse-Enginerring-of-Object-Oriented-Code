package sequence;

import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;

public class DrawPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int flag=1;
	static int incr=0;
	static String drawn[]=new String[20];
	static int val1[]=new int[10];
	static int count=0;

	static int no_classes;
	
	public DrawPanel(){
		System.out.println("inside init");
		try {
			get_total_no_of_classes();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	static public void drawLine(Graphics g,String s,String t,String m)
	{
		int x1,x2,i;
		 int y1=70,y2=70;
		for( i=0;i<count;i++)
		{
			if(s.equals(drawn[i])){
				break;
			}
			else{
				
			}
				
		}
		x1=val1[i];
		for( i=0;i<count;i++)
		{
			if(t.equals(drawn[i])){
				break;
			}
			else{
				
			}
				
		}
		x2=val1[i];
		
		g.drawLine(x1, y1+(incr*20), x2, y2+(incr*20));
		
		if(x1<x2){
			g.drawLine(x2-10,(y2+(incr*20))-7 , x2,y2+(incr*20));
			g.drawLine(x2-10,(y2+(incr*20))+7 , x2,y2+(incr*20));
			
			g.drawString(m, x1+5,y1+(incr*20));	
		}
		else{
			g.drawLine(x2+10,(y2+(incr*20))-7 , x2,y2+(incr*20));
			g.drawLine(x2+10,(y2+(incr*20))+7 , x2,y2+(incr*20));
			
			g.drawString(m, x2+10,y2+(incr*20));	
			
		}
		
		
		incr++;
		
	}
	
	
	public void drawlayout(Graphics g){
		
		int x1=10,y1=15,x2=125,y2=50;
		int temp;
		
		
		for(int i=0;i<count;i++){
		
		g.drawRect((x1+i*135),y1,x2,y2);
		temp=((x1+i*135)+125)-65;
		val1[i]=temp;
		
		g.drawString(drawn[i], (x1+i*135)+12,y1+25 );
		
/*   CODE  TO DRAW DOTTED LINE
 * 
 */
	/*	Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(drawingStroke);
		Line2D line1= new Line2D.Double(temp,65, temp,700);
		g2d.draw(line1);*/
	
		
		g.drawLine(temp,65, temp,400);
		
	
			
			
		}
	}
	public void paintComponent(Graphics g){
		
		incr=0;
		System.out.println("value="+incr);
		drawlayout(g);
		SequenceEditor ed = null;
		try {
			ed = new SequenceEditor();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ed.decideCaseForActivity(g);
		
		
	}
	
	
	
	 public void drawParameters(String source,String message,String target){
		
		 System.out.println(" " + source + "  (" + message + ")   " + target);
		 
		// for(int i=0;i<20)
		 }
	 
	 void get_total_no_of_classes() throws ClassNotFoundException {
		 
		 System.out.println("inside getno()");
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Connection connection = null;
			try {
				connection = DriverManager
						.getConnection("jdbc:sqlite:project_db");
				Statement statement = connection.createStatement();

				ResultSet rs = statement
						.executeQuery("select class_name from classes ");
				int i=0;
				

				while (rs.next()) {
                    drawn[i]=rs.getString("class_name");
					i++;
					count=i;
				}
				rs.close();
				statement.close();
				
				for(int j=0;j<count;j++){
					System.out.println("Classes Extracted are:"+drawn[j]);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR:");
				e.printStackTrace();
			} finally {
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					// connection close failed.
					System.err.println(e);
				}
			}

		}

}
