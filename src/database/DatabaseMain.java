package database;
import java.sql.*;
import java.util.Vector;

public class DatabaseMain {
	public static void connect() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		DatabaseConnection.conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/MagicBoard",
				"mxx", "123456");
		DatabaseConnection.stmt = DatabaseConnection.conn.createStatement();
		
		String tableName = "rankboard";
		DatabaseConnection.stmt.execute(
				"CREATE TABLE IF NOT exists " + tableName + "( "+
				"name varchar(20) not null, " +
				"time varchar(20) not null, " +
				"steps int not null );"
				);
	}
	
	public static void insertRankRecord(RankRecord record) throws Exception{
		DatabaseConnection.stmt.execute("INSERT INTO rankboard VALUES(" + 
				"\""+record.name+"\"" + ", " + 
				"\""+record.time+"\"" + ", " + 
				record.steps + ");");
	}
	
	public static Vector<RankRecord> getRank() throws Exception{
		PreparedStatement pstmt = DatabaseConnection.conn.prepareStatement("SELECT * FROM rankboard ORDER BY time, steps;");
		ResultSet rs = pstmt.executeQuery();
		Vector<RankRecord> result = new Vector<RankRecord>();
		while (rs.next()) {
			String name = rs.getString("name");
			String time = rs.getString("time");
			int steps = rs.getInt("steps");
			result.add(new RankRecord(name, time, steps));
		}
		pstmt.close();
		rs.close();
		return result;
	}
	
	public static void disconnect() throws Exception {
		DatabaseConnection.conn.close();
	}
	
	 
}
