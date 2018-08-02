import javax.swing.JOptionPane;

import org.junit.Test;

public class testPrintRecord {

	@Test
	public void testPrintRecord() throws Exception {
		String tableName = JOptionPane.showInputDialog(null, "Please enter the table name you want to test.");
		Project1.createTable(tableName);
		Project1.printRecord(tableName);
	}

}
