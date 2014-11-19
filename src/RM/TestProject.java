package RM;

import java.util.*;


//Author : Arnab

//line 129,158 : BookingID inside Booking needs to be public or a public get method.
//line 133,162 : Can't find reject permission methods in ModifyBookingInDatabase.

public class TestProject
{
	public static void main(String args[]) throws NumberFormatException, InvalidTimeException
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
				System.out.println("\n\n1 : Book Room\n2 : Cancel Room\n3 : Check Status\n4 : Exit");
				else
					System.out.println("\n\n1 : Book Room\n2 : Cancel Room\n3 : Check Status\n4 : Check Pending Requests \n5 : Exit");
			
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
					System.out.println("\nYou have chosen new booking.");
										
					System.out.println("\nEnter date in YYYY-MM-DD format: ");
					s3 = scan.nextLine();
					
					System.out.println("\nEnter Start time : ");
					 s4 = scan.nextLine();
					
					
					System.out.println("\nEnter End time : ");
					s5 = scan.nextLine();
					
					System.out.println("\nEnter Room size : ");
					s6 = scan.nextLine();
					
					System.out.println("\nEnter Reason : ");
					s7 = scan.nextLine();
					
					System.out.println("\nProjector Required y/n?");
					boolean projectorReqd;
					s8 = scan.nextLine();
					if(s8.equals("y")||s8.equals("Y")) projectorReqd = true;
					else projectorReqd = false;
					
					
					
				Details details;
				try {
					details = new Details(s1, s3, s4, s5, Integer.parseInt(s6), user.getPrivilege(), s7);
							
				
					ModifyBookingsInDatabase.addToWaitlist(details, projectorReqd);
					
					//System.out.println("\nYour booking ID is " + bookingID);
				} catch (InvalidTimeException e) {
					//Insert code to reset startTime/EndTime
				}
					System.out.println(s3);
					break;
					
				//cancel booking	
				case 2 :
					
					System.out.println("\nYou have chosen to cancel booking.\n\nEnter booking ID :");
					String cancelID = new String(scan.nextLine());
					
					System.out.println("Are you sure you want to cancel y/n?");
					String confirmation = new String(scan.nextLine());
					
					if(confirmation.equals("y")||confirmation.equals("Y")) ModifyBookingsInDatabase.deleteFromWaitlist(cancelID, s1);
					
					break;
				
				//check status
				case 3 : 
					System.out.println("\nYou have chosen to check status.\n\nEnter booking ID :");
					String checkID = new String(scan.nextLine());
					Booking[] b = ModifyBookingsInDatabase.SearchFromWaitlist("Id", checkID);
					if(b[0].bookingApproved) System.out.println("\nBooking Approved");
					else System.out.println("\nBooking Not Approved");
					break;
					
				//exit for student/faculty, check pending requests for admins.
				case 4 : 
					if(user.getPrivilege() < 3)
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
								//*********call reject permission
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
								//********call reject permission method
								System.out.println("\n1 : Permission rejected");
								break;
							default : validInput2 = false; System.out.println("Invalid. Enter 1 or 2.");
							}
						}while(!validInput2);
						}
						break;
					}
					
				//exit for admins. invalid for others.	
				case 5 : 
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
				switch(Integer.parseInt(scan.nextLine()))
				{
				case 1 : exit = false; break;
				case 2 : exit = true; break;
				default : System.out.println("\nInvalid Choice. Exiting..."); exit = true;
				}
			}
		}while(!validInput || !exit);
		System.out.println("\nLogged off");
	}
}