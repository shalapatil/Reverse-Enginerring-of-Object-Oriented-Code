package activity;

import extraction.JavaGrammer;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class functionCallHandler implements ActionListener, ItemListener,
		MouseListener {

	activityBlock frame;

	public functionCallHandler(activityBlock a) {

		this.frame = a;
	}

	public void mouseClicked(MouseEvent evt) {
		// TODO Auto-generated method stub
		if (evt.getSource() == frame.funCall) {
			if (evt.getClickCount() == 2) {
				String temp = (String) frame.funCall.getSelectedItem();
				System.out.println(temp);
				String[] ar = temp.split(":");
				int i = 0;
				while (i < ar.length) {
					ar[i] = ar[i].replaceAll(" ", "");
					ar[i] = ar[i].replace("()", "");
					i++;

				}
				createCalledActivityBlock(ar[0], ar[1]);

			}
		}

	}

	public void createCalledActivityBlock(String className, String funName) {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> l1 = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = DriverManager
					.getConnection("jdbc:sqlite:/home/shala/workspace/reverse_Enginnering/project_db");
			Statement statement = connection.createStatement();
			Statement statement1 = connection.createStatement();
			Statement statement3 = connection.createStatement();

			ResultSet rs = statement
					.executeQuery("select * from mapping where s_class='"
							+ className + "' and s_fun='" + funName + "'");

			while (rs.next()) {
				System.out.println("inside while..");

				int flag2 = 0;
				for (int i = 0; i < JavaGrammer.ignoreMethod.length; i++) {

					if (rs.getString("s_fun").equals(
							JavaGrammer.ignoreMethod[i])) {
						flag2 = 1;

					}

				}
				if (flag2 == 1) {
					continue;
				}

				String s = rs.getString("d_fun");
				String s1 = rs.getString("d_class");
				s = s1 + " : " + s + "()";
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
			ResultSet rs3 = statement3
					.executeQuery("select * from classes where class_name='"
							+ className + "'");
			if (rs3.next()) {

			}

			ResultSet rs1 = statement1
					.executeQuery("select * from mapping where s_class='"
							+ className + "'");

			while (rs1.next()) {

				String g = rs1.getString("s_fun");

				for (int i = 0; i < JavaGrammer.listenerMethod.length; i++) {

					if (g.equals(JavaGrammer.listenerMethod[i])) {

						String s = rs1.getString("d_fun");
						String s1 = rs1.getString("d_class");
						s = s1 + " : " + s + "()";
						int flag = 0;
						for (int i1 = 0; i1 < l1.size(); i1++) {
							if (s.equals(l1.get(i1))) {
								flag = 1;

							}

						}
						if (flag == 0) {

							l1.add(s);

						}

					}
				}

			}

			rs1.close();
			statement1.close();

			List l = new List(l1.size());

			for (int i = 0; i < l1.size(); i++) {
				l.add(l1.get(i));
				System.out.println(l1.get(i));

			}

			int id1 = frame.id;

			System.out.println("Id is " + id1);
			int x = id1 * 250;
			int y = editor.col_arr[id1 + 1] * 250;
			editor.col_arr[id1 + 1]++;
			activityBlock b = new activityBlock(className, l, x, y, id1 + 1);

			

			b.setVisible(true);
			editor.jdpDesktop.add(b);
			try {
				b.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
