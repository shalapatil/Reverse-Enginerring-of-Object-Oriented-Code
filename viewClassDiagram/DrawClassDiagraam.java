package viewClassDiagram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;

public class DrawClassDiagraam extends JPanel implements ActionListener,classMeasurements{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int no_classes;
	Frame f;
	classPattern classPattern;
	int x = startClassX, y = startClassY;
	Panel p;
	int count = 0;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
public DrawClassDiagraam(){
	setLayout(null);
	try {
		get_total_no_of_classes();
		drawClassDiagram();
	} catch (ClassNotFoundException e) {
		
		e.printStackTrace();
	}
	}
void drawAssociation(Graphics g) {

	try {
		Class.forName("org.sqlite.JDBC");
	} catch (ClassNotFoundException e1) {
	
		e1.printStackTrace();
	}

	Connection connection = null;
	try {
		connection = DriverManager
				.getConnection("jdbc:sqlite:project_db");
		Statement statement = connection.createStatement();
		Statement statement1 = connection.createStatement();
		Statement statement2 = connection.createStatement();

		int id = 0;
		ResultSet rs = statement.executeQuery("select * from classes");

		while (rs.next()) {
			id = rs.getInt(1);
			ResultSet rs1 = statement1
					.executeQuery("select * from variable where class_id="
							+ id);

			while (rs1.next()) {
				String class_object = rs1.getString("type");
				ResultSet rs2 = statement2
						.executeQuery("select * from classes");

				while (rs2.next()) {

					if (rs2.getString("class_name").equals(class_object)) {
						int class_obj_id = rs2.getInt("class_id");
						drawLine(id, class_obj_id, g, false,false);
	
						break;
					}

				}

				rs2.close();
				statement2.close();

			}
			rs1.close();
			statement1.close();

		}
		rs.close();
		statement.close();

	

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("ERROR:");
		e.printStackTrace();
	} finally {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
	
			System.err.println(e);
		}
	}
}

void drawLine(int id, int class_obj_id, Graphics g,
		Boolean generalizationFlag, Boolean dependancyFlag) {

	

	int id_diff = Math.abs(id - class_obj_id);
	System.out.println("id_diff " + id_diff);

	Boolean case5Flag = false;

	int id1 = 0, id2 = 0;

	switch (id_diff) {

	case 2:
	
		modifyLineColor(g);
		case2(id, class_obj_id, g, generalizationFlag, dependancyFlag);
		break;

	case 3:
		modifyLineColor(g);
		case3(id, class_obj_id, g, generalizationFlag, dependancyFlag);
		break;

	case 1:
		id1 = id;
		id2 = class_obj_id;
		modifyLineColor(g);
		case1(id1, id2, g, generalizationFlag, dependancyFlag);
		break;

	case 5:

		id1 = id;
		id2 = class_obj_id;
		modifyLineColor(g);
		case5Flag = case5(class_obj_id, g, case5Flag, id1, id2,
				generalizationFlag, dependancyFlag);

	default:
	

		id1 = id;
		id2 = class_obj_id;

		if (case5Flag)
			break;
		System.out.println("for diff " + id_diff);

		defaultCase(g, id_diff, id1, id2, generalizationFlag,
				dependancyFlag);

	}
}

