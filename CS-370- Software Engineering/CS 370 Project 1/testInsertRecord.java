import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.junit.Test;

public class testInsertRecord {

	@Test
	public void testInsertRecord() throws Exception {
		String tableName = JOptionPane.showInputDialog(null, "Please enter the table name you want to test.");
		if(tableName == null)System.exit(0);
		Project1.createTable(tableName);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = Project1.getConnection();
		preparedStatement = con.prepareStatement("SELECT groupID FROM bhy370group" + tableName);
		resultSet = preparedStatement.executeQuery();
		JOptionPane.showMessageDialog(null, "The following numbers are the ID's that currently exist in the table.");
		if(resultSet != null){
			while (resultSet.next()) {
				int groupID = resultSet.getInt("groupID");
				System.out.println("ID #: " + groupID);
			}
		}
			String TestID = JOptionPane.showInputDialog(null, "Please enter the groupID you want to test.");
			if(TestID == null)System.exit(0);
		resultSet = preparedStatement.executeQuery();
		if(resultSet != null){
			while (resultSet.next()) {
				try{
					int groupID = resultSet.getInt("groupID");
					while(Integer.parseInt(TestID)<0){
						TestID = JOptionPane.showInputDialog(null, "Test ID cannot be negative! Please enter another groupID you want to test.");
						if(TestID == null)System.exit(0);
						resultSet = preparedStatement.executeQuery();	
						}
					
					while(groupID == Integer.parseInt(TestID)){
						TestID = JOptionPane.showInputDialog(null, TestID+" is already exist! Please enter another groupID you want to test.");
						if(TestID == null)System.exit(0);
						resultSet = preparedStatement.executeQuery();	
						}
					}
				catch(Exception e){
					TestID = JOptionPane.showInputDialog(null, "Test ID cannot be null! Please enter another groupID you want to test.");
					if(TestID == null)System.exit(0);
				}
			}
		}
		JOptionPane.showMessageDialog(null, "In testing, please only insert one record to the table.");
		JOptionPane.showMessageDialog(null, "Start!!");
		Project1.insertRecord(tableName);
		preparedStatement = con.prepareStatement("SELECT * FROM bhy370group"+tableName+" WHERE groupID="+TestID);
		resultSet = preparedStatement.executeQuery();
		if(resultSet != null){
			resultSet.next();
			int groupID = resultSet.getInt("groupID");
			assertNotNull(groupID);
		}
		
	}

}
