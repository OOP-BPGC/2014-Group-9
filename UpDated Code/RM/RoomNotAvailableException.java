package RM;

public class RoomNotAvailableException extends Exception {
	RoomNotAvailableException(String a){
		super(a);
	}

	public RoomNotAvailableException() {
		super();
	}
}