private void defaultCase(Graphics g, int id_diff, int id1, int id2,
		Boolean generalizationFlag, Boolean dependancyFlag) {
	int x1;
	int y1;
	int x2;
	int y2;
	int x3, x4, x5, x6, x7, x8, y3, y4, y5, y6, y7, y8;
	x3 = x4 = x5 = x6 = x7 = x8 = y3 = y4 = y5 = y6 = y7 = y8 = 0;
	System.out.println("id1 " + id1 + "id2" + id2);
	int right, tot_col = 0;
	right = 0;
	tot_col = no_classes / 2;
	int class1_col = tot_col - (id1 / 2);
	int class2_col = ((id2 + 1) / 2);

	right = tot_col - class1_col + tot_col - class2_col;

	if (id1 % 2 == 0 && id2 % 2 == 1) 
	{
		

		if (true)
		{
			int shortLine = (id1 / 2) * smallLineDist;

			x1 = (((id1 + 1) / 2) - 1) * (classWidth + horizontalSpace)
					+ startClassX + classWidth / 2;
			y1 = startClassY + 2 * classHeight + 3 * verticalSpace;

			x2 = x1;
			y2 = y1 + shortLine;

			x3 = startClassX - shortLine;
			y3 = y2;

			
			x6 = startClassX + (((id2 + 1) / 2) * (classWidth))
					+ ((((id2 + 1) / 2) - 1) * (horizontalSpace))
					- classWidth / 2;
			
			y6 = startClassY;

			x5 = x6;
			y5 = y6 - shortLine;

			x4 = x3;
			y4 = y5;
			modifyLineColor(g);
			g.drawLine(x1, y1, x2, y2);
			g.drawLine(x2, y2, x3, y3);
			g.drawLine(x3, y3, x4, y4);
			g.drawLine(x4, y4, x5, y5);
			g.drawLine(x5, y5, x6, y6);
			if (generalizationFlag) {
				x7 = x6 + 10;
				y7 = y6 - 6;
				x8 = x6 - 10;
				y8 = y6 - 6;
				g.drawLine(x6, y6, x7, y7);
				g.drawLine(x6, y6, x8, y8);
				g.drawLine(x8, y8, x7, y7);
			}if (dependancyFlag) {
				x7 = x6 + 10;
				y7 = y6 - 6;
				x8 = x6 - 10;
				y8 = y6 - 6;
				g.drawLine(x6, y6, x7, y7);
				g.drawLine(x6, y6, x8, y8);
			
			}

		}

	} else if (id1 % 2 == 0 && id2 % 2 == 0)// bottom to bottom
	{
		int shortLine = (id1 / 2) * smallLineDist + 2;

		x1 = (((id1 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2 + shortLine;
		y1 = startClassY + 2 * classHeight + 3 * verticalSpace;

		x2 = x1;
		y2 = y1 + shortLine;

		x4 = (((id2 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2 + shortLine;
		y4 = startClassY + 2 * classHeight + 3 * verticalSpace;

		x3 = x4;
		y3 = y4 + shortLine;
		modifyLineColor(g);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x3, y3);
		g.drawLine(x3, y3, x4, y4);

		
		if (generalizationFlag) {
			x7 = x4 + 10;
			y7 = y4 + 6;
			x8 = x4 - 10;
			y8 = y4 + 6;
			g.drawLine(x4, y4, x7, y7);
			g.drawLine(x4, y4, x8, y8);
			g.drawLine(x8, y8, x7, y7);
		}if (dependancyFlag) {
			x7 = x4 + 10;
			y7 = y4 + 6;
			x8 = x4 - 10;
			y8 = y4 + 6;
			g.drawLine(x4, y4, x7, y7);
			g.drawLine(x4, y4, x8, y8);
		
		}
	}

	else if (id1 % 2 == 1 && id2 % 2 == 1)
	{
		int shortLine = (id1 / 2) * smallLineDist + 2;

		x1 = (((id1 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2 + shortLine;
		y1 = startClassY;

		x2 = x1;
		y2 = y1 - shortLine - 2;

		x4 = (((id2 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2 + shortLine;
		y4 = startClassY;

		x3 = x4;
		y3 = y2;
		modifyLineColor(g);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x3, y3);
		g.drawLine(x3, y3, x4, y4);


		if (generalizationFlag) {
			x7 = x4 + 10;
			y7 = y4 - 6;
			x8 = x4 - 10;
			y8 = y4 - 6;
			g.drawLine(x4, y4, x7, y7);
			g.drawLine(x4, y4, x8, y8);
			g.drawLine(x8, y8, x7, y7);
		}
		if (dependancyFlag) {
			x7 = x4 + 10;
			y7 = y4 - 6;
			x8 = x4 - 10;
			y8 = y4 - 6;
			g.drawLine(x4, y4, x7, y7);
			g.drawLine(x4, y4, x8, y8);
			g.drawLine(x8, y8, x7, y7);
		}

	} else 
	{

		int shortLine = (id1 / 2) * smallLineDist;
		shortLine += 5;
		x1 = (((id2 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		y1 = startClassY + 2 * classHeight + 3 * verticalSpace;

		x2 = x1;
		y2 = y1 + shortLine;

		x3 = startClassX - shortLine;
		y3 = y2;

		
		x6 = (((id1 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		
		y6 = startClassY;

		x5 = x6;
		y5 = y6 - shortLine;

		x4 = x3;
		y4 = y5;

		modifyLineColor(g);
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y2, x3, y3);
		g.drawLine(x3, y3, x4, y4);
		g.drawLine(x4, y4, x5, y5);
		g.drawLine(x5, y5, x6, y6);

		if (generalizationFlag) {
			x7 = x1 + 10;
			y7 = y1 + 6;
			x8 = x1 - 10;
			y8 = y1 + 6;
			g.drawLine(x1, y1, x7, y7);
			g.drawLine(x1, y1, x8, y8);
			g.drawLine(x8, y8, x7, y7);
		}
		
		if (dependancyFlag) {
			x7 = x1 + 10;
			y7 = y1 + 6;
			x8 = x1 - 10;
			y8 = y1 + 6;
			g.drawLine(x1, y1, x7, y7);
			g.drawLine(x1, y1, x8, y8);
			//g.drawLine(x8, y8, x7, y7);
		}

	}

}

private void modifyLineColor(Graphics g) {
	long r1 = 0;
	long b1 = 0;
	long g1 = 0;
	r1 = Math.round(Math.random()) % 255;
	b1 = Math.round(Math.random()) % 255;
	g1 = Math.round(Math.random()) % 255;

	
	Color c = new Color(r1, g1, b1);

	
	g.setXORMode(c);
}

private void changeLinePattern(Graphics g, boolean dotted, float width) {

	Graphics2D g2d = (Graphics2D) g;

	if (dotted)
		g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 1f, new float[] { 2f }, 0f));
	else
		g2d.setStroke(new BasicStroke(width));
}

private Boolean case5(int class_obj_id, Graphics g, Boolean case5Flag,
		int id1, int id2, Boolean generalizationFlag, Boolean dependancyFlag) {
	int x1;
	int y1;
	int x2;
	int y2;
	int temp = 0;
	System.out.println("id1 " + id1 + " id2 " + id2 + "class_obj_id"
			+ class_obj_id);

	if (id1 > class_obj_id) {
		temp = id1;
		id1 = id2;
		id2 = temp;

	}

	if (id1 % 2 == 1) {

		case5Flag = true;
		x1 = (((id1 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		y1 = startClassY + classHeight;

		x2 = x1 + 2 * (classWidth + horizontalSpace);
		y2 = y1 + 3 * verticalSpace;

		g.drawLine(x1, y1, x2, y2);
		System.out.println("diff=5 (1st class odd)-> x1 " + x1 + " y1 "
				+ y1 + " x2 " + x2 + " y2 " + y2);
		int x3 = 0, y3 = 0;
		if (generalizationFlag) {

			if (temp == 0) {
				x1 = x2;
				y1 = y2 - 7;
				x3 = x2 - 20;
				y3 = y2 - 2;
				g.drawLine(x2, y2, x1, y1);
				g.drawLine(x3, y3, x1, y1);
				g.drawLine(x2, y2, x3, y3);
	

			} else {
				x2 = x1 + 20;
				y2 = y1 + 10;
				x3 = x1 + 20;
				y3 = y1 - 5;

				g.drawLine(x2, y2, x1, y1);
				g.drawLine(x3, y3, x1, y1);
				g.drawLine(x2, y2, x3, y3);
	

			}
		}

		if (dependancyFlag) {

			if (temp == 0) {
				x1 = x2;
				y1 = y2 - 7;
				x3 = x2 - 20;
				y3 = y2 - 2;
				g.drawLine(x2, y2, x1, y1);
			
				g.drawLine(x2, y2, x3, y3);
			

			} else {
				x2 = x1 + 20;
				y2 = y1 + 10;
				x3 = x1 + 20;
				y3 = y1 - 5;

				g.drawLine(x2, y2, x1, y1);
				g.drawLine(x3, y3, x1, y1);
			

			}
		}

	}
	return case5Flag;
}

private void case1(int id1, int id2, Graphics g,
		Boolean generalizationFlag, Boolean dependancyFlag) {
	int x1;
	int y1;
	int x2;
	int y2;
	int class1_col = (id1 + 1) / 2;
	int class2_col = ((id2 + 1) / 2);
	

	if (id1 % 2 == 1)
	{
		x1 = (((id1 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		y1 = startClassY + classHeight;

		x2 = (((id2 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		;
		y2 = y1 + 3 * verticalSpace;

		g.drawLine(x1, y1, x2, y2);
		System.out.println("diff=1 (odd)-> x1 " + x1 + " y1 " + y1 + " x2 "
				+ x2 + " y2 " + y2);

		if (generalizationFlag) {
			x1 = x2 + 10;
			y1 = y2 - 10;
			int x3 = x2 - 10;
			int y3 = y1;

			if (class1_col != class2_col) {
				

				
				y1 = y2 - 5;
				x3 = x2;
				y3 = y2 - 10;

			}

			g.drawLine(x2, y2, x1, y1);
			g.drawLine(x3, y3, x1, y1);
			g.drawLine(x2, y2, x3, y3);

		}

		if (dependancyFlag) {
			x1 = x2 + 10;
			y1 = y2 - 10;
			int x3 = x2 - 10;
			int y3 = y1;

			if (class1_col != class2_col) {
				

				
				y1 = y2 - 5;
				x3 = x2;
				y3 = y2 - 10;

			}

			g.drawLine(x2, y2, x1, y1);
			
			g.drawLine(x2, y2, x3, y3);

		}

	} else {
		y1 = startClassY + classHeight + 3 * verticalSpace;
		x1 = (((id1 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;

		x2 = (((id2 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		y2 = startClassY + classHeight;
		g.drawLine(x1, y1, x2, y2);
		System.out.println("diff=1 -> x1 (even )" + x1 + " y1 " + y1
				+ " x2 " + x2 + " y2 " + y2);

		if (generalizationFlag) {
			int x3 = 0, y3 = 0;

			if (class1_col != class2_col) {
			

				x1 = x2 - 15;
				y1 = y2 + 7;
				x3 = x2 + 10;
				y3 = y2 + 10;

			} else {
				x1 = x2 - 10;
				y1 = y2 + 10;
				x3 = x2 + 10;
				y3 = y1;

			}

			g.drawLine(x2, y2, x1, y1);
			g.drawLine(x3, y3, x1, y1);
			g.drawLine(x2, y2, x3, y3);

		}

		if (dependancyFlag) {
			int x3 = 0, y3 = 0;

			if (class1_col != class2_col) {
			

				x1 = x2 - 15;
				y1 = y2 + 7;
				x3 = x2 + 10;
				y3 = y2 + 10;

			} else {
				x1 = x2 - 10;
				y1 = y2 + 10;
				x3 = x2 + 10;
				y3 = y1;

			}

			g.drawLine(x2, y2, x1, y1);
			
			g.drawLine(x2, y2, x3, y3);

		}

	}
}

private void case3(int id, int class_obj_id, Graphics g,
		Boolean generalizationFlag, Boolean dependancyFlag) {
	int x1;
	int y1;
	int x2;
	int y2;
	int id1 = 0, id2 = 0;
	id1 = id;
	id2 = class_obj_id;
	int x3 = 0, y3 = 0;

	if (id % 2 == 1) {

		x1 = (((id + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		y1 = startClassY + classHeight;

		x2 = (((id2 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		y2 = y1 + 3 * verticalSpace;

		g.drawLine(x1, y1, x2, y2);
		System.out.println("diff=3 -> x1 " + x1 + " y1 " + y1 + " x2 " + x2
				+ " y2 " + y2);

		if (generalizationFlag) {
			g.fillOval(x2, y2, 10, 10);

			if (id1 < id2) {
				x1 = x2 + 7;
				y1 = y2 - 4;
				x3 = x2 - 20;
				y3 = y2 - 3;
			} else {
				System.out.println("Sagarchi idea...");
				x1 = x2 + 9;
				y1 = y2 - 15;
				x3 = x2 + 35;
				y3 = y2 - 1;
			}

			g.drawLine(x2, y2, x1, y1);
			g.drawLine(x3, y3, x1, y1);
			g.drawLine(x2, y2, x3, y3);

		}

		if (dependancyFlag) {
			g.fillOval(x2, y2, 10, 10);

			if (id1 < id2) {
				x1 = x2 + 7;
				y1 = y2 - 4;
				x3 = x2 - 20;
				y3 = y2 - 3;
			} else {
			
				x1 = x2 + 9;
				y1 = y2 - 15;
				x3 = x2 + 35;
				y3 = y2 - 1;
			}

			g.drawLine(x2, y2, x1, y1);
			
			g.drawLine(x2, y2, x3, y3);

		}

	} else {

		x1 = (((id + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		y2 = startClassY + classHeight;

		x2 = (((id2 + 1) / 2) - 1) * (classWidth + horizontalSpace)
				+ startClassX + classWidth / 2;
		y1 = y2 + 3 * verticalSpace;

		g.drawLine(x1, y1, x2, y2);
		System.out.println("diff=3 -> x1 " + x1 + " y1 " + y1 + " x2 " + x2
				+ " y2 " + y2);

		if (generalizationFlag) {
			g.fillOval(x2, y2, 100, 100);

			if (id1 < id2) {
				x1 = x2 - 10;
				y1 = y2 + 10;
				x3 = x1 - 40;
				y3 = y2 + 4;
			} else {

				x1 = x2 - 10;
				y1 = y2 + 10;
				x3 = x2 + 40;
				y3 = y2 + 10;
			}

			g.drawLine(x2, y2, x1, y1);
			g.drawLine(x3, y3, x1, y1);
			g.drawLine(x2, y2, x3, y3);

		}

		if (dependancyFlag) {
			g.fillOval(x2, y2, 100, 100);

			if (id1 < id2) {
				x1 = x2 - 10;
				y1 = y2 + 10;
				x3 = x1 - 40;
				y3 = y2 + 4;
			} else {

				x1 = x2 - 10;
				y1 = y2 + 10;
				x3 = x2 + 40;
				y3 = y2 + 10;
			}

			g.drawLine(x2, y2, x1, y1);
			
			g.drawLine(x2, y2, x3, y3);

		}

	}
}

private void case2(int id1, int id2, Graphics g,
		Boolean generalizationFlag, Boolean dependancyFlag) {
	int x1;
	int y1;
	int x2;
	int y2;
	int x3;
	int y3;
	int temp = -1;
	if (id1 > id2) {
		temp = id1;
		id1 = id2;
		id2 = temp;
	}

	x1 = (((id1 + 1) / 2) - 1) * (classWidth + horizontalSpace)
			+ startClassX + classWidth;
	y1 = startClassY + classHeight / 2;

	x2 = x1 + horizontalSpace;
	y2 = y1;

	g.drawLine(x1, y1, x2, y2);
	System.out.println("diff 2 -> x1 " + x1 + " y1 " + y1 + " x2 " + x2
			+ " y2 " + y2);

	if (generalizationFlag) {
		if (temp == -1) {
			
			x1 = x2 - 10;
			y1 = y2 - 10;
			x3 = x1;
			y3 = y2 + 10;

			g.drawLine(x2, y2, x1, y1);
			g.drawLine(x3, y3, x1, y1);
			g.drawLine(x2, y2, x3, y3);
		}

		else {
			
			x2 = x1 + 10;
			y2 = y1 - 10;
			x3 = x2;
			y3 = y1 + 10;

			g.drawLine(x2, y2, x1, y1);
			g.drawLine(x3, y3, x1, y1);
			g.drawLine(x2, y2, x3, y3);

		}

		if (dependancyFlag) {
			if (temp == -1) {
			
				x1 = x2 - 10;
				y1 = y2 - 10;
				x3 = x1;
				y3 = y2 + 10;

				g.drawLine(x2, y2, x1, y1);
			
				g.drawLine(x2, y2, x3, y3);
			}

			else {
			
				x2 = x1 + 10;
				y2 = y1 - 10;
				x3 = x2;
				y3 = y1 + 10;

				g.drawLine(x2, y2, x1, y1);
				g.drawLine(x3, y3, x1, y1);
			

			}

		}
	}

}

void drawTriangle(int id) {

}

void drawGeneraliazation(Graphics g) {
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
		Statement statement1 = connection.createStatement();

		ResultSet rs = statement.executeQuery("select * from classes");

		int id1 = 0, id2 = 0;
		String cname = null;

		while (rs.next()) {

			id1 = rs.getInt(1);
			cname = rs.getString("parent");
			System.out.println(cname);

			ResultSet rs1 = statement1
					.executeQuery("select * from classes");

			while (rs1.next()) {
				System.out.println("checking " + cname + " and "
						+ rs1.getString("class_name"));
				if (cname != null
						&& cname.equals(rs1.getString("class_name"))) {
					id2 = rs1.getInt("class_id");
					System.out.println(id2 + " is parent of " + id1);
					drawLine(id1, id2, g, true,false);

					drawTriangle(id2);

					break;
				}
			}

			rs1.close();

		}
		statement1.close();
		rs.close();
		statement.close();

	

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
void drawDependancy(Graphics g) {

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
		Statement statement1 = connection.createStatement();

		ResultSet rs = statement.executeQuery("select * from dependancy");

		int id1 = 0, id2 = 0;
		String cname = null;

		while (rs.next()) {

			id1 = rs.getInt(1);
			cname = rs.getString("class_name");

			ResultSet rs1 = statement1
					.executeQuery("select * from classes");

			while (rs1.next()) {
				if (cname.equals(rs1.getString("class_name"))) {
					id2 = rs1.getInt("class_id");
					System.out.println(id1 + " is dependant upon " + id2);
					drawLine(id1, id2, g, false, true);
					break;
				}
			}

			rs1.close();

		}
		statement1.close();
		rs.close();
		statement.close();

	

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

void drawClassDiagram() {
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

		ResultSet rs = statement.executeQuery("select * from classes ");

		int id = 0;
		int count = 0;

		while (rs.next()) {

			id = rs.getInt(1);

			classPattern = new classPattern();
			classPattern.className = new Label(rs.getString(2));
			System.out.println(classPattern.className.getText());
			addVariableToClassDaigarm(id);
			addFunctionToClassDaigarm(id);

			classPattern.className.setBounds(x, y, labelWidth, lableHeight);
			classPattern.variable.setBounds(x, y + lableHeight,
					variableListWidth, variableListHeight);
			classPattern.funtion.setBounds(x, y + lableHeight
					+ variableListHeight + 1, functionListWidth,
					functionListHeight);
			count++;

			if (count % 2 == 0) {
				y = startClassY;
				x += classWidth + horizontalSpace;

			} else {
				y += y + lableHeight + variableListHeight + 1
						+ functionListHeight + verticalSpace;

			}

			Color c = new Color(254, 254, 254);

			Color c1 = new Color(254, 254, 200);

			classPattern.className.setBackground(c1);
			classPattern.variable.setBackground(c);
			classPattern.funtion.setBackground(c);

			add(classPattern.className);
			add(classPattern.variable);
			add(classPattern.funtion);

		}
		rs.close();
		statement.close();

		System.out.println("no_of_claess " + no_classes);

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

void addVariableToClassDaigarm(int id) {

	String var[] = new String[100];

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
				.executeQuery("select * from variable where class_id=" + id);

		int i = 0;

		while (rs.next()) {

			String access = rs.getString("access");
			if (access.equals("default"))
				var[i] = "~ ";
			else if (access.equals("public"))
				var[i] = "+ ";
			else if (access.equals("private"))
				var[i] = "- ";
			else if (access.equals("protected"))
				var[i] = "#";

			var[i] = var[i].concat(rs.getString("name") + " : ");
			var[i] = var[i].concat(rs.getString("type"));
			System.out.println(var[i]);

			i++;

		}
		rs.close();
		statement.close();
		classPattern.variable = new List(i);

		for (int k = 0; k < i; k++) {
			classPattern.variable.add(var[k]);

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

void addFunctionToClassDaigarm(int id) {

	String var[] = new String[100];

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
		Statement statement1 = connection.createStatement();

		// one traversing for costructor

		ResultSet rs = statement
				.executeQuery("select * from function where class_id=" + id);

		int i = 0, fid = 0, t1 = 0;
		String type;
		while (rs.next()) {

			type = rs.getString("type");
			System.out.println("type is *" + type + "* comparing with - "
					+ i);
			if (type.equals("-")) {

				var[i] = "<<constructor>>";
				System.out.println(var[i]);

				i++;

			} else {
				if (t1 == 0) {
					var[i] = "     <<misc>>";
					System.out.println(var[i]);
					i++;
					t1++;
				}

			}

			fid = rs.getInt("fun_id");
			String access = rs.getString("access");

			if (access.equals("default"))
				var[i] = "~ ";
			else if (access.equals("public"))
				var[i] = "+ ";
			else if (access.equals("private"))
				var[i] = "- ";
			else if (access.equals("protected"))
				var[i] = "# ";

			var[i] = var[i].concat(rs.getString("name") + "( ");

			{

				ResultSet rs1 = statement1
						.executeQuery("select * from parameter where fun_id="
								+ fid);
				int t = 0;
				while (rs1.next()) {
					if (t > 0)
						var[i] = var[i].concat("," + rs.getString("name")
								+ " : " + type);
					else
						var[i] = var[i].concat(rs.getString("name") + " : "
								+ type);
					t++;
				}

				rs1.close();
				statement1.close();

			} 

			var[i] = var[i].concat(") : " + rs.getString("type"));
			System.out.println(var[i]);

			i++;

		}
		rs.close();

		statement.close();
		classPattern.funtion = new List(i);

		for (int k = 0; k < i; k++) {
			classPattern.funtion.add(var[k]);

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

void get_total_no_of_classes() throws ClassNotFoundException {
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
				.executeQuery("select max(class_id) from classes ");

		if (rs.next()) {

			no_classes = rs.getInt(1);

		}
		rs.close();
		statement.close();

	

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

public void paintComponent(Graphics g){
	count++;
	int x1 = 280, y1 = 185, x2 = 330, y2 = 185;
	
	// g.dispose();
	changeLinePattern(g, false, 1f);
	drawAssociation(g);
	changeLinePattern(g, true, 1f);
	drawDependancy(g);
	changeLinePattern(g, false, 2f);
	drawGeneraliazation(g);
   }
}
