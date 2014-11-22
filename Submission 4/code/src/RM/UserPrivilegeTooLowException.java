package RM;

public class UserPrivilegeTooLowException extends Exception {
	UserPrivilegeTooLowException(){
		super("Action not allowed for this privilege level");
	}
}
