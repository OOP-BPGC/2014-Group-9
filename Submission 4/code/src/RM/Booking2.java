package RM;

// Author : Chirag Vartak
// Do not rename it before uploading.
// Not meant to replace the Booking class. Just an additional class.

// When the process() of the Waitlist runs, it import ALL the bookings in the database as an array.
// Thus, we will have a 'bookings' array that will be created in the process() method.
// Assumption: The database has the bookings listed in the order of their ids.
// Assumption: Their ids in database will be integers in ascending order. Doesn't matter if consecutive.

public class Booking2 {
	
	public int id = -1;
	public int privilege = -1;
	public boolean bookingApproved = false;
	public boolean bookingAddressed = false;
	public int size = -1;
	public String dateRequired = "noname";
	public String startTime = "noname";
	public String endTime = "noname";
	public String reason = "noname";
	public boolean projectorRequired = false;
	public String username = "noname";
	
	public Booking corrBooking = null;
	public Classroom classroom = null;
	
	public void set(Booking booking){
		id = booking.getBookingId();
		privilege = booking.details.getPriority();
		size = booking.details.getRoomSize();
		dateRequired = booking.details.getDate();
		startTime = booking.details.getStartTime();
		endTime = booking.details.getEndTime();
		reason = booking.details.getReason();
//		projectorRequired =
		username = booking.details.getUserID();
		corrBooking = booking;
	}
	
}
