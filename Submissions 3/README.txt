CLASSES Completed are described. The ones left empty aren't complete yet.
 
Booking : Instanciated to a Booking object when a new booking is made and contains all the information related to that booking.
 
Booking2 :
 
ClassRoom :
 
Details : Stores all the resource information enterd by the user and is used to make the Booking oject.
 
InvlidTimeException :
 
LoggedInUser : Instanciated and contains all the information related to the user once the user has logged in.
 
ModifyBookingsInDatabase : Manages the Trial(Waitlist) and User Credentials tables of the database. Contains methods to modify, add, delete and search entries in the database.
 
NotLoggedInException :
 
Resource :
 
Room :
 
RoomNotAvailableException :
 
TestProject : Contains the main method which has the user interface.
 
Trial :
 
User :
 
UserPrivilegeTooLowException :
 
UserWithPrivileges :
 
Waitlist :


A Database is required according to the following guidelines. 4 tables. Trial, User_credentials, Confirmed, NextBookingID

Trial should have following columns: Id, UserId, Date, StartTime, EndTime, RoomSize, Priority, ARC_PermissionReqd, CC_PermissionReqd, Details
User_credentials has columns: Sno, Username, Password
Confirmed has columns: Id, UserId, Date, StartTime, EndTime, RoomNo
NextBookingId has two columns: SNo, Number

Drivers reqd are in the lib folder. 
		
