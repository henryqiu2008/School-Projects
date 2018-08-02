import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.junit.Test;

public class testUpdateRecord {

	@Test
	public void testUpdateRecord() throws Exception {
		int result;
		JTextField fn = new JTextField();
		JTextField ln = new JTextField();
		JTextField nt = new JTextField();
		boolean flag2 = true;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String tableName = JOptionPane.showInputDialog(null, "Please enter the table name you want to test.");
		Project1.createTable(tableName);
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
			JOptionPane.showMessageDialog(null, "In testing, please only change one record from the table.");
			JOptionPane.showMessageDialog(null, "Start!!");
		Project1.updateRecord(tableName);
		JOptionPane.showMessageDialog(null, "Please re-enter all datas exacly as you just enter");
		Object[] fields = {
				"New first name:", fn,
				"New last name:", ln,
				"New notes: ", nt
		};
		result = JOptionPane.showConfirmDialog(null, fields, "testing!", JOptionPane.OK_CANCEL_OPTION);
		if	(result == JOptionPane.OK_OPTION)	{
			String newfirstname = fn.getText();
			String newlastname = ln.getText();
			String newnotes = nt.getText();
		
		preparedStatement = con.prepareStatement("SELECT * FROM bhy370group"+tableName+" WHERE groupID="+TestID);
		resultSet = preparedStatement.executeQuery();
		if(resultSet != null){
			resultSet.next();
			String FirstName = resultSet.getString("firstname");
			String LastName = resultSet.getString("lastname");
			String Notes = resultSet.getString("notes");
			assertEquals(newfirstname, FirstName);
			assertEquals(newlastname, LastName);
			assertEquals(newnotes, Notes);
		}
		}
		
	}
	}
}


