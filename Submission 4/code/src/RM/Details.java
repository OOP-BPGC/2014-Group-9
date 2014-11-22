package RM;
//Author : Siddharth
import java.util.Calendar;
public class Details {
		private String userID; //UserID 
		private String date; //date of Booking
		private String startTime; //Start time in format 'HH:MM' 24 hour clock
		private String endTime; 	//end time in format 'HH:MM' 24 hour clock
		private int roomSize;
	
		private int priority;
		private String reason;
		
		public Details(){};
		/**
		 * 
		 * @param userID unique userID
		 * @param date date in YYYY-MM-DD format 
		 * @param startTime Start time of booking in HH:MM format. Else throws exception
		 * @param endTime End time of booking in HH:MM format. Else throws exception
		 * @param roomSize Size of the room. 0 = null, 1 = Small, 2 = medium, 3 = large, 4 = LT, 5 = Audi
		 * @param priority Priority value of User. 1 = student, 2 = faculty
		 * @param reason String reason for booking the room
		 * @throws InvalidTimeException Throws exception if Time format is invalid
		 */
		public Details(String userID, String date, String startTime, String endTime, int RoomSize, int priority, String reason) throws InvalidTimeException{
			this.userID = userID;		//THIS IS FOR ROOM DETAILS
			this.date = date;
			setStartTime(startTime);
			setEndTime(endTime);
			this.roomSize = RoomSize;
			this.priority = priority;
			this.reason = reason;
		}
				 
		public String getUserID() {
			return userID;
		}
		public void setUserID(String userID) {
			this.userID = userID;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) throws InvalidTimeException {
			if(!startTime.matches("([01][0-9]||[2][0-3]):[0-5][0-9]")) throw new InvalidTimeException();
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) throws InvalidTimeException {
			if(!startTime.matches("([01][0-9]||[2][0-3]):[0-5][0-9]")) throw new InvalidTimeException();
			this.endTime = endTime;
		}
		
		public int getRoomSize() {
			return roomSize;
		}
		public void setRoomSize(int roomSize) {
			this.roomSize = roomSize;
		}
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		@Override
		public String toString() {
			return "Details ["
					+ (userID != null ? "userID=" + userID + ", " : "")
					+ (date != null ? "date=" + date + ", " : "")
					+ (startTime != null ? "Start Time =" + startTime + ", " : "")
					+ (endTime != null ? "End Time=" + endTime + ", " : "")
					+ (roomSize != 0? "Room Size = " + roomSize + ", " :"")
					+ (priority !=0? "Priority Value =" + priority +", ": "")
					+(reason!=null? "Reason = " + reason : "")
					+ "]";
		}
}
