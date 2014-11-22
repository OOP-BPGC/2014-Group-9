package RM;
public interface User {

	//to allow all the confirmed bookings to be seen
	//called only when user wishes to see the confirmed rooms
	//no return type required
	public boolean viewBookings();
}
