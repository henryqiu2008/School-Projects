import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.junit.Test;

public class testDeleteRecord {

	@Test
	public void testDeleteRecord() throws Exception {
		boolean flag = true;
		boolean flag2 = true;
		String tableName = JOptionPane.showInputDialog(null, "Please enter the table name you want to test.");
		if(tableName == null)System.exit(0);
		Project1.createTable(tableName);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection con = Project1.getConnection();
		preparedStatement = con.prepareStatement("SELECT groupID FROM bhy370group"+tableName);
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
			while(flag2){
				while (resultSet.next()) {
					try{
						int groupID = resultSet.getInt("groupID");
						if(groupID == Integer.parseInt(TestID)) {flag2 = false;}
						while(Integer.parseInt(TestID)<0){
							TestID = JOptionPane.showInputDialog(null, "Test ID cannot be negative! Please enter another groupID you want to test.");
							if(TestID == null)System.exit(0);
							resultSet = preparedStatement.executeQuery();	
							}
						}
					catch(Exception e){
						TestID = JOptionPane.showInputDialog(null, "Test ID cannot be null! Please enter another groupID you want to test.");
						if(TestID == null)System.exit(0);
						resultSet = preparedStatement.executeQuery();
					}
				}
				if(flag2){
					TestID = JOptionPane.showInputDialog(null, TestID+" does not exist!  Please enter another groupID you want to test.");
					if(TestID == null)System.exit(0);
					resultSet = preparedStatement.executeQuery();				
				}
			}
			JOptionPane.showMessageDialog(null, "In testing, please only delete one record from the table.");
			JOptionPane.showMessageDialog(null, "Start!!");
			Project1.deleteRecord(tableName);
			resultSet = preparedStatement.executeQuery();
			if(resultSet != null){
				while(resultSet.next()){
					int groupID = resultSet.getInt("groupID");
					if(groupID == Integer.parseInt(TestID)) flag = false;}
			}
			assertTrue(flag);
		}

	}
}
