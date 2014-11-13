package RM;
public class LoggedInUser implements UserWithPrivileges{

	//Details details;
	private int privilege;
	private boolean loggedIn = false;
	LoggedInUserTestMethods test;
	
	public LoggedInUser(String username, String password){
		int num = verifyLogin(username, password);
		if(num >0) loggedIn = true;
		privilege = num;
	}
	public void setLoggedInUserTestMethods(LoggedInUserTestMethods test){
		this.test = test;
	}

	public int verifyLogin(String userName, String pwd) {
		return ModifyBookingsInDatabase.loginAndReturnPrivilege(userName, pwd);
	}

	public void modifyBooking(Details details, boolean projectorReqd) throws NotLoggedInException  {
		if(!loggedIn) throw new NotLoggedInException();
		// TODO Auto-generated method stub
		ModifyBookingsInDatabase.addToWaitlist(details, Booking.numberOfNextBooking, projectorReqd);
	}

	public void checkPendingBookingRequets(String bookingID) throws NotLoggedInException{
		if(!loggedIn) throw new NotLoggedInException();
		
		// TODO Auto-generated method stub
		
		
		//checks bookingID in database, and returns appropriate message
	}
	
	public void modifyAvailableRooms(Room[] rooms) throws NotLoggedInException,
												UserPrivilegeTooLowException {
		if(!loggedIn) throw new NotLoggedInException();
		if(privilege!=3) throw new UserPrivilegeTooLowException();
		//Room.modifyAvailability(false);
	}
	
	public void setLoggedIn(boolean a){
		loggedIn = a;
	}
	
	public void getPendingPermissionRequests() throws NotLoggedInException,
						UserPrivilegeTooLowException {
		if(!loggedIn) throw new NotLoggedInException();
		if(privilege<2) throw new UserPrivilegeTooLowException();
	}
	
	public void modifyPermissionRequestStatus()throws //NotLoggedInException,
									UserPrivilegeTooLowException {
		//if(!loggedIn) throw new NotLoggedInException();
		if(privilege<2) throw new UserPrivilegeTooLowException();
	}

	public int getPrivilege() {
		return privilege;
		//should return zero if no modification privileges have been lent to it
		
	}
	
	public void setPrivilege(int a){
		privilege = a;
	}
	
}
