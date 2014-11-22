package RM;
// Author : Chirag Vartak
// Rename it as 'Waitlist.java' before uploading.

// 

public class Waitlist {

	// Algorithm for the below method:
	// (First read the assumptions stated in the Booking2 and Classroom classes)
	// 
	// 1. Get the relevant(for a specific date) bookings from the database and make an array of Booking objects.
	// 		First import them as Booking-objects-array. Then transfer these to Booking2-objects-array.
	// 2. Get ALL the classrooms from the database and make an array of Classroom objects.
	// Modify them in the memory itself.
	// Write the now modified classroom objects array to a SEPERATE database. (We keep the original classrooms database untouched.)
	public static void processClassrooms(String date) throws Exception{
		
		// 1
		Booking[] tempBookings = ModifyBookingsInDatabase.getBookingsFromWaitlist(date);		// Check if this works
//		System.out.println(tempBookings.length);
//		System.out.println(tempBookings[0].getBookingId());
		Booking2[] bookings = new Booking2[tempBookings.length];
		for(int i=0; i<tempBookings.length;i++){
			bookings[i] = new Booking2();
		}
		for(int i=0; i<tempBookings.length; i++){
			bookings[i].set(tempBookings[i]);
		}
		
		
		// 2
//		Classroom[] classrooms = null;	// Database code
		Classroom[] classrooms = ModifyBookingsInDatabase.instantiateRooms();

		// 3
		// Sort the 'bookings' array (consider 'privilege' and 'id').
		// After sorting, higher privileges will be at the top. Among members of a privilege, they will be in ascending order of 'id's.
		// 'classrooms' array does not need to be sorted.
		// 'classrooms' will have higher sized members at the top.
		int nElements = bookings.length;
		int highestPrivilege = bookings[0].privilege;
		int elementWithHP = 0;	// for the 0th element
		Booking2 temp = new Booking2();
		for(int i=0; i<nElements-1; i++){
			elementWithHP = i;
			highestPrivilege = bookings[i].privilege;
			for(int j=i+1; j<nElements; j++){
				if(bookings[j].privilege > highestPrivilege){
					highestPrivilege = bookings[j].privilege;
					elementWithHP = j;
				}
			}
			temp = bookings[i];
			bookings[i] = bookings[elementWithHP];
			bookings[elementWithHP] = temp;
		}
		
		int lowestId = bookings[0].id;
		int elementWithLI = 0;	// for the 0th element
		Booking2 temp2 = new Booking2();
		for(int i=0; i<nElements-1; i++){
			elementWithLI = i;
			lowestId = bookings[i].id;
			for(int j=i+1; j<nElements; j++){
				if(bookings[j].id < lowestId && bookings[i].privilege == bookings[j].privilege){
					lowestId = bookings[j].id;
					elementWithLI = j;
				}
			}
			temp2 = bookings[i];
			bookings[i] = bookings[elementWithLI];
			bookings[elementWithLI] = temp2;
		}
		
		
		// 4
		// Address the bookings
		
		// Find the number of classrooms-available and booking-requests per size-of-classroom.
		// Classroom size can vary from 1 to 5.
		int nClassroomsWithSize[] = {0,0,0,0,0,0};
		int nBookingsWithSize[] = {0,0,0,0,0,0};
		for(int i=0; i<classrooms.length; i++){
			switch (classrooms[i].size) {
			case 1:
				nClassroomsWithSize[1]++;
				break;
			case 2:
				nClassroomsWithSize[2]++;
				break;
			case 3:
				nClassroomsWithSize[3]++;
				break;
			case 4:
				nClassroomsWithSize[4]++;
				break;
			case 5:
				nClassroomsWithSize[5]++;
				break;
			default:
				break;
			}
		}
		for(int i=0; i<bookings.length; i++){
			switch (bookings[i].size) {
			case 1:
				nBookingsWithSize[1]++;
				break;
			case 2:
				nBookingsWithSize[2]++;
				break;
			case 3:
				nBookingsWithSize[3]++;
				break;
			case 4:
				nBookingsWithSize[4]++;
				break;
			case 5:
				nBookingsWithSize[5]++;
				break;
			default:
				break;
			}
		}
		
		
		// Finding the index of the first elements w.r.t. size in the 'classrooms' array.
		int size5StartsAt = classrooms.length - nClassroomsWithSize[1] - nClassroomsWithSize[2] - nClassroomsWithSize[3] -
				nClassroomsWithSize[4] - nClassroomsWithSize[5];
		int size4StartsAt = classrooms.length - nClassroomsWithSize[1] - nClassroomsWithSize[2] - nClassroomsWithSize[3] -
				nClassroomsWithSize[4];
		int size3StartsAt = classrooms.length - nClassroomsWithSize[1] - nClassroomsWithSize[2] - nClassroomsWithSize[3];
		int size2StartsAt = classrooms.length - nClassroomsWithSize[1] - nClassroomsWithSize[2];
		int size1StartsAt = classrooms.length - nClassroomsWithSize[1];
		
		
		if(nClassroomsWithSize[5] >= nBookingsWithSize[5]){
			int currentSize5Classroom = size5StartsAt;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 5){
					approveBooking(bookings[i], classrooms[currentSize5Classroom]);
					currentSize5Classroom++;
				}
			}
		}else{
			int currentSize5Classroom = size5StartsAt;
			int currentBookingOfThisSize = 1;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 5){
					if(currentBookingOfThisSize <= nClassroomsWithSize[5]){
						approveBooking(bookings[i], classrooms[currentSize5Classroom]);
						currentSize5Classroom++;
					}else{
						for(int j=size5StartsAt; j<size4StartsAt; j++){
							if(isClash(bookings[i], classrooms[j], bookings) == false){
								approveBooking(bookings[i], classrooms[j]);
								break;
							}
						}
						if(bookings[i].bookingApproved == false){
							declineBooking(bookings[i]);
						}
					}
					currentBookingOfThisSize++;
				}
			}
		}
		if(nClassroomsWithSize[4] >= nBookingsWithSize[4]){
			int currentSize4Classroom = size4StartsAt;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 4){
					approveBooking(bookings[i], classrooms[currentSize4Classroom]);
					currentSize4Classroom++;
				}
			}
		}else{
			int currentSize4Classroom = size4StartsAt;
			int currentBookingOfThisSize = 1;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 4){
					if(currentBookingOfThisSize <= nClassroomsWithSize[4]){
						approveBooking(bookings[i], classrooms[currentSize4Classroom]);
						currentSize4Classroom++;
					}else{
						for(int j=size4StartsAt; j<size3StartsAt; j++){
							if(isClash(bookings[i], classrooms[j], bookings) == false){
								approveBooking(bookings[i], classrooms[j]);
								break;
							}
						}
						if(bookings[i].bookingApproved == false){
							declineBooking(bookings[i]);
						}
					}
					currentBookingOfThisSize++;
				}
			}
		}
		if(nClassroomsWithSize[3] >= nBookingsWithSize[3]){
			int currentSize3Classroom = size3StartsAt;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 3){
					approveBooking(bookings[i], classrooms[currentSize3Classroom]);
					currentSize3Classroom++;
				}
			}
		}else{
			int currentSize3Classroom = size3StartsAt;
			int currentBookingOfThisSize = 1;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 3){
					if(currentBookingOfThisSize <= nClassroomsWithSize[3]){
						approveBooking(bookings[i], classrooms[currentSize3Classroom]);
						currentSize3Classroom++;
					}else{
						for(int j=size3StartsAt; j<size2StartsAt; j++){
							if(isClash(bookings[i], classrooms[j], bookings) == false){
								approveBooking(bookings[i], classrooms[j]);
								break;
							}
						}
						if(bookings[i].bookingApproved == false){
							declineBooking(bookings[i]);
						}
					}
					currentBookingOfThisSize++;
				}
			}
		}
		if(nClassroomsWithSize[2] >= nBookingsWithSize[2]){
			int currentSize2Classroom = size2StartsAt;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 2){
					approveBooking(bookings[i], classrooms[currentSize2Classroom]);
					currentSize2Classroom++;
				}
			}
		}else{
			int currentSize2Classroom = size2StartsAt;
			int currentBookingOfThisSize = 1;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 2){
					if(currentBookingOfThisSize <= nClassroomsWithSize[2]){
						approveBooking(bookings[i], classrooms[currentSize2Classroom]);
						currentSize2Classroom++;
					}else{
						for(int j=size2StartsAt; j<size1StartsAt; j++){
							if(isClash(bookings[i], classrooms[j], bookings) == false){
								approveBooking(bookings[i], classrooms[j]);
								break;
							}
						}
						if(bookings[i].bookingApproved == false){
							declineBooking(bookings[i]);
						}
					}
					currentBookingOfThisSize++;
				}
			}
		}
		if(nClassroomsWithSize[1] >= nBookingsWithSize[1]){
			int currentSize1Classroom = size1StartsAt;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 1){
					approveBooking(bookings[i], classrooms[currentSize1Classroom]);
					currentSize1Classroom++;
				}
			}
		}else{
			int currentSize1Classroom = size1StartsAt;
			int currentBookingOfThisSize = 1;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 1){
					if(currentBookingOfThisSize <= nClassroomsWithSize[1]){
						approveBooking(bookings[i], classrooms[currentSize1Classroom]);
						currentSize1Classroom++;
					}else{
						for(int j=size1StartsAt; j<classrooms.length; j++){
							if(isClash(bookings[i], classrooms[j], bookings) == false){
								approveBooking(bookings[i], classrooms[j]);
								break;
							}
						}
						if(bookings[i].bookingApproved == false){
							declineBooking(bookings[i]);
						}
					}
					currentBookingOfThisSize++;
				}
			}
		}		
		
		System.out.println("The program ends!");
	}
		
			
	public static void declineBooking(Booking2 booking){
		
		booking.bookingApproved = false;
		booking.bookingAddressed = true;
		
	}
	
	
	
	public static void approveBooking(Booking2 booking, Classroom classroom) throws NumberFormatException, InvalidTimeException{
		
		booking.bookingApproved = true;
		booking.bookingAddressed = true;
		classroom.isItBooked = true;
		classroom.bookedUser = booking.username;
		
		booking.classroom = classroom;
		classroom.bookingIds.add(booking.id);
		ModifyBookingsInDatabase.ConfirmBookings(booking.id, classroom.name);
	}



	public static boolean isClash(Booking2 booking, Classroom classroom, Booking2[] bookings){
		
		int bookingStartTime = getMinutes(booking.startTime);
		int bookingEndTime = getMinutes(booking.endTime);
		int currentListElementStartTime = -1;
		int currentListElementEndTime = -1;
		Booking2 currentBookingInClassroom = null;
		
		for(int i=0; i<classroom.bookingIds.size(); i++){
			currentBookingInClassroom = getBooking2WithId(classroom.bookingIds.get(i), bookings);
			currentListElementStartTime = getMinutes(currentBookingInClassroom.startTime);
			currentListElementEndTime = getMinutes(currentBookingInClassroom.endTime);
			if(currentListElementStartTime>=bookingStartTime && currentListElementStartTime<bookingEndTime){
				return true;
			}
			if(currentListElementEndTime>bookingStartTime && currentListElementEndTime<=bookingEndTime){
				return true;
			}
			if(currentListElementStartTime<bookingStartTime && currentListElementEndTime>bookingEndTime){
				return true;
			}
		}
		return false;
		
	}



	public static Booking2 getBooking2WithId(int id, Booking2[] bookings){
		
		for(int i=0; i<bookings.length; i++){
			if(id == bookings[i].id){
				return bookings[i];
			}
		}
		return null;
		
	}



	public static int getMinutes(String strHours){
		String startTime1 = strHours.substring(0, 2);
		String startTime2 = strHours.substring(3, 5);
		int intStartTime1 = Integer.parseInt(startTime1);
		int intStartTime2 = Integer.parseInt(startTime2);
		int totalMinutes = intStartTime1*60 + intStartTime2;
		return totalMinutes;
	}









}