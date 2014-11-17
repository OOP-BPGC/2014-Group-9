package RM;

public class Trial {

	/**
	 * @param args
	 */
	  public static void main(String[] args) throws InvalidTimeException{
	   
	    	Details details = new Details("2012b4a851911G", "2014-07-08", "18:00", "19:00", 2,1, "For CEL club");
	    	Details details1 = new Details("2012b4a851912G", "2014-07-08", "15:00", "16:00", 3,1, "For CEL club");
	    	Details details2= new Details("2012b4a851913G", "2014-07-08", "16:00", "17:00", 3,2, "For CEL club");
	    	Details details3 = new Details("2012b4a851914G", "2014-07-08", "18:00", "19:00", 1,2, "For CEL club");
	    	Details details4 = new Details("2012b4a851915G", "2014-07-08", "11:00", "12:00", 4,2, "For CEL club");
	    	Details details5= new Details("2012b4a851916G", "2014-07-08", "13:00", "14:00", 5,1, "For CEL club");
	    	
	    	ModifyBookingsInDatabase.addToWaitlist(details, 1, false);
	    	ModifyBookingsInDatabase.addToWaitlist(details1, 2, false);
	    	ModifyBookingsInDatabase.addToWaitlist(details2, 3, false);
	    	ModifyBookingsInDatabase.addToWaitlist(details3, 4, false);
	    	ModifyBookingsInDatabase.addToWaitlist(details4, 5, false);
	    	ModifyBookingsInDatabase.addToWaitlist(details5, 6, false);

	//    	ModifyBookingsInDatabase.deleteFromWaitlist("0");
	//    	ModifyBookingsInDatabase.incrementNextBookingID();
//	    	Booking[] bookings = ModifyBookingsInDatabase.SearchFromWaitlist("Date", "2014-07-08");
//	    	if(bookings == null) System.out.println("No search");
//	    	System.out.println(bookings.length);
//	    	System.out.println(bookings[0].details.toString());
//	    	if(bookings[0]==null) System.out.println("Card void");
//	    	for(int i = 0; i<bookings.length && bookings[i]!=null; i++){
//	    		System.out.println(bookings[i].details.toString());
//	    	System.out.println(true);
//	    	}
	    //}
//		  //	ModifyBookingsInDatabase.ConfirmBookings(7,ClassRoom.A505);
		 	ModifyBookingsInDatabase.giveARCPermission(1);
		 	ModifyBookingsInDatabase.giveARCPermission(2);
		 	ModifyBookingsInDatabase.giveARCPermission(3);
		 	ModifyBookingsInDatabase.giveARCPermission(4);
		 	ModifyBookingsInDatabase.giveARCPermission(5);
		 	ModifyBookingsInDatabase.giveARCPermission(6);
	/*	  System.out.println(ModifyBookingsInDatabase.loginAndReturnPrivilege("CC-In-Charge", "CC-In-Charge"));
	  
	  ModifyBookingsInDatabase.registerNewUser("2012B3a7519g", "siddharth", 1);*/
	    	
//	Classroom[] temp = ModifyBookingsInDatabase.instantiateRooms();
//   	for(Classroom two : temp){
//	    		System.out.println(two.name);
//	    	}
//		  
//		  ModifyBookingsInDatabase.modifyRoomAvailability("C307", true);
		  
		  Waitlist.processClassrooms();
	  }
	  
}
