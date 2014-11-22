package RM;

public class Booking {
		public Details details;
		private int BookingId; 
		
		public boolean bookingApproved = false;
		public boolean bookingAddressed = false;
		public Classroom classroom = null;
		
		public Booking(Details details, int id){
			this.BookingId = id;
			this.details = details;
		}

		public int getBookingId() {
			return BookingId;
		}

		public void setBookingId(int bookingId) {
			BookingId = bookingId;
		}
}
