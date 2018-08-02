import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC_Table {

	public static Connection getConnection() throws Exception{
		try{
			String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
			String DB_URL = "jdbc:oracle:thin:@bonnet19.cs.qc.edu:1521:dminor";
			String username = "lackner";
			String password = "guest";
			Class.forName(JDBC_DRIVER);
			
			System.out.println("Connecting to a selected database...");
			Connection conn = DriverManager.getConnection(DB_URL, username, password);
			System.out.println("Connected database successfully...");
			return conn;
		}
		catch(Exception e){System.out.println(e);
		
		return null;
		}
	}
	
	public static void creatTable() throws Exception{
		try{
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS bhy370groupmembers" +
					"(StudentID INTEGER not NULL, " +
					"firstname VARCHAR(20)," +
					"lastname VARCHAR(20))");
		    create.executeUpdate();	
		    System.out.println("Created table in given database...");
		}catch(Exception e){System.out.println(e);}
		finally{System.out.println("Created table in given database...");}
	
	}
	
	public static void insert() throws Exception{
		try{
			Statement stmt = null;
			Connection con = getConnection();
			stmt = con.createStatement();
			String sql = "INSERT INTO bhy370groupmembers " + "VALUES (1, 'Barry', 'Lau')";
		    stmt.executeUpdate(sql);
		    sql = "INSERT INTO bhy370groupmembers " +
		                   "VALUES (2, 'Henry', 'Qiu')";
		    stmt.executeUpdate(sql);
		    sql = "INSERT INTO bhy370groupmembers " +
		                   "VALUES (3, 'Yu', 'Zheng')";
		    stmt.executeUpdate(sql);
		    }catch(Exception e){System.out.println(e);}
		finally{System.out.println("Inserted records into table...");}
		
	}
	
	public static void print() throws Exception{
		try{
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT groupID,firstname,lastname FROM bhy370groupmembers");
			ResultSet rs = statement.executeQuery();
					
			System.out.println("GroupID    FirstName    LastName");
			while (rs.next()) {
				int groupID = rs.getInt("groupID");
				String FirstName = rs.getString("firstname");
				String LastName = rs.getString("lastname");
				System.out.println(groupID+"        "+FirstName+"        "+LastName);
				}
			}catch(Exception e){System.out.println(e);}
		
		}
	public static void delete() throws Exception{
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE bhy370groupmembers WHERE GroupID = 2";

		
		try{
			Connection con = getConnection();
			preparedStatement = con.prepareStatement(deleteSQL);
			preparedStatement.executeUpdate();

			System.out.println("Record is deleted!");

		}catch(Exception e){System.out.println(e);}
	}
	public static void update() throws Exception{
		try{
			Statement stmt = null;
			Connection con = getConnection();
			stmt = con.createStatement();
			String sql = "UPDATE bhy370groupmembers " +
						"SET groupID = 1 WHERE groupID in (2, 3)";
			stmt.executeUpdate(sql);
		
	}catch(Exception e){System.out.println(e);}
	}
		
	public static void main(String[] args) throws Exception {
		

		//creatTable();
		//insert();
		//delete();
		//update();
		print() ;
	    
	}


}
