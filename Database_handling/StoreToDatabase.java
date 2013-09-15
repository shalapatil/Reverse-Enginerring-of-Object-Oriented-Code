package Database_handling;

import extraction.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class StoreToDatabase {
	public static Vector<Mapping> map = new Vector<Mapping>();

	int getIntegerValueForBoolean(Boolean b) {
		if (b)
			return 1;
		else
			return 0;

	}

	public void store(MetaClass m) throws ClassNotFoundException {
		storeClassDetails(m);

	}

	void storeClassDetails(MetaClass m) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {

			connection = DriverManager.getConnection("jdbc:sqlite:project_db");

			Statement statement = connection.createStatement();

			ResultSet rs = statement
					.executeQuery("select max(class_id) from classes ");

		
			PreparedStatement ps = connection
					.prepareStatement("insert into classes (class_name,static,final,abstract,access,parent) values(?,?,?,?,?,?)");
			ps.setString(1, m.classname);
			ps.setInt(2, getIntegerValueForBoolean(m.static_class));
			ps.setInt(3, getIntegerValueForBoolean(m.final_class));
			ps.setInt(4, getIntegerValueForBoolean(m.abstract_class));
			ps.setString(5, m.access_class);
			ps.setString(6, m.parent_class);
			ps.executeUpdate();
			ps.close();

			int id = 0;

			rs = statement.executeQuery("select max(class_id) from classes ");
			if (rs.next()) {

				id = rs.getInt(1);
			}

			for (int i = 0; i < m.interfac.size(); i++) {
				ps = connection
						.prepareStatement("insert into interface (class_id,string) values(?,?)");
				ps.setInt(1, id);
				ps.setString(2, m.interfac.get(i));
				ps.executeUpdate();
				ps.close();

			}

			Variable v;
			for (int i = 0; i < m.variable.size(); i++) {
				v = m.variable.get(i);
				ps = connection
						.prepareStatement("insert into variable (class_id,type,name,access,static,final,abstract,const) values(?,?,?,?,?,?,?,?)");
				ps.setInt(1, id);
				ps.setString(2, v.type);
				ps.setString(3, v.name);
				ps.setString(4, v.access);
				ps.setInt(5, getIntegerValueForBoolean(v.static1));
				ps.setInt(6, getIntegerValueForBoolean(v.final1));
				ps.setInt(7, getIntegerValueForBoolean(v.abstract1));
				ps.setInt(8, getIntegerValueForBoolean(v.const1));
				ps.executeUpdate();
				ps.close();

			}
			//removeDuplicateEntries();
		/*	Mapping f1;
			for (int k = 0; k < map.size(); k++) {
				f1 = map.get(k);
				checkForDuplication(k,f1);
				ps = connection
						.prepareStatement("insert into mapping (s_class,s_fun,d_class,d_fun) values(?,?,?,?)");
				ps.setString(1, f1.s_class);
				ps.setString(2, f1.s_fun);
				ps.setString(3, f1.d_class);
				ps.setString(4, f1.d_fun);

				ps.executeUpdate();
				ps.close();
			}
			*/
			Function f;
			for (int i = 0; i < m.function.size(); i++) {
				f = m.function.get(i);
				System.out.print(m.function.get(i) + " ,( ");
				ps = connection
						.prepareStatement("insert into function (class_id,type,name,access,static,final,const) values(?,?,?,?,?,?,?)");
				ps.setInt(1, id);
				ps.setString(2, f.type);
				ps.setString(3, f.name);
				ps.setString(4, f.access);
				ps.setInt(5, getIntegerValueForBoolean(f.static1));
				ps.setInt(6, getIntegerValueForBoolean(f.final1));
				ps.setInt(7, getIntegerValueForBoolean(f.const1));
				ps.executeUpdate();
				ps.close();

				int fid = 0;
				rs = statement
						.executeQuery("select max(fun_id) from function ");
				if (rs.next()) {

					fid = rs.getInt(1);
				}

				Parameter1 p;
				for (int k = 0; k < f.parameter.size(); k++) {
					p = f.parameter.get(k);
					ps = connection
							.prepareStatement("insert into parameter (fun_id,type,name) values(?,?,?)");
					ps.setInt(1, fid);
					ps.setString(2, p.type);
					ps.setString(3, p.name);

					ps.executeUpdate();
					ps.close();

				}

				for (int k = 0; k < f.localVar.size(); k++) {
					p = f.localVar.get(k);
					ps = connection
							.prepareStatement("insert into dependancy (class_id,class_name) values(?,?)");
					ps.setInt(1, id);
					ps.setString(2, p.type);

					ps.executeUpdate();
					ps.close();

				}

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

}
