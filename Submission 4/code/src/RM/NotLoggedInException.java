package RM;

public class NotLoggedInException extends Exception {
	NotLoggedInException(){
		super("The user is not logged in");
	}
}
