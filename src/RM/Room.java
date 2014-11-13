package RM;
// Author :Siddharth
public class Room implements Resource{
	private RoomTestMethods roomTest;
	private ClassRoom name;
	private int size;	
	//protected int numberOfAttemptedBookings = 0;
	private boolean availability=true;
	public Room(int size) throws RoomNotAvailableException{
		if(size>4) throw new RoomNotAvailableException();
		this.size = size;
	}
	public void setRoomTestMethods(RoomTestMethods test){
		roomTest = test;
	}
//	@Override
	public boolean isBooked() throws RoomNotAvailableException{
		if(!availability) throw new RoomNotAvailableException("Room Closed for bookings");
		else return roomTest.isBooked();
		
		
	}

	@Override
	public void modifyAvailability(boolean isAvailable)  {
		if(isAvailable) {
			System.out.println("Setting room to be available");
			availability = true;
		}
		else {
			System.out.println("Setting room to be closed for bookings");
			availability = false;
		}
		// TODO Auto-generated method stub

	}

}
