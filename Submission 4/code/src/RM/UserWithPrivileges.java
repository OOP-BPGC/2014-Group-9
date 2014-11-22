package RM;
public interface UserWithPrivileges {
	
	
	
	//takes in username and password of person trying to login
	//checks in database to ensure authorized login
	public int verifyLogin(String userName, String pwd);
	
	
	//userName and pwd will be passed implicitly, by in the program itself
	//based on privilege no, the priority will be decided
	public void modifyBooking(Details details, boolean Projector_reqd) throws NotLoggedInException;
	
	
	//prints booking status
	public void checkPendingBookingRequets(String bookingID) throws NotLoggedInException;

}
