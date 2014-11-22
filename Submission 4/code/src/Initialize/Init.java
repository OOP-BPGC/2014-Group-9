package Initialize;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Init {

	
	public static void main(String[] args) {
		if(args[0] == null) {
			System.out.println("To execute input should be of the form:");
			System.out.println("java -jar FirstUse.jar <MySQLRootPassword>");
		}
		else{
			Connection conn = null;
			Statement st = null;
			String password = args[0];
			String cs = "jdbc:mysql://localhost:3306/?user=root&password=" + password;
	       
	        
	        try{
	        	conn = DriverManager.getConnection(cs);
	        	
	        	st = conn.createStatement();
	        	String trial = "CREATE DATABASE RM";
	        	st.executeUpdate(trial);
	        	trial = "use RM";
	        	st.execute(trial);
	        	trial = "grant all privileges on RM.* to 'sqluser'@'localhost' identified by 'sqluserpw'";
	        	st.execute(trial);
	        	String query = "Create Table IF NOT EXISTS Trial(Id Int NOT NULL Auto_Increment, UserId VARCHAR(30) NOT NULL, Date DATE NOT NULL, StartTime VARCHAR(5)" +
	        				" NOT NULL, EndTime VARCHAR(5) NOT NULL, RoomSize INT NOT NULL, Priority INT NOT NULL" +
	        				", ARC_PermissionReqd BOOLEAN NOT NULL DEFAULT 0, CC_PermissionReqd BOOLEAN, Reason VARCHAR(100) NOT NULL, PRIMARY KEY (Id))";
	        	st.execute(query);
	        	
	        	query = "Create Table IF NOT EXISTS Confirmed(BookingID Int NOT NULL, UserId VARCHAR(30), Date DATE NOT NULL, StartTime VARCHAR(5) NOT NULL, EndTime VARCHAR(5) NOT NULL," +
	        			"Room VARCHAR(10) NOT NULL)";
	        	st.execute(query);
	        	
	        	query = "Create Table IF NOT EXISTS User_credentials(Id Int NOT NULL AUTO_INCREMENT, Username VARCHAR(30) NOT NULL, Password VARCHAR(30) NOT NULL, Privilege INT NOT NULL, PRIMARY KEY(Id))";
	        	st.execute(query);
	        	
	        	query = "Insert into User_credentials VALUES(1, 'admin', 'admin', 5)";
	        	st.execute(query);
	        	query = "Insert into User_credentials VALUES(2, 'CC-In-Charge', 'CC-In-Charge', 4)";
	        	st.execute(query);
	        	query = "Insert into User_credentials VALUES(3, 'ARC-In-Charge', 'ARC-In-Charge', 3)";
	        	st.execute(query);
	        	
	        	String[] classrooms = {"AUDITORIUM", "LT1","LT2", "LT3", "LT4", "A605", "A602", "C405", "C402",  "A501",
	        			"A502", "A503" , "A507", "A508", "C301", "C302", "C306", "C307",
	        			"C308", "A601", "A603", "A604", "C401", "C403", "C404", "A504", "A505", "A506", "C303", "C304", "C305"};
	        	int[] priority = {5,4,4,4,4,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1};
	        	
	        	query = "Create TABLE IF NOT EXISTS Classrooms(id INT NOT NULL AUTO_INCREMENT, size INT NOT NULL, name VARCHAR(10) NOT NULL, open BOOLEAN NOT NULL DEFAULT 1, PRIMARY KEY(id))";
	        	
	        	st.execute(query);
	        	PreparedStatement ps = conn.prepareStatement("Insert into Classrooms(size, name) VALUES(?, ?)");
	        	conn.setAutoCommit(false);
	        	for(int i = 0; i<31; i++){
	        	ps.setInt(1, priority[i]);
	        	ps.setString(2, classrooms[i]);
	        	ps.addBatch();
	        	}
	        	ps.executeBatch();
	        	conn.setAutoCommit(true);
	        }catch (SQLException ex) {
		        Logger lgr = Logger.getLogger(Init.class.getName());
		        lgr.log(Level.SEVERE, ex.getMessage(), ex);
		    }finally{
		    	try{
		    		if(st != null) st.close();
		    		if(conn != null) conn.close();
		    	}catch(SQLException ex){
		    		 Logger lgr = Logger.getLogger(Init.class.getName());
		             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
		    	}
		    	
		    }
		}
	}
	

}
