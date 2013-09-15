package activity;

import java.awt.Dimension;
import java.awt.List;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

class ScrollDesktop extends JDesktopPane implements Scrollable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	public int getScrollableUnitIncrement(Rectangle r, int axis, int dir) {
		return 50;
	}

	public int getScrollableBlockIncrement(Rectangle r, int axis, int dir) {
		return 200;
	}

	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
}

public class editor extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String start_class = null;
	int case_ = 0;
	
	int case_2=0;
	public static int screen_width = 10000;
	public static int screen_height = 10000;
	public static int col_arr[] = new int[1000];

	static JDesktopPane jdpDesktop;

	public editor() throws ClassNotFoundException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jdpDesktop = new ScrollDesktop();
		jdpDesktop.setPreferredSize(new Dimension(screen_width, screen_height));
		getContentPane().add(new JScrollPane(jdpDesktop), "Center");
		decideCaseForActivity();

		adjustDatabase();

		pack();
		setSize(3000, 3000);
		setVisible(true);
	}

	void decideCaseForActivity() {

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
					.getConnection("jdbc:sqlite:/home/shala/workspace/reverse_Enginnering/project_db");
			Statement statement = connection.createStatement();
			ResultSet rs = statement
					.executeQuery("select * from Classes where parent='Applet'");

			if (rs.next()) {
				case_ = 1;
				classid = rs.getInt(1);
				classname = rs.getString("class_name");
				start_class = classname;

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
			handleCase1();
		if (case_ == 0) // check for other case
		{

		}

	}

	void handleCase1() {
		// 1st constr
		// activityBlock b=new activityBlock(start_class);
		ArrayList<String> l1 = new ArrayList<String>();
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection connection = null;
		try {
			connection = DriverManager
					.getConnection("jdbc:sqlite:/home/shala/workspace/reverse_Enginnering/project_db");
			Statement statement = connection.createStatement();

			ResultSet rs = statement
					.executeQuery("select * from mapping where s_class='"
							+ start_class + "'");

			while (rs.next()) {
				System.out.println("inseide while..");

				String s = rs.getString("s_fun");
				s = start_class + " : " + s + "()";
				int flag = 0;
				for (int i = 0; i < l1.size(); i++) {
					if (s.equals(l1.get(i))) {
						flag = 1;

					}

				}

				if (flag == 0) {

					l1.add(s);

				}

			}
			rs.close();
			statement.close();
			List l = new List(l1.size());

			for (int i = 0; i < l1.size(); i++) {
				l.add(l1.get(i));
				System.out.println(l1.get(i));

			}

			activityBlock b = new activityBlock(start_class, l, 0, 0, 1);

		

			b.setVisible(true);
			jdpDesktop.add(b);
			try {
				b.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {
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

	void adjustDatabase() throws ClassNotFoundException {

		Class.forName("org.sqlite.JDBC");
		Vector<Integer> mapid = new Vector<Integer>();

		Connection connection = null;
		try {

			connection = DriverManager.getConnection("jdbc:sqlite:project_db");

			Statement statement = connection.createStatement();
			Statement statement1 = connection.createStatement();
			Statement statement2 = connection.createStatement();

			ResultSet rs1 = statement1.executeQuery("select * from mapping ");
			Boolean flag = false;

			while (rs1.next()) {
				flag = false;
				String cname = rs1.getString("d_class");

				ResultSet rs2 = statement2
						.executeQuery("select * from classes where class_name='"
								+ cname + "'");

				if (rs2.next()) {
					flag = true;

				}

				if (!flag) {
					mapid.add(new Integer(rs1.getInt("map_id")));

				}
				rs2.close();
			}
			rs1.close();
			statement.close();
			statement1.close();
			statement2.close();

			for (int i = 0; i < mapid.size(); i++) {
				statement.executeUpdate("delete  from mapping where map_id="
						+ mapid.get(i).intValue());

			}

		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
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

	public static void main(String arg[]) throws ClassNotFoundException {
		new editor();
	}
}
