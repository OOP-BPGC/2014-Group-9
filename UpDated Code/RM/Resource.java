package RM;
// Author : Siddharth
public interface Resource {
	public boolean Availability = false;
	public boolean isBooked() throws Exception; //should tell if the Resource is booked or not
	//public Details getDetails(); //Returns the Details of who made the booking and for how long etc.
	public void modifyAvailability(boolean isAvailable); //In order to change availability
}
