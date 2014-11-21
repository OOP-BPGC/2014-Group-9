package RM;

import java.util.*;


//Author : Arnab


public class UserInterface
{
	public static void main(String args[]) throws Exception
	{
		
		Scanner scan = new Scanner(System.in);
		
		String s1; //stores userID
		String s2; //stores password
		int a; //invalid login loop controller
		
		do{
		System.out.print("\nUsername : ");
		s1 = scan.nextLine();
		System.out.print("Password : ");
		s2 = scan.nextLine();
		LoggedInUser user = new LoggedInUser(s1,s2);
		a = user.verifyLogin(s1,s2);
		if(a == -1) System.out.println("\nInvalid password for the username");
		if(a == -2) System.out.println("\nInvalid username");
		}while(a < 0);
		
		LoggedInUser user = new LoggedInUser(s1,s2);
			
		System.out.println("\nYou have successfully logged in");
		
		/**
		 * loop controllers
		 */
		boolean validInput;
		boolean exit;
		
		do{
			if(user.getPrivilege() < 3)
				System.out.println("\n\n1 : Book Room\n2 : Cancel Room\n3 : Check Status\n4 : Check Availability\n5 : Exit");
			else if(user.getPrivilege() < 5)
				System.out.println("\n\n1 : Book Room\n2 : Cancel Room\n3 : Check Status\n4 : Check Availability\n5 : Check Pending Requests \n6 : Exit");
			else
				System.out.println("\n\n1 : Register new user\n2 : Check Rooms\n3 : Close room\n4 : Process Waitlist\n5 : Exit");
			
			validInput = true;
			exit = false;
			System.out.println("\nEnter choice : ");
			String s3; //stores users date
			String s4; //stores Start time
			String s5; //stores End time
			String s6; //stores Room size
			String s7; //stores reason
			String s8; //stores projector reqd
			switch(Integer.parseInt(scan.nextLine()))
			{
				//new booking
				case 1 : 
					
					boolean v;
					if(user.getPrivilege() == 5)
					{
						System.out.println("Registering new user\n");
						System.out.print("\nUsername : ");
						String string1 = scan.nextLine();
						System.out.print("Password : ");
						String string2 = scan.nextLine();
						int a1;
						do{
						System.out.print("Privilege : ");
						a1 = Integer.parseInt(scan.nextLine());
						if(a1<1 || a1>4) System.out.println("Enter a valid privilege number.\n");
						}while(a1<1 || a1>4);
						ModifyBookingsInDatabase.registerNewUser(string1, string2, a1);
						System.out.println("User registered successfully.");
					}
					else
					{
					System.out.println("\nYou have chosen new booking.");
										
					do{
					System.out.println("\nEnter date in YYYY-MM-DD format: ");
					s3 = scan.nextLine();
					if(!s3.matches("[1-9][0-9][0-9][0-9]-([0][1-9]||[1][0-2])-([0][1-9]||[1-2][0-9]||[3][0-1])")) System.out.println("Enter in correct format.");
					}while(!s3.matches("[1-9][0-9][0-9][0-9]-([0][1-9]||[1][0-2])-([0][1-9]||[1-2][0-9]||[3][0-1])"));
					
					do{
					System.out.println("\nEnter Start time : ");
					 s4 = scan.nextLine();
					 if(!s4.matches("([01][0-9]||[2][0-3]):[0-5][0-9]")) System.out.println("Enter in correct format.");
					}while(!s4.matches("([01][0-9]||[2][0-3]):[0-5][0-9]"));
					
					do{
						System.out.println("\nEnter End time : ");
						s5 = scan.nextLine();
						 if(!s5.matches("([01][0-9]||[2][0-3]):[0-5][0-9]")) System.out.println("Enter in correct format.");
						 if(s4.compareTo(s5)>=0) System.out.println("End time must be after start time.");
						}while(!s5.matches("([01][0-9]||[2][0-3]):[0-5][0-9]") || s4.compareTo(s5)>=0);
					
					do{
					System.out.println("\nEnter Room size : 1,2,3 or 4");
					s6 = scan.nextLine();
					if(Integer.parseInt(s6)<1 || Integer.parseInt(s6)>4) System.out.println("Enter valid size.");
					}while(Integer.parseInt(s6)<1 || Integer.parseInt(s6)>4);
						
					System.out.println("\nEnter Reason : ");
					s7 = scan.nextLine();
					
					boolean projectorReqd;
					do{
						v = true;
					System.out.println("\nProjector Required y/n?");
					s8 = scan.nextLine();
					if(s8.equals("y")||s8.equals("Y")) projectorReqd = true;
					else projectorReqd = false;
					if(!(s8.equals("y")||s8.equals("Y")||s8.equals("n")||s8.equals("N")))
					{
						System.out.println("Inalid Input. Enter y/n?");
						v = false;
					}
					}while(!v);
					
					
				Details details;
				try {
					details = new Details(s1, s3, s4, s5, Integer.parseInt(s6), user.getPrivilege(), s7);
				
					
					ModifyBookingsInDatabase.addToWaitlist(details, projectorReqd);
					
			
				} catch (InvalidTimeException e) {
					//Insert code to reset startTime/EndTime
				}}
				break;
					
				//cancel booking	
				case 2 :
					
					if(user.getPrivilege() == 5)
					{
						System.out.println("You have chosen to check rooms\n");
						ModifyBookingsInDatabase.checkRooms();
					}
					
					else{
					System.out.println("\nYou have chosen to cancel booking.\n\nEnter booking ID :");
					String cancelID = new String(scan.nextLine());
					String confirmation;
					do{
					v = true;
					System.out.println("Are you sure you want to cancel y/n?");
					confirmation = scan.nextLine();
					if(confirmation.equals("y")||confirmation.equals("Y"))
					{
						System.out.println("Cancelling booking...");
						ModifyBookingsInDatabase.deleteFromWaitlist(cancelID, s1);
					}
					if(!(confirmation.equals("y")||confirmation.equals("Y")||confirmation.equals("n")||confirmation.equals("N")))
					{
						System.out.println("Inalid Input.");
						v = false;
					}
					}while(!v);
					}
					break;
				
				//check status
				case 3 : 
					
					if(user.getPrivilege() == 5)
					{
						boolean open = false;
						boolean validroom;
						String enteredroom;
						do{
						System.out.println("You have chosen to close room\nEnter room : ");
						String[] rooms = ModifyBookingsInDatabase.getRoomNames(); 
						enteredroom = new String(scan.nextLine());
						validroom = false;
						for(String s : rooms)
						{	
							
							if(s.equalsIgnoreCase(enteredroom))
							{
							enteredroom = enteredroom.toUpperCase();
							validroom = true;
							boolean v3;
							do{
								System.out.println("1 : Close Room 2 : Open Room");
								v3 = true;
								int value = Integer.parseInt(scan.nextLine());
								if(value == 1) open = false; 
								else if(value == 2) open = true;
								else {System.out.println("Invalid Input"); v3 = false;}
							}while(!v3);
							}
						}
						if(!validroom) System.out.println("Enter a valid room name");
						}while(!validroom);
						ModifyBookingsInDatabase.modifyRoomAvailability(enteredroom,  open);
						if(open) System.out.println("Room open");
						else System.out.println("Room closed");
					}
					else{
						System.out.println("\nYou have chosen to check status.\n\nEnter booking ID :");
						String checkID = new String(scan.nextLine());
						Booking[] b = ModifyBookingsInDatabase.SearchFromWaitlist("Id", checkID);
						boolean inConfirmed = ModifyBookingsInDatabase.SearchFromConfirmed(checkID);
						if(inConfirmed){
						 System.out.println("\nBooking Confirmed");
						}
						else if(b.length==1){ System.out.println("\nBooking Still on Waitlist");
						}
						else{ System.out.println("\nBooking rejected by In-Charges or Invalid Booking ID");
						}
					}
					break;
					
				case 4 :
					if(user.getPrivilege() == 5)
					{
						System.out.println("\nProcessing WaitList...");
						String s30;
						do{
							System.out.println("\nEnter date in YYYY-MM-DD format: ");
							s30 = scan.nextLine();
							if(!s30.matches("[1-9][0-9][0-9][0-9]-([0][1-9]||[1][0-2])-([0][1-9]||[1-2][0-9]||[3][0-1])")) System.out.println("Enter in correct format.");
						}while(!s30.matches("[1-9][0-9][0-9][0-9]-([0][1-9]||[1][0-2])-([0][1-9]||[1-2][0-9]||[3][0-1])"));
						Waitlist.processClassrooms(s30);
					}
					else
					{
					System.out.println("\nYou have chosen to check availibilty.\n");
					String date;
					do{
						System.out.println("\nEnter date in YYYY-MM-DD format: ");
						date = scan.nextLine();
						if(!date.matches("[1-9][0-9][0-9][0-9]-([0][1-9]||[1][0-2])-([0][1-9]||[1-2][0-9]||[3][0-1])")) System.out.println("Enter in correct format.");
						}while(!date.matches("[1-9][0-9][0-9][0-9]-([0][1-9]||[1][0-2])-([0][1-9]||[1-2][0-9]||[3][0-1])"));
					ModifyBookingsInDatabase.checkAvailability(date);
					}
					break;
					
				//exit for student/faculty/admin, check pending requests for cc/arc.
				case 5 : 
					if(user.getPrivilege() < 3 || user.getPrivilege() == 5)
					{System.out.println("\nExiting...");
					exit = true;
					break;}
					
					else if(user.getPrivilege() == 4) //CC incharge
					{	System.out.println("Logged in as CC in charge");
						System.out.println("\nYou have chosen to Check Pending Requests");
						Booking[] PendingReqs = ModifyBookingsInDatabase.SearchFromWaitlist("CC_PermissionReqd", "False");
						for(Booking booking : PendingReqs)
						{
						System.out.println(booking.details.toString());
						System.out.println("\n1 : Grant permission 2 : Reject permission");
						boolean validInput2;
						do
						{
							validInput2 = true; //loop controller
							switch(Integer.parseInt(scan.nextLine()))
							{
							case 1 : 
								ModifyBookingsInDatabase.giveCCPermission(booking.getBookingId());
								System.out.println("\n1 : Permission granted");
								break;
							case 2 : 
								ModifyBookingsInDatabase.deleteFromWaitlist(Integer.toString(booking.getBookingId()));
								System.out.println("\n1 : Permission rejected");
								break;
							default : validInput2 = false; System.out.println("Invalid. Enter 1 or 2.");
							}
						}while(!validInput2);
						}
						break;
					}
					
					else if(user.getPrivilege() == 3) //ARC incharge
					{
						System.out.println("\nYou have chosen to Check Pending Requests");
						Booking[] PendingReqs = ModifyBookingsInDatabase.SearchFromWaitlist("ARC_PermissionReqd", "NULL");
						for(Booking booking : PendingReqs)
						{
						System.out.println(booking.details.toString());
						System.out.println("\n1 : Grant permission 2 : Reject permission ");
						boolean validInput2;
						do
						{
							validInput2 = true; //loop controller
							switch(Integer.parseInt(scan.nextLine()))
							{
							case 1 : 
								ModifyBookingsInDatabase.giveARCPermission(booking.getBookingId());
								System.out.println("\n1 : Permission granted");
								break;
							case 2 : 
								ModifyBookingsInDatabase.deleteFromWaitlist(Integer.toString(booking.getBookingId()));
								System.out.println("\n1 : Permission rejected");
								break;
							default : validInput2 = false; System.out.println("Invalid. Enter 1 or 2.");
							}
						}while(!validInput2);
						}
						break;
					}
					
				//exit for admins. invalid for others.	
				case 6 : 
					if(user.getPrivilege() < 3)
					{
						validInput = false;
						  System.out.println("Invalid number. Enter again.");
						  break;
					}
					else
					{
						System.out.println("\nExiting...");
						exit = true;
						break;
					}
				
				//invalid input.
				default : validInput = false;
						  System.out.println("Invalid number. Enter again.");
						  
			}
	
			if(validInput && !exit)
			{
				System.out.println("\nContinue Booking? 1 : Yes 2 : No");
				boolean validinput3;
				do{
				validinput3 = true;
				switch(Integer.parseInt(scan.nextLine()))
				{
				case 1 : exit = false; break;
				case 2 : exit = true; break;
				default : System.out.println("\nInvalid Choice. Enter 1 or 2."); validinput3 = false;
				}}while(!validinput3);
			}
		}while(!validInput || !exit);
		System.out.println("\nLogged off");
	}
}