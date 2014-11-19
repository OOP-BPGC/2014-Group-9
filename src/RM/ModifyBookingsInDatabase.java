package RM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ModifyBookingsInDatabase {

	/**This method adds a new row to the database. If projectorPermission is not reqd, then 
	 * it is automatically set as null in the database. If required, it is set as false.So that it can be 
	 * changed to True once CC in charge gives permission.
	 * Automatically, the ARC permission is set to be false. And then it should be manually changed to True by
	 * ARC InCharge
	 * Booking ID must be tracked by some method and should be fed into the database.
	 * 
	 * @param details Details of the booking. 
	 * @param BookingID ID number of the booking. Should be incremented automatically
	 * @param projectorReqd Whether Projector is required or not. True = needed
	 */
	public static void addToWaitlist(Details details, int BookingID, boolean projectorReqd) {
		Connection conn2 = null;
		Statement st2 = null;
				
		String cs = "jdbc:mysql://localhost:3306/RM";
        String user2 = "sqluser3";
        String password = "sqluserpw";
      
        
        try{
        	conn2 = DriverManager.getConnection(cs, user2, password);
        	st2 = conn2.createStatement();
        	
        	String query = projectorReqd? "Insert into Trial(Id, UserId, Date, StartTime," +
        			" EndTime, RoomSize, Priority, ARC_PermissionReqd, CC_PermissionReqd, Reason) " +
        			"VALUES(" + Integer.toString(BookingID)+ ", '" + details.getUserID() + "', '" + 
        			details.getDate() + "', '" + details.getStartTime() + "', '" +
        			details.getEndTime() + "', " + details.getRoomSize() + "," + details.getPriority()+
        			", false," + Boolean.toString(!projectorReqd) + ", '" + details.getReason() + "')" :
    				"Insert into Trial(Id, UserId, Date, StartTime, EndTime, RoomSize, " +
    				"Priority, ARC_PermissionReqd, Reason) VALUES(" + 
    				Integer.toString(BookingID)+ ", '" + details.getUserID() + "', '" + 
        			details.getDate() + "', '" + details.getStartTime() + "', '" +
        			details.getEndTime() + "', " + details.getRoomSize() + "," + details.getPriority()+
        			", false, '" + details.getReason() + "')";
        	System.out.println(query);
        	st2.execute(query);		
        }catch (SQLException ex) {
	        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	        lgr.log(Level.SEVERE, ex.getMessage(), ex);
	    }finally{
	    	try{
	    		if(st2 != null) st2.close();
	    		if(conn2 != null) conn2.close();
	    	}catch(SQLException ex){
	    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
	    	}
	    	incrementNextBookingID();
	    }
	}
	
	/**Deletes the booking from the Waitlist database given its booking ID number. 
	 * 
	 * @param Id Booking ID number must be inputted
	 */
    public static void deleteFromWaitlist(String Id) {
    		Connection conn = null;
    		Statement st = null;
    		
    		String cs = "jdbc:mysql://localhost:3306/RM";
            String user = "sqluser2";
            String password = "sqluserpw";
            
            
            try{
            	conn = DriverManager.getConnection(cs, user, password);
            	st = conn.createStatement();
            	
            	String query = "Delete from Trial where Id = " + Id;
            	st.execute(query);
	    	}catch (SQLException ex) {
	            Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	            lgr.log(Level.SEVERE, ex.getMessage(), ex);
	        }finally{
	        	try{
	        		if(st != null) st.close();
	        		if(conn != null) conn.close();
	        	}catch(SQLException ex){
	        		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	                 lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
	        	}
	        }
    	}
    	/**Searches the Waitlist database for the Row containing the Key in the column containing the SearchBy string
    	 * All the Rows which are returned are converted to a Booking object and returned as an Array
    	 * 
    	 * @param SearchBy  Column Name in String format
    	 * @param Key Cell Value in String Format
    	 * @return Returns Array of Booking Object as output of search
    	 * @throws NumberFormatException 
    	 * @throws InvalidTimeException
    	 */
    public static Booking[] SearchFromWaitlist(String SearchBy, String Key)
    		throws NumberFormatException, InvalidTimeException{
    	Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Booking[] temp = new Booking[100]; 
		
		String cs = "jdbc:mysql://localhost:3306/RM";
        String user = "sqluser2";
        String password = "sqluserpw";
        
		 try{
            	conn = DriverManager.getConnection(cs, user, password);
            	st = conn.createStatement();
            	
            	String query = (SearchBy=="CC_PermissionReqd")||
            					(SearchBy=="Priority") ||
            					(SearchBy.equals("Id")) || 
            					(SearchBy=="RoomSize")? 
            					"Select * from Trial where " + SearchBy +
            					" = " + Key:
            					"Select * from Trial where " + SearchBy+" = '" + Key +"'"; 
            	System.out.println(query);
            	rs = st.executeQuery(query);
            	if(rs==null) System.out.println("No return");
            	temp = compileBookings(rs);
            	int i = 0;
            	while(temp[i]!= null){
            		i++;
            	}
            	Booking[] returnable = new Booking[i];
            	for(int j = 0; j<i; j++){
            		returnable[j] = temp[j];
            	}
            	
            	return returnable;
		 }catch (SQLException ex) {
	            Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	            lgr.log(Level.SEVERE, ex.getMessage(), ex);
		        }finally{
		        	try{
		        		if(st != null) st.close();
		        		if(conn != null) conn.close();
		        	}catch(SQLException ex){
		        		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.
		        				 class.getName());
		                 lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
		        	}
		        }
		return temp;
		 
    }
    
    private static Booking[] compileBookings(ResultSet rs) 
	    		throws SQLException, NumberFormatException, InvalidTimeException{
	    	Booking[] temp = new Booking[100];
	    	int count = 0;
	    	while(rs.next()){	    		
	    		Details details = new Details(rs.getString("UserId"), rs.getString("Date"),
	    				rs.getString("StartTime"), rs.getString("EndTime"), 
	    				Integer.parseInt(rs.getString("RoomSize")), Integer.parseInt(rs.getString("Priority")), rs.getString("Reason"));
	    		Booking card = new Booking(details, Integer.parseInt(rs.getString("Id")));
	    		temp[count++] = card;
	    	}
	    	return temp;
	    }
    
    public static void ConfirmBookings(int BookingID, String room) throws NumberFormatException, InvalidTimeException{
	  Booking[] temp = SearchFromWaitlist("Id", Integer.toString(BookingID));
	  assert(temp.length==1);
	  addToConfirmed(temp[0],BookingID, room);
	  deleteFromWaitlist(Integer.toString(BookingID));
  }
  
    private static void addToConfirmed(Booking booking, int BookingID, String room){
	 	Connection conn2 = null;
		Statement st2 = null;
				
		String cs = "jdbc:mysql://localhost:3306/RM";
        String user2 = "sqluser3";
        String password = "sqluserpw";
      
        
        try{
        	conn2 = DriverManager.getConnection(cs, user2, password);
        	st2 = conn2.createStatement();
        	
        	String query = "Insert into Confirmed (BookingId, UserId, Date, StartTime," +
        			"EndTime, Room) VALUES (" + Integer.toString(BookingID) + ", '" +
        			booking.details.getUserID() + "', '" + booking.details.getDate() +
        			"', '" + booking.details.getStartTime() +"', '" + booking.details.getEndTime() +
        			"', '" + room + "')";
        	
        	st2.execute(query);
        }catch (SQLException ex) {
            Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }finally{
        	try{
        		if(st2 != null) st2.close();
        		if(conn2 != null) conn2.close();
        	}catch(SQLException ex){
        		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
                 lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
        	}
        }
	}
  
    public static void giveARCPermission(int BookingID){
	  	Connection conn = null;
		Statement st = null;
		
		String cs = "jdbc:mysql://localhost:3306/RM";
        String user = "sqluser2";
        String password = "sqluserpw";
        
        try{
        	conn = DriverManager.getConnection(cs, user, password);
        	st = conn.createStatement();
        	
        	String query = "update Trial SET ARC_PermissionReqd = 1 where Id = " + 
        	Integer.toString(BookingID);
        	
        	st.execute(query);
        }catch (SQLException ex) {
	        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	        lgr.log(Level.SEVERE, ex.getMessage(), ex);
	    }finally{
	    	try{
	    		if(st != null) st.close();
	    		if(conn != null) conn.close();
	    	}catch(SQLException ex){
	    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
	    	}
	    }
	}
  
    public static void giveCCPermission(int BookingID){
	  Connection conn = null;
	  Statement st = null;
	
	  String cs = "jdbc:mysql://localhost:3306/RM";
      String user = "sqluser2";
      String password = "sqluserpw";
      
      try{
      	conn = DriverManager.getConnection(cs, user, password);
      	st = conn.createStatement();
      	
      	String query = "update Trial SET CC_PermissionReqd = 1 where Id = " + 
      	Integer.toString(BookingID);
      	
      	st.execute(query);
      }catch (SQLException ex) {
	        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	        lgr.log(Level.SEVERE, ex.getMessage(), ex);
	  }finally{
	    	try{
	    		if(st != null) st.close();
	    		if(conn != null) conn.close();
	    	}catch(SQLException ex){
	    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
	    	}
	    }
}

    public static int loginAndReturnPrivilege(String username, String password){
		  
		  Connection conn = null;
		  Statement st = null;
		  ResultSet rs = null;
		  
		  String cs = "jdbc:mysql://localhost:3306/RM";
	      String user = "sqluser2";
	      String pwd = "sqluserpw";
	      
	      try{
	      	conn = DriverManager.getConnection(cs, user, pwd);
	      	st = conn.createStatement();
	      	
	      	String query = "Select * from User_credentials where Username = '" + username 
	      			+ "'";
	      	rs = st.executeQuery(query);
	      	 if(rs != null && rs.next()){
	                rs.first();
		      		if(!password.equals(rs.getString("Password"))) return -1;
		      		rs.first();
		      		return Integer.parseInt(rs.getString("Privilege"));
		      			
	         }	        
	         return -2;
	     
	      	}catch (SQLException ex) {
		        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
		        lgr.log(Level.SEVERE, ex.getMessage(), ex);
			  }finally{
			    	try{
			    		if(st != null) st.close();
			    		if(conn != null) conn.close();
			    	}catch(SQLException ex){
			    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
			             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
			    	}
			    }
	      return -2;
	  }

    public static int registerNewUser(String username, String password, int privilege){
    	  Connection conn = null;
		  Statement st = null;
		  ResultSet rs = null;
		  
		  String cs = "jdbc:mysql://localhost:3306/RM";
	      String user = "sqluser2";
	      String pwd = "sqluserpw";
	      
	      try{
	      	conn = DriverManager.getConnection(cs, user, pwd);
	      	st = conn.createStatement();
	      	
	      	String query = "Select * from User_credentials where Username = '" + username 
	      			+ "'";
	      	rs = st.executeQuery(query);
	      	if(rs != null && rs.next()){
	              return -1;
	         }	        
	        query = "Insert into User_credentials(Username, Password, Privilege) VALUES (' " +
	        		username + "', '" + password + "', " + Integer.toString(privilege) +")";
	        st.execute(query);
	        return 0;
		    }catch (SQLException ex) {
		        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
		        lgr.log(Level.SEVERE, ex.getMessage(), ex);
			  }finally{
			    	try{
			    		if(st != null) st.close();
			    		if(conn != null) conn.close();
			    	}catch(SQLException ex){
			    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
			             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
			    	}
			    }
	      	return 1;
    }
    
    public static int getNextBookingID() {
    	Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		  
		String cs = "jdbc:mysql://localhost:3306/RM";
	    String user = "sqluser2";
	    String pwd = "sqluserpw";
	    
    try{
    	conn = DriverManager.getConnection(cs, user, pwd);
    	st = conn.createStatement();
    	
    	String query = "Select * from nextBookingID where SNo = 1";
       	rs = st.executeQuery(query);
    	rs.next();
       	return Integer.parseInt(rs.getString("Number"));
    }catch (SQLException ex) {
	        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	        lgr.log(Level.SEVERE, ex.getMessage(), ex);
		  }finally{
		    	try{
		    		if(st != null) st.close();
		    		if(conn != null) conn.close();
		    	}catch(SQLException ex){
		    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
		             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
		    	}
		    }
	return 0;    	
    }

    private static void incrementNextBookingID() {
    	int current = getNextBookingID();
    	int next = current +1;
    	
    	 Connection conn = null;
		 Statement st = null;
		 
		 String cs = "jdbc:mysql://localhost:3306/RM";
	     String user = "sqluser2";
	     String pwd = "sqluserpw";
	     
	     try{
	      	conn = DriverManager.getConnection(cs, user, pwd);
	      	st = conn.createStatement();
	      	
	      	String query = "update nextBookingID SET Number = " + Integer.toString(next) + " where SNo = 1";
	      	st.execute(query);
		    }catch (SQLException ex) {
		        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
		        lgr.log(Level.SEVERE, ex.getMessage(), ex);
		  }finally{
		    	try{
		    		if(st != null) st.close();
		    		if(conn != null) conn.close();
		    	}catch(SQLException ex){
		    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
		             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
		    	}
		    }
	      
    }
    
    public static Classroom[] instantiateRooms(){
    	 Connection conn = null;
		 Statement st = null;
		 ResultSet rs = null;
		 Classroom[] temp = new Classroom[31];
		 String cs = "jdbc:mysql://localhost:3306/RM";
	     String user = "sqluser2";
	     String pwd = "sqluserpw";
	     
	     try{
		      	conn = DriverManager.getConnection(cs, user, pwd);
		      	st = conn.createStatement();
		      	
		      	String query = "select * from Classrooms";
		      	rs = st.executeQuery(query);
		      	
		      	while(rs.next()){
		      		int id = Integer.parseInt(rs.getString("id"));
		      		int size = Integer.parseInt(rs.getString("size"));
		      		String name = rs.getString("name");
		      		boolean open = rs.getBoolean("open");
		      		
		      		temp[id-1] = new Classroom(id,size,name,open);
		      	}
		      	
		      	return temp;
	     }catch (SQLException ex) {
		        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
		        lgr.log(Level.SEVERE, ex.getMessage(), ex);
		  }finally{
		    	try{
		    		if(st != null) st.close();
		    		if(conn != null) conn.close();
		    	}catch(SQLException ex){
		    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
		             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
		    	}
		    }
		return temp;
    }

    public static void modifyRoomAvailability(String room, boolean open){
    	
    	Connection conn = null;
  	    Statement st = null;
  	    int temp = open?1:0;
  	
  	    String cs = "jdbc:mysql://localhost:3306/RM";
        String user = "sqluser2";
        String password = "sqluserpw";
        
        try{
        	conn = DriverManager.getConnection(cs, user, password);
        	st = conn.createStatement();
        	
        	String query = "update Classrooms SET open = " + temp + " where name = '" + room + "'";
        	st.execute(query);
        }catch (SQLException ex) {
	        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	        lgr.log(Level.SEVERE, ex.getMessage(), ex);
	    }finally{
	    	try{
	    		if(st != null) st.close();
	    		if(conn != null) conn.close();
	    	}catch(SQLException ex){
	    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
	    	}
	    }
    }
    
    public static void checkAvailability(String date){
      	 System.out.println("For the " + date);
    	 System.out.println("Room Size                |        Number of Rooms Available    | Number of Bookings made");
    	for(int i = 1; i<6; i++){
    		checkAvailability(i,date);
    	}
    }
    
    private static void checkAvailability(int Size, String date){
    	Connection conn = null;
  	    Statement st = null;
  	  
  	    String cs = "jdbc:mysql://localhost:3306/RM";
        String user = "sqluser2";
        String password = "sqluserpw";
        int max = getMax(Size);
        try{
        	conn = DriverManager.getConnection(cs, user, password);
        	st = conn.createStatement();
        	
        	String query = "Select Count(RoomSize) As RoomSize From Trial Where RoomSize = " + Integer.toString(Size) + " AND "
        			+ "Date = '" + date + "'";
        	ResultSet rs = st.executeQuery(query);
        	while(rs.next()){
        		System.out.println("           " + Integer.toString(Size) + "            |            " +
        				Integer.toString(max) + "                       |                 " + rs.getString("RoomSize"));
        	}
        	
        }catch (SQLException ex) {
    	        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
    	        lgr.log(Level.SEVERE, ex.getMessage(), ex);
	    }finally{
	    	try{
	    		if(st != null) st.close();
	    		if(conn != null) conn.close();
	    	}catch(SQLException ex){
	    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
	             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
	    	}
	    }
    	
    }
    
    private static int getMax(int size){
	    	Connection conn = null;
	  	    Statement st = null;
	  	  
	  	    String cs = "jdbc:mysql://localhost:3306/RM";
	        String user = "sqluser2";
	        String password = "sqluserpw";
	        
	        try{
	        	conn = DriverManager.getConnection(cs, user, password);
	        	st = conn.createStatement();
	        	
	        	String query = "Select Count(Size) AS Count From Classrooms where Size = " + Integer.toString(size) + " AND Open = 1";
	        	ResultSet rs = st.executeQuery(query);
	        	
	        	while(rs.next()){
	        		return Integer.parseInt(rs.getString("Count"));
	        	}
	        }catch (SQLException ex) {
    	        Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
    	        lgr.log(Level.SEVERE, ex.getMessage(), ex);
		    }finally{
		    	try{
		    		if(st != null) st.close();
		    		if(conn != null) conn.close();
		    	}catch(SQLException ex){
		    		 Logger lgr = Logger.getLogger(ModifyBookingsInDatabase.class.getName());
		             lgr.log(Level.SEVERE, ex.getMessage(), ex);        	
		    	}
		    }
			return -1;
    }
}
