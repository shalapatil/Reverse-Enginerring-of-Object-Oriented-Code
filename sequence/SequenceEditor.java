package sequence;

import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import Extraction.JavaGrammer;

//import acitivity.activityBlock;

public class SequenceEditor {
	int case_ = 0;
	private String start_class = null;
	public String source = null, target = null;

	public SequenceEditor() throws ClassNotFoundException {

	}

	public void decideCaseForActivity(Graphics g) {

		int classid = 0;
		String classname = null;
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
					.executeQuery("select * from Classes where parent='Applet'");

			if (rs.next()) {
				case_ = 1;

				classid = rs.getInt(1);
				classname = rs.getString("class_name");
				start_class = classname;
				source = start_class;
				target = start_class;
				System.out.println("Class Id=" + classid + "\nClassName="
						+ classname);

			}
			rs.close();
		}

		catch (Exception e) {
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
		if (case_ == 1)
			handleCase1(g);
		if (case_ == 0) // check for other case
		{

		}

	}

	private void handleCase1(Graphics g) {
		// TODO Auto-generated method stub
		ArrayList<callDetails> l1 = new ArrayList<callDetails>();
		callDetails c = new callDetails();
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
					.executeQuery("select * from mapping where s_class='"
							+ start_class + "'");

			while (rs.next()) {
				// System.out.println("inside while..");

				String s = rs.getString("s_fun");
				// System.out.println(" "+s);

				// s = start_class + " : " + s + "()";
				int flag = 0;
				for (int i = 0; i < l1.size(); i++) {
					if (s.equals(l1.get(i).funName)) {
						flag = 1;
					}

				}

				if (flag == 0) {
					c = new callDetails();
					c.className = start_class;
					c.funName = s;
					l1.add(c);

				}

			}
			rs.close();
			statement.close();

			for (int i = 0; i < l1.size(); i++) {
				// System.out.println("nooo....");
				System.out.println("hii...");
				processCall(l1.get(i),g);

			}

			System.out.println("I am back here.....");

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

	private void processCall(callDetails c,Graphics g) {
		// TODO Auto-generated method stu
		//System.out.println("\nWorking for " + c.className + " : " + c.funName);
		String message, temp_source;
		message = c.funName;
	
		drawArrow(message,g);

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<callDetails> l1 = new ArrayList<callDetails>();

		Connection connection = null;
		try {
			connection = DriverManager
					.getConnection("jdbc:sqlite:project_db");
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("select * from mapping where s_class='"
							+ c.className + "' and s_fun='" + c.funName + "'");
			while (rs.next()) {
				callDetails temp = new callDetails();
				temp.className = rs.getString("d_class");
				temp.funName = rs.getString("d_fun");
				l1.add(temp);

			}
			rs.close();

			if (c.className.equals(c.funName)) {
				rs = statement
						.executeQuery("select * from mapping where s_class='"
								+ c.className + "'");
				while (rs.next()) {
					callDetails temp = new callDetails();
					String s_fun=rs.getString("s_fun");
					temp.className = rs.getString("d_class");
					temp.funName = rs.getString("d_fun");

					boolean addToList = checkWetherMethodIsInBuilt(s_fun);
					
					if(addToList)
					l1.add(temp);

				}
				rs.close();
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

		temp_source = target;
		for (int i = 0; i < l1.size(); i++) {

			source = temp_source;
			target = l1.get(i).className;
			processCall(l1.get(i),g);

		}

	}

	private boolean checkWetherMethodIsInBuilt(String funName) {
		// TODO Auto-generated method stub
		
		
		for (int i = 0; i < extraction.JavaGrammer.listenerMethod.length; i++) {

			if (funName.equals(extraction.JavaGrammer.listenerMethod[i])) {
				return true;				
			}
		}
		return false;
}

	private void drawArrow(String message,Graphics g) {
		// TODO Auto-generated method stub
		
	System.out.println(" " + source + "  (" + message + ")   " + target);
	DrawPanel.drawLine(g,source,target,message);
	//DrawSequence.drawLine(g,source,target,message);
	//new DrawSequence();
    
	}

}
