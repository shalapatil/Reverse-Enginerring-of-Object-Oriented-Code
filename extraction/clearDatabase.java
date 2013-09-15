package extraction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class clearDatabase
{
  public void clearDB() throws ClassNotFoundException
  {
    // load the sqlite-JDBC driver using the current class loader
    Class.forName("org.sqlite.JDBC");
    Vector<Integer> mapid = new Vector<Integer>();

    Connection connection = null;
    try
    {
    	
      connection = DriverManager.getConnection("jdbc:sqlite:project_db");

      Statement statement = connection.createStatement();
      Statement statement1 = connection.createStatement();
      Statement statement2 = connection.createStatement();


		ResultSet rs1 = statement1
				.executeQuery("select * from mapping ");
		Boolean flag=false;
		
		while(rs1.next())
		{
			flag=false;
			String cname=rs1.getString("d_class");

					ResultSet rs2 = statement2
					.executeQuery("select * from classes where class_name='"+cname+"'");

			if(rs2.next())
			{				
					flag=true;				
				
			}
			
			if(!flag)
			{
				//statement.executeUpdate("delete  from mapping where map_id="+rs1.getInt("map_id"));
			     mapid.add(new Integer(rs1.getInt("map_id"))); 		
				
			}
			rs2.close();
		}
		rs1.close();
		statement.close();
		statement1.close();
		statement2.close();
		

		for (int i = 0; i < mapid.size(); i++) {
		//	System.out.println(mapid.get(i).intValue());
			 statement.executeUpdate("delete  from mapping where map_id="+mapid.get(i).intValue());			

		}
  
      statement.executeUpdate("delete  from mapping");
      statement.executeUpdate("delete  from classes");
      statement.executeUpdate("delete  from interface");
      statement.executeUpdate("delete  from function");
      statement.executeUpdate("delete  from parameter");
      statement.executeUpdate("delete  from variable");
      statement.executeUpdate("delete  from dependancy"); 
      
  
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory", 
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }
}