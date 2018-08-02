import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;


public class TestGetConnection {

	@Test
	public void testGetConnection() throws Exception {
		Connection con = Project1.getConnection();
	    assertNotNull(con);
	}

}
