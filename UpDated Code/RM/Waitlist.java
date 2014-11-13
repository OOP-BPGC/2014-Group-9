package RM;

import java.util.Calendar;

/**
 * 
 * @author Chirag Vartak
 * @version Luna
 *
 */
public class Waitlist {
	static WaitlistTestMethods test;
	/**
	 * The 'bookings' array is the array made from all the booking requests present in the database.
	 * Some Database Management code is required to do this.
	 */
	public static Booking[] bookings = new Booking[]{};
			// Replace the RHS with
			// code to get all bookings from the database
			// create a Booking object for each booking
			// and store them in the bookings array
	/**
	 * The time the Waitlist was last processed.
	 * By this, I mean the time at which the waitlist was last sorted and the booking requests in it were addressed.
	 */
	public static Calendar timeLastProcessed = Calendar.getInstance();
	
	Waitlist(){
		timeLastProcessed.set(1970, Calendar.JANUARY, 1, 0, 0, 0);
		Waitlist.sort();
	}
	
	/**
	 * This method is the heart of the Waitlist class.
	 * It addresses the booking requests, considering clashes, privilege levels etc.
	 */
	public static void processClassrooms(){
		for(int i=0; i<bookings.length-1; i++){
			
			if(bookings[i].bookingAddressed == false){
			
				bookings[i].bookingApproved = true;
				bookings[i].bookingAddressed = true;
				
				for(int j=i+1; j<bookings.length; j++){
					
					if(isClash(bookings[i], bookings[j]) == true){
						bookings[j].bookingApproved = false;
						bookings[j].bookingAddressed = true;
					}
				}
			}
		}
		timeLastProcessed = Calendar.getInstance();
	}
	
	public static void setUpWaitlistTestMethods(WaitlistTestMethods test){
		Waitlist.test = test;
	}
	// Required methods
	
	/**
	 * Checks whether 'booking1' clashes with 'booking2'
	 * @param booking1
	 * @param booking2
	 * @return
	 * 
	 */
	public static boolean isClash(Booking booking1, Booking booking2){
		//
		return test.isClash(booking1, booking2);
	}
	
	/** Sorts the waitlist.
	 * The members with higher privileges come at the top.
	 * Among a group of members with a privilege, members who requested the booking earlier come at the top.
	 * (Code not completed)
	 */
	public static void sort(){
		
	}
	
}
