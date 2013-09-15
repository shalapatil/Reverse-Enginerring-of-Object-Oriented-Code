package extraction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import Database_handling.*;

public class MetaClass {

	public String classname;
	public boolean static_class;
	public boolean final_class;
	public boolean abstract_class;
	public String access_class;
	public String parent_class;
	public Vector<String> interfac = new Vector<String>();
	public Vector<Variable> variable = new Vector<Variable>();
	public Vector<Function> function = new Vector<Function>();
	public static Vector<Mapping> mapping = new Vector<Mapping>();

	public MetaClass() {

	}

	public void print_class_details() {

		StoreToDatabase s = new StoreToDatabase();
		try {
			s.store(this);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("*** Class Details ******");

		System.out.println("\nClass name :: " + classname);
		System.out.println("Static class :: " + static_class);
		System.out.println("Final class :: " + final_class);
		System.out.println("Abstract class :: " + abstract_class);
		System.out.println("Access specifier of class :: " + access_class);
		System.out.println("Parent class : " + parent_class);
		System.out.print("Interfaces implemented : ");
		for (int i = 0; i < interfac.size(); i++) {
			System.out.print(interfac.get(i) + " , ");

		}

		System.out.println("\n\n**** Instance variable Details ****** ");

		Variable v;
		for (int i = 0; i < variable.size(); i++) {
			v = variable.get(i);
			v.print_variable_details();
		}

		Function f;
		for (int i = 0; i < function.size(); i++) {
			f = function.get(i);
			f.print_function_details();
		}

	}

	public static void print_all_mapping() throws ClassNotFoundException {
		Mapping f1;
		
		System.out.println("length : "+extractDetails.globalClassCount);
		for(int i1=0;i1<extractDetails.globalClassCount;i1++)
		{
			System.out.println(extractDetails.output[i1].classname);
			
		}
		System.out.println("Size is****:-"+mapping.size());
		
		/*for (int i = 0; i < mapping.size(); i++) {
			f1 = mapping.get(i);
			int flag=0;
			for(int i1=0;i1<extractDetails.output.length;i1++)
			{
				if(f1.d_class.equals(extractDetails.output[i1].classname))
				{
					
					flag=1;
					break;
				}
				
			}
			if(flag==0)
				mapping.removeElementAt(i);
				
				
			
			//f.print_mapping();
		}*/
		
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {

			connection = DriverManager.getConnection("jdbc:sqlite:project_db");

			PreparedStatement ps ;
			
			for (int i = 0; i < mapping.size(); i++) {
				f1 = mapping.get(i);
				
				
				int flag=0;
				for(int i1=0;i1<extractDetails.globalClassCount;i1++)
				{
					if(f1.d_class.equals(extractDetails.output[i1].classname))
					{
						
						flag=1;
						break;
					}
					
				}
				if(flag==1)
				{
			ps = connection
					.prepareStatement("insert into mapping (s_class,s_fun,d_class,d_fun) values(?,?,?,?)");
			ps.setString(1, f1.s_class);
			ps.setString(2, f1.s_fun);
			ps.setString(3, f1.d_class);
			ps.setString(4, f1.d_fun);

			ps.executeUpdate();
			ps.close();
				}
			}
		}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		finally {
				try
				{
					if (connection != null)
						connection.close();
				} 
				catch (SQLException e) 
				{
					// connection close failed.
					System.err.println(e);
				}
			

			}
	}
}