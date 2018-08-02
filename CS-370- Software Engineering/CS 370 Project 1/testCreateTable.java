import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import org.junit.Test;

public class testCreateTable {

	@Test
	public void testCreateTable() throws Exception {
		String tableName = JOptionPane.showInputDialog(null, "Please enter the table name you want to test.");
		Project1.createTable(tableName);
		boolean flag = false;
		ResultSet rs = null;
		DatabaseMetaData dbm = null;
		Connection con = Project1.getConnection();
		dbm = con.getMetaData();
		rs = dbm.getTables(null, null, "%", null);
		while(rs.next()){
			if(rs.getString(3).equals("bhy370group"+tableName)) flag = true;
		}
		assertTrue(flag);
	}

}
