package RM;

public class Trial {

	/**
	 * @param args
	 */
	  public static void main(String[] args) throws InvalidTimeException{
	    	
	    //	Details details = new Details("2012b4a8519G", "2014-07-08", "18:00", "19:00", 2,1, "For CEL club");
	    	//ModifyBookingsInDatabase.addToWaitlist(details, 8, true);
	    	//ModifyBookingsInDatabase.deleteFromWaitlist("4");
	   // 	Booking[] bookings = ModifyBookingsInDatabase.SearchFromWaitlist("Date", "2014-07-08");
	    	//if(bookings == null) System.out.println("No search");
	  //  	System.out.println(bookings.length);
	    //	System.out.println(bookings[0].details.toString());
	    	//if(bookings[0]==null) System.out.println("Card void");
	    //	for(int i = 0; i<bookings.length && bookings[i]!=null; i++){
	    	//	System.out.println(bookings[i].details.toString());
	    	//System.out.println(true);
	    //	}
	    //}
		  //	ModifyBookingsInDatabase.ConfirmBookings(7,ClassRoom.A505);
		  	ModifyBookingsInDatabase.giveARCPermission(8);
	/*	  System.out.println(ModifyBookingsInDatabase.loginAndReturnPrivilege("CC-In-Charge", "CC-In-Charge"));
	  
	  ModifyBookingsInDatabase.registerNewUser("2012B3a7519g", "siddharth", 1);*/
	  }
}
