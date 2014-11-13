package RM;

public class Booking {
		public Details details;
		private int BookingId; 
		public static int numberOfNextBooking = 0;
		public boolean bookingApproved = false;
		public boolean bookingAddressed = false;
		
		public Booking(Details details, int id){
			this.BookingId = id;
			this.details = details;
		}
}
