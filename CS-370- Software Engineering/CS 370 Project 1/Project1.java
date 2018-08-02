//bhygroup
//Group members - Barry Lau, Henry Qiu, Yu Zheng
import java.sql.*;

import javax.swing.*;

public class Project1 {
	/*
	 * The below static code has to be hard coded in 
	 * to use the getConnection() and this program
	 * assumes your local database has a group made 
	 * already.
	 */
	static String dbURL = "jdbc:mysql://localhost/bhy370";
	static String JDBC = "com.mysql.jdbc.Driver";
	static String user = "root";
	static String pass = "admin";
	
	public static Connection getConnection() throws Exception{
		try{
			Class.forName(JDBC);
			Connection conn = DriverManager.getConnection(dbURL, user, pass);
			return conn;
		}
		catch(Exception e){
			return null;
		}
	}
	public static void createTable(String tableName) throws Exception{
		Connection con = getConnection();
		try{
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS bhy370group" + tableName +
															"(groupID INTEGER NOT NULL, " +
															"firstname VARCHAR(50) NOT NULL," +
															"lastname VARCHAR(50) NOT NULL," +
															"notes VARCHAR(255) NOT NULL,"
															+ "PRIMARY KEY ( groupID ))");
			create.executeUpdate();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error! Table could not be created!");
		}

	}
	public static void insertRecord(String tableName) throws Exception{
		int result;
		Statement stmt = null;
		Connection con = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		preparedStatement = con.prepareStatement("SELECT groupID FROM bhy370group" + tableName);
		resultSet = preparedStatement.executeQuery();
		JTextField id = new JTextField();
		JTextField fn = new JTextField();
		JTextField ln = new JTextField();
		JTextField nt = new JTextField();		
		Object[] fields = {
				"ID", id,
				"First Name", fn,
				"Last Name", ln,
				"Notes", nt
		};//Object
		if(resultSet != null){
			JOptionPane.showMessageDialog(null, "The following numbers are the ID's that currently exist in the database.");
			while (resultSet.next()) {
				int groupID = resultSet.getInt("groupID");
				System.out.println("ID #: " + groupID);
			}//while
		}//if
		result = JOptionPane.showConfirmDialog(null, fields, "Insert Record", JOptionPane.OK_CANCEL_OPTION);
		if	(result == JOptionPane.OK_OPTION)	{
			String groupID = id.getText();
			String firstname = fn.getText();
			String lastname = ln.getText();
			String notes = nt.getText();
			int ID = Integer.parseInt(groupID);
			if(ID < 0){
				JOptionPane.showMessageDialog(null, "Error! Group Id is less than 0!");
				return;
			}
		try{
			stmt = con.createStatement();
			String sql = "INSERT INTO bhy370group" + tableName + " " + 
								"VALUES (" + groupID + ", '" + firstname + "', '" + lastname + "', '" + notes + "')";
			stmt.executeUpdate(sql);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error! group ID : " + groupID + " is not an integer or the ID already exists in the database" +
											" or any of your inputs have unsupported ASCII characters in them!");
		}
		result = JOptionPane.showConfirmDialog(null, "Would you like to add another record?", null, JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION){
			insertRecord(tableName);
		}
		else
			return;
		}
	}
	public static void deleteRecord(String tableName) throws Exception{
		int result;
		String criteria0 = null;
		String criteria1 = null;
		Connection con = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			criteria0 = JOptionPane.showInputDialog(null, "Please enter the table scope of where you wish to search. Your choices are firstname, lastname, or notes. \n" +
																"Please note that you MUST enter the string as shown above or else this function will fail.");
			if(criteria0 == null){
				return;
			}
			if(criteria0.equals("firstname") || criteria0.equals("lastname") || criteria0.equals("notes")){
				criteria1 = JOptionPane.showInputDialog(null, "Please narrow the search criteria for a possible list of what you wish to delete: ");
				if(criteria1 == null){
					return;
				}
				preparedStatement = con.prepareStatement("SELECT groupID, firstname, lastname, notes FROM bhy370group" + tableName + " WHERE " + criteria0 + " LIKE " +
														"'%" + criteria1 + "%'");
				JOptionPane.showMessageDialog(null, "A list of data containing your search criteria has been printed to the console. \n" + 
													"Table Scope Criteria: " + criteria0 + " and Narrowed Search Criteria: " + criteria1 + "\n" +
													"Note: If nothing is printed, it means that the Narrowed Search Criteria: " + criteria1 + " has no data that qualifies. \n" +
													"In this case please skip the next few input dialogs until you can restart this function.");
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null){
					while (resultSet.next()) {
						int groupID = resultSet.getInt("groupID");
						String FirstName = resultSet.getString("firstname");
						String LastName = resultSet.getString("lastname");
						String Notes = resultSet.getString("notes");
						System.out.print("ID #: " + groupID);
						System.out.print(" First Name: " + FirstName);
						System.out.print(" Last Name: " + LastName);
						System.out.println(" Notes: " + Notes);
					}
					try{
						String groupID = JOptionPane.showInputDialog("Please enter the group ID of the person you want to delete from the database: ");
						if(groupID == null){
							return;
						}
						int ID = Integer.parseInt(groupID);
						if(ID < 0){
							JOptionPane.showMessageDialog(null, "Error! Group Id is less than 0!");
							return;
						}
						preparedStatement = null;
						String deleteSQL = "DELETE FROM bhy370group" + tableName + " WHERE groupID = " + groupID;
						preparedStatement = con.prepareStatement("SELECT groupID FROM bhy370group" + tableName + " WHERE groupID = " + groupID );
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							try{
								preparedStatement = con.prepareStatement(deleteSQL);
								preparedStatement.executeUpdate();
								JOptionPane.showMessageDialog(null, "The person with group ID: " + groupID + " has been deleted from the database");	
							}	
							catch(Exception e){
								JOptionPane.showMessageDialog(null, "Error! group ID : " + groupID + " is not an integer or does not exist in the database");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "Error! group ID : " + groupID + " does not exist!");
						}
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null, "Error! Nothing was entered! Exiting function!");
					}
				}	
			}
			else if(criteria0.equals("")){
				JOptionPane.showMessageDialog(null, "Your Table Scope Search Criteria was 'null', hence all the data on the table is printed out.");
				preparedStatement = con.prepareStatement("SELECT groupID, firstname, lastname, notes FROM bhy370group" + tableName);
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null){
					while (resultSet.next()) {
						int groupID = resultSet.getInt("groupID");
						String FirstName = resultSet.getString("firstname");
						String LastName = resultSet.getString("lastname");
						String Notes = resultSet.getString("notes");
						System.out.print("ID #: " + groupID);
						System.out.print(" First Name: " + FirstName);
						System.out.print(" Last Name: " + LastName);
						System.out.println(" Notes: " + Notes);
					}
					try{
						String groupID = JOptionPane.showInputDialog("Please enter the group ID of the person you want to delete from the database: ");
						if(groupID == null){
							return;
						}
						int ID = Integer.parseInt(groupID);
						if(ID < 0){
							JOptionPane.showMessageDialog(null, "Error! Group Id is less than 0!");
							return;
						}
						preparedStatement = null;
						String deleteSQL = "DELETE FROM bhy370group" + tableName + " WHERE groupID = " + groupID;
						preparedStatement = con.prepareStatement("SELECT groupID FROM bhy370group" + tableName + " WHERE groupID = " + groupID );
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							try{
								preparedStatement = con.prepareStatement(deleteSQL);
								preparedStatement.executeUpdate();
								JOptionPane.showMessageDialog(null, "The person with group ID: " + groupID + " has been deleted from the database");	
							}	
							catch(Exception e){
								JOptionPane.showMessageDialog(null, "Error! group ID : " + groupID + " is not an integer or does not exist in the database");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "Error! group ID : " + groupID + " does not exist!");
						}
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null, "Error! Nothing was entered! Exiting function!");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Error! The table is empty so no data could be printed to the console!");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Error! The Table Scope Criteria : " + criteria0 + " is not a valid parameter in this table");
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error! Criterias: " + criteria0 + " is invalid! No such column exists in the table!");
		}
		result = JOptionPane.showConfirmDialog(null, "Would you like to delete another record?", null, JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION){
			deleteRecord(tableName);
		}
		else{
			return;
		}
	}
	public static void updateRecord(String tableName) throws Exception{
		int result;
		String criteria0 = null;
		String criteria1 = null;
		Connection con = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		JTextField fn = new JTextField();
		JTextField ln = new JTextField();
		JTextField nt = new JTextField();
		try{
			criteria0 = JOptionPane.showInputDialog(null, "Please enter the table scope of where you wish to search. Your choices are firstname, lastname, or notes. \n" +
																	"Please note that you MUST enter the string as shown above or else this function will fail.");
			if(criteria0 == null){
				return;
			}
			if(criteria0.equals("firstname") || criteria0.equals("lastname") || criteria0.equals("notes")){
				criteria1 = JOptionPane.showInputDialog(null, "Please narrow the search criteria for a possible list of what you wish to update by giving specific " 
																	+ "data you wish to update : ");
				if(criteria1 == null){
					return;
				}
				preparedStatement = con.prepareStatement("SELECT groupID, firstname, lastname, notes FROM bhy370group" + tableName + " WHERE " + criteria0 + " LIKE " +
															"'%" + criteria1 + "%'");
				JOptionPane.showMessageDialog(null, "A list of data containing your search criteria has been printed to the console. \n" + 
													"Table Scope Criteria: " + criteria0 + " and Narrowed Search Criteria: " + criteria1 + "\n" +
													"Note: If nothing is printed, it means that the Narrowed Search Criteria: " + criteria1 + " has no data that qualifies. \n" +
													"In this case please skip the next few input dialogs until you can restart this function.");
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null){
					while (resultSet.next()) {
						int groupID = resultSet.getInt("groupID");
						String FirstName = resultSet.getString("firstname");
						String LastName = resultSet.getString("lastname");
						String Notes = resultSet.getString("notes");
						System.out.print("ID #: " + groupID);
						System.out.print(" First Name: " + FirstName);
						System.out.print(" Last Name: " + LastName);
						System.out.println(" Notes: " + Notes);
					}
					try{
						String groupID = JOptionPane.showInputDialog(null, "Please enter the group ID of the person whose comments you wish to change: ");
						if(groupID == null){
							return;
						}
						int ID = Integer.parseInt(groupID);
						if(ID < 0){
							JOptionPane.showMessageDialog(null, "Error! Group Id is less than 0!");
							return;
						}
						preparedStatement = con.prepareStatement("SELECT groupID FROM bhy370group" + tableName + " WHERE groupID = " + groupID);
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							Object[] fields = {
									"New first name:", fn,
									"New last name:", ln,
									"New notes: ", nt
							};
							try{
								result = JOptionPane.showConfirmDialog(null, fields, "Update records", JOptionPane.OK_CANCEL_OPTION);
								if	(result == JOptionPane.OK_OPTION)	{
									String firstname = fn.getText();
									String lastname = ln.getText();
									String notes = nt.getText();
									Statement stmt = null;
									stmt = con.createStatement();
									String sql = "UPDATE bhy370group" + tableName + " " +
												"SET firstname = '" + firstname + "' WHERE groupID = " + groupID;
									stmt.executeUpdate(sql);
									sql = "UPDATE bhy370group" + tableName + " " +
												"SET lastname = '" + lastname + "' WHERE groupID = " + groupID;
									stmt.executeUpdate(sql);
									sql = "UPDATE bhy370group" + tableName + " " +
												"SET notes = '" + notes + "' WHERE groupID = " + groupID;
									stmt.executeUpdate(sql);
									JOptionPane.showMessageDialog(null, "The data of the person with group ID: " + groupID + " has been changed!");
									}
								}
							catch(Exception e){
								JOptionPane.showMessageDialog(null, "Error! group ID : " + groupID + " is not an integer or does not exist in the database " 
																	+ "or the comments you entered exceed the maximum amount of characters or there exists unsupported"
																	+ " ASCII characters in the comments!");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "Error! group ID : " + groupID + " does not exist!");
						}
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null, "Error! Nothing was entered! Exiting function!");
					}
				}
			}
			else if(criteria0.equals("")){
				JOptionPane.showMessageDialog(null, "Your Table Scope Search Criteria was 'null', hence all the data on the table is printed out.");
				preparedStatement = con.prepareStatement("SELECT groupID, firstname, lastname, notes FROM bhy370group" + tableName);
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null){
					while (resultSet.next()) {
						int groupID = resultSet.getInt("groupID");
						String FirstName = resultSet.getString("firstname");
						String LastName = resultSet.getString("lastname");
						String Notes = resultSet.getString("notes");
						System.out.print("ID #: " + groupID);
						System.out.print(" First Name: " + FirstName);
						System.out.print(" Last Name: " + LastName);
						System.out.println(" Notes: " + Notes);
					}
					try{
						String groupID = JOptionPane.showInputDialog(null, "Please enter the group ID of the person whose comments you wish to change: ");
						if(groupID == null){
							return;
						}
						int ID = Integer.parseInt(groupID);
						if(ID < 0){
							JOptionPane.showMessageDialog(null, "Error! Group Id is less than 0!");
							return;
						}
						preparedStatement = con.prepareStatement("SELECT groupID FROM bhy370group" + tableName + " WHERE groupID = " + groupID);
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							Object[] fields = {
									"New first name:", fn,
									"New last name:", ln,
									"New notes: ", nt
							};
							try{
								result = JOptionPane.showConfirmDialog(null, fields, "Update records", JOptionPane.OK_CANCEL_OPTION);
								if	(result == JOptionPane.OK_OPTION)	{
									String firstname = fn.getText();
									String lastname = ln.getText();
									String notes = nt.getText();
									Statement stmt = null;
									stmt = con.createStatement();
									String sql = "UPDATE bhy370group" + tableName + " " +
												"SET firstname = '" + firstname + "' WHERE groupID = " + groupID;
									stmt.executeUpdate(sql);
									sql = "UPDATE bhy370group" + tableName + " " +
												"SET lastname = '" + lastname + "' WHERE groupID = " + groupID;
									stmt.executeUpdate(sql);
									sql = "UPDATE bhy370group" + tableName + " " +
												"SET notes = '" + notes + "' WHERE groupID = " + groupID;
									stmt.executeUpdate(sql);
									JOptionPane.showMessageDialog(null, "The data of the person with group ID: " + groupID + " has been changed!");
									}
							}
							catch(Exception e){
								JOptionPane.showMessageDialog(null, "Error! group ID : " + groupID + " is not an integer or does not exist in the database " 
																	+ "or the comments you entered exceed the maximum amount of characters or there exists unsupported"
																	+ " ASCII characters in the comments!");
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "Error! The table is empty so no data could be printed to the console!");
						}
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(null, "Error! Nothing was entered! Exiting function!");
					}
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Error! The Table Scope Criteria : " + criteria0 + " is not a valid parameter in this table");
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error! Criterias: " + criteria0 + " is invalid! No such column exists in the table!");
		}
		result = JOptionPane.showConfirmDialog(null, "Would you like to try again?", null, JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION){
			updateRecord(tableName);
		}
		else{
			return;
		}
	}
	public static void printRecord(String tableName) throws Exception{
		int result;
		Connection con = getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			String criteria0 = JOptionPane.showInputDialog(null, "Please enter the table scope of where you wish to search. Your choices are firstname, lastname, or notes. \n" +
																	"Please note that you MUST enter the string as shown above or else this function will fail. \n "
																	+ "Note: If okay is pressed without entering anything, the entire table is printed.");
			if(criteria0 == null){
				return;
			}
			if(criteria0.equals("firstname") || criteria0.equals("lastname") || criteria0.equals("notes")){
				String criteria1 = JOptionPane.showInputDialog(null, "Please narrow the search criteria for a possible list of what you wish to update by giving specific " 
																	+ "data you wish to update : ");
				if(criteria1 == null){
					return;
				}
				preparedStatement = con.prepareStatement("SELECT groupID, firstname, lastname, notes FROM bhy370group" + tableName + " WHERE " + criteria0 + " LIKE " +
														"'%" + criteria1 + "%'");
				JOptionPane.showMessageDialog(null, "A list of data containing your search criteria has been printed to the console. \n" + 
													"Table Scope Criteria: " + criteria0 + " and Narrowed Search Criteria: " + criteria1 + "\n" +
													"Note: If nothing is printed to the console, it means that the Narrowed Search Criteria: " + criteria1 + " has no data \n "
														+ "that exists in the Table Scope Critera: " + criteria1);
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null){
					while (resultSet.next()) {
						int groupID = resultSet.getInt("groupID");
						String FirstName = resultSet.getString("firstname");
						String LastName = resultSet.getString("lastname");
						String Notes = resultSet.getString("notes");
						System.out.print("ID #: " + groupID);
						System.out.print(" First Name: " + FirstName);
						System.out.print(" Last Name: " + LastName);
						System.out.println(" Notes: " + Notes);
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Error! The table is empty so no data could be printed to the console!");
				}
			}
			else if(criteria0.equals("")){
				JOptionPane.showMessageDialog(null, "Your Table Scope Search Criteria was 'null', hence all the data on the table is printed out.");
				preparedStatement = con.prepareStatement("SELECT groupID, firstname, lastname, notes FROM bhy370group" + tableName);
				resultSet = preparedStatement.executeQuery();
				if(resultSet != null){
					while (resultSet.next()) {
						int groupID = resultSet.getInt("groupID");
						String FirstName = resultSet.getString("firstname");
						String LastName = resultSet.getString("lastname");
						String Notes = resultSet.getString("notes");
						System.out.print("ID #: " + groupID);
						System.out.print(" First Name: " + FirstName);
						System.out.print(" Last Name: " + LastName);
						System.out.println(" Notes: " + Notes);
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Error! The table is empty so no data could be printed to the console!");
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Error! The Table Scope Criteria : " + criteria0 + " is not a valid parameter in this table");
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error! Either the table could not be printed or the criteria you have inputed is invalid!");
		}
		result = JOptionPane.showConfirmDialog(null, "Would you like to print out another record?", null, JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION){
			printRecord(tableName);
		}
		else{
			return;
		}
	}
	
	public static void main(String[] args) throws Exception{
		String tableName = null;
		int result = -1;
		String[] optionButtons = {"Print", "Change", "Insert", "Delete", "Exit"};
		ResultSet rs = null;
		DatabaseMetaData dbm = null;
		JOptionPane.showMessageDialog(null, "Hello, this is a basic program which does limited" 	
											+ " DML operations for a premade database. Press Okay to continue");
		System.out.println("Connecting to selected database...");	
		Connection con = getConnection();
		if(getConnection() != null){
			System.out.println("Connected to database successfully...");
		}
		else{
			System.out.println("Could not connect to database...");
			JOptionPane.showMessageDialog(null, "Could not establish a connection to database, terminating program...");
			System.exit(0);
		}
		JOptionPane.showMessageDialog(null, "Here is a list of currently existing tables in the database printed to the console?");
		dbm = con.getMetaData();
		rs = dbm.getTables(null, null, "%", null);
		while(rs.next()){
			System.out.println(rs.getString(3));
		}
		tableName = JOptionPane.showInputDialog(null, "Please enter the table name. \n"
															+ "If no table name is entered, but okay is pressed, then the default table is 'bhy370group' \n"
															+ "If a table name is entered, then this program searches the database to see if the table exists, \n"
															+ "if it does not, then a new table is created, if it does exist, then that table will be used for this program");
		if(tableName == null){
			System.exit(0);
		}
		createTable(tableName);
		System.out.println("You are using table bhy370group" + tableName + "!");
		dbm = con.getMetaData();
		rs = dbm.getTables(null,  null, "bhy370group" + tableName, null);
		if(rs.next()){					
			while(result != 4){					
				result = JOptionPane.showOptionDialog(null, "What would you like to do?", "Options",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, optionButtons, optionButtons[4]);
				if(result == 0)
					printRecord(tableName);				
				else if(result == 1)
					updateRecord(tableName);
				else if (result == 2)
					insertRecord(tableName);
				else if(result == 3)
					deleteRecord(tableName);
				else if(result == 4)
				{System.exit(0);}
			}//while				
		}
	}
}


