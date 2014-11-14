import java.util.*;
import RM.*;

//Author : Arnab

//line 129,158 : BookingID inside Booking needs to be public or a public get method.
//line 133,162 : Can't find reject permission methods in ModifyBookingInDatabase.

public class TestProject
{
	public static void main(String args[])
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
			switch(scan.nextInt())
			{
				//new booking
				case 1 : 
					System.out.println("\nYou have chosen new booking.");
										
					System.out.print("\nEnter date : ");
					String date = new String(scan.nextLine());
					
					System.out.print("\nEnter Start time : ");
					String startTime = new String(scan.nextLine());
					
					System.out.print("\nEnter End time : ");
					String endTime = new String(scan.nextLine());
					
					System.out.print("\nEnter Start size : ");
					int size = scan.nextInt();
					
					System.out.print("\nEnter Reason : ");
					String reason = new String(scan.nextLine());
					
					System.out.print("\nProjector Required y/n?");
					boolean projectorReqd;
					String s = scan.nextLine();
					if(s.equals("y")||s.equals("Y")) projectorReqd = true;
					else projectorReqd = false;
					
					Details details = new Details(s1, date, startTime, endTime, size, user.getPrivilege(), reason);
					
					int bookingID = Booking.numberOfNextBooking;
					
					ModifyBookingsInDatabase.addToWaitlist(details, bookingID, projectorReqd);
					
					System.out.println("\nYour booking ID is " + bookingID);
					
					break;
					
				//cancel booking	
				case 2 :
					
					System.out.println("\nYou have chosen to cancel booking.\n\nEnter booking ID :");
					String cancelID = new String(scan.nextLine());
					
					System.out.println("Are you sure you want to cancel y/n?");
					String confirmation = new String(scan.nextLine());
					if(confirmation.equals("y")||confirmation.equals("Y")) ModifyBookingsInDatabase.deleteFromWaitlist(cancelID);
					
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
					
					else if(user.getPrivilege() == 3) //CC incharge
					{
						System.out.println("\nYou have chosen to Check Pending Requests");
						Booking[] PendingReqs = ModifyBookingsInDatabase.SearchFromWaitlist("CC_PermissionReqd", "NULL");
						for(Booking booking : PendingReqs)
						{
						System.out.println(booking.details.toString());
						System.out.println("\n1 : Grant permission 2 : Reject permission  \n3 : Back to main menu");
						boolean validInput2;
						do
						{
							validInput2 = true; //loop controller
							switch(scan.nextInt())
							{
							case 1 : 
								ModifyBookingsInDatabase.giveCCPermission(booking.BookingID);
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
					
					else if(user.getPrivilege() == 4) //ARC incharge
					{
						System.out.println("\nYou have chosen to Check Pending Requests");
						Booking[] PendingReqs = ModifyBookingsInDatabase.SearchFromWaitlist("ARC_PermissionReqd", "NULL");
						for(Booking booking : PendingReqs)
						{
						System.out.println(booking.details.toString());
						System.out.println("\n1 : Grant permission 2 : Reject permission  \n3 : Back to main menu");
						boolean validInput2;
						do
						{
							validInput2 = true; //loop controller
							switch(scan.nextInt())
							{
							case 1 : 
								ModifyBookingsInDatabase.giveARCPermission(booking.BookingID);
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
				switch(scan.nextInt())
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
