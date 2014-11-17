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
	public static void processClassrooms() throws NumberFormatException, InvalidTimeException{
		
		// 1
		Booking[] tempBookings = ModifyBookingsInDatabase.SearchFromWaitlist("Date", "2014-07-08");		// Check if this works
//		System.out.println(tempBookings.length);
//		System.out.println(tempBookings[0].getBookingId());
		Booking2[] bookings = new Booking2[tempBookings.length];
		for(int i=0; i<tempBookings.length; i++){
			//System.out.println(i);
			bookings[i] = new Booking2();
			bookings[i].set(tempBookings[i]);
			System.out.println(bookings[i].id);
		}
		System.out.println("_____________");
		// 2
		Classroom[] classrooms = ModifyBookingsInDatabase.instantiateRooms();	// Database code
		
		// 3
		// Sort the 'bookings' array (consider 'privilege' and 'id').
		// After sorting, higher privileges will be at the top. Among members of a privilege, they will be in ascending order of 'id's.
		// 'classrooms' array does not need to be sorted.
		// 'classrooms' will have higher sized members at the top.
		Booking2[] temp = insertionSortGeneric(bookings);
		//temp = insertionSortByID(temp);
//		int nElements = bookings.length;
//		int highestPrivilege = 0;
//		int elementWithHP = -1;	// for the 0th element
//		for(int i=0; i<nElements-1; i++){
//		//	Booking2 temp = bookings[i];
//			for(int j=i+1; j<nElements; j++){
//				if(bookings[j].privilege > highestPrivilege){
//					highestPrivilege = bookings[j].privilege;
//					elementWithHP = j;
//				}
//			}
//			Booking2 temp = bookings[i];
//			bookings[i] = bookings[elementWithHP];
//			bookings[elementWithHP] = temp;
//		}
		for(Booking2 one : temp)
		System.out.println(one.privilege + "- " + one.id);
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
		System.out.println("-----");
		for(int i : nBookingsWithSize)		System.out.println(i);
		System.out.println("---------");
		for( int j : nClassroomsWithSize) System.out.println(j);
		// Finding the index of the first elements w.r.t. size in the 'classrooms' array.
		int size5StartsAt = classrooms.length - nClassroomsWithSize[1] - nClassroomsWithSize[2] - nClassroomsWithSize[3] -
				nClassroomsWithSize[4] - nClassroomsWithSize[5];
		int size4StartsAt = classrooms.length - nClassroomsWithSize[1] - nClassroomsWithSize[2] - nClassroomsWithSize[3] -
				nClassroomsWithSize[4];
		int size3StartsAt = classrooms.length - nClassroomsWithSize[1] - nClassroomsWithSize[2] - nClassroomsWithSize[3];
		int size2StartsAt = classrooms.length - nClassroomsWithSize[1] - nClassroomsWithSize[2];
		int size1StartsAt = classrooms.length - nClassroomsWithSize[1];
		
		System.out.println(size5StartsAt + " " + size4StartsAt + " " + size3StartsAt + " " + size2StartsAt + " " + size1StartsAt);
		if(nClassroomsWithSize[5] >= nBookingsWithSize[5]){
			int currentSize5Classroom = size5StartsAt;
			System.out.println("-------");
			System.out.println(currentSize5Classroom);
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 5){
					System.out.println(bookings[i].id);
					ModifyBookingsInDatabase.ConfirmBookings(bookings[i].id, "Audi");
					currentSize5Classroom++;
				}
			}
		}else{
			int currentSize5Classroom = size5StartsAt;
			int currentBookingOfThisSize = 1;
			for(int i=0; i<bookings.length; i++){
				if(bookings[i].size == 5){
					if(currentBookingOfThisSize <= nBookingsWithSize[5]){
						approveBooking(bookings[i], classrooms[currentSize5Classroom]);
						currentSize5Classroom++;
					}else{
						declineBooking(bookings[i]);
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
					if(currentBookingOfThisSize <= nBookingsWithSize[4]){
						approveBooking(bookings[i], classrooms[currentSize4Classroom]);
						currentSize4Classroom++;
					}else{
						declineBooking(bookings[i]);
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
					if(currentBookingOfThisSize <= nBookingsWithSize[3]){
						approveBooking(bookings[i], classrooms[currentSize3Classroom]);
						currentSize3Classroom++;
					}else{
						declineBooking(bookings[i]);
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
					if(currentBookingOfThisSize <= nBookingsWithSize[2]){
						approveBooking(bookings[i], classrooms[currentSize2Classroom]);
						currentSize2Classroom++;
					}else{
						declineBooking(bookings[i]);
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
					if(currentBookingOfThisSize <= nBookingsWithSize[1]){
						approveBooking(bookings[i], classrooms[currentSize1Classroom]);
						currentSize1Classroom++;
					}else{
						declineBooking(bookings[i]);
					}
					currentBookingOfThisSize++;
				}
			}
		}		
				
	}
	
	
	
	public static void writeToDatabase(){
		// Code to write the entire 'classrooms' array to (a preferably new) database.
	}
	
	
	
	// Methods required for the processClassrooms() method:
	
	public static void declineBooking(Booking2 booking){
		
		booking.bookingApproved = false;
		booking.bookingAddressed = true;
		
	}
	
	public static void approveBooking(Booking2 booking, Classroom classroom) throws NumberFormatException, InvalidTimeException{
		
		booking.bookingApproved = true;
		booking.bookingAddressed = true;
		classroom.isItBooked = true;
		classroom.bookedUser = booking.username;
		ModifyBookingsInDatabase.ConfirmBookings(booking.id, classroom.name);
		
	}
	public static Booking2[] insertionSortGeneric(Booking2[] a) {
        for (int i=0; i < a.length; i++) {
            /* Insert a[i] into the sorted sublist */
            Booking2 v = a[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
            	//System.out.println(j + "*");
                if (a[j].privilege > a[i].privilege ) break;
        //        else if( a[j].privilege==a[i].privilege && a[j].id>a[i].id) break;
               a[j + 1] = a[j];
            }
           a[j + 1] = v;
        }
        return a;
    }
	
	public static Booking2[] insertionSortByID(Booking2[] a) {
        for (int i=0; i < a.length; i++) {
            /* Insert a[i] into the sorted sublist */
            Booking2 v = a[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
            	//System.out.println(j + "*");
                if (a[j].id < a[i].id ) break;
        //        else if( a[j].privilege==a[i].privilege && a[j].id>a[i].id) break;
               a[j + 1] = a[j];
            }
           a[j + 1] = v;
        }
        return a;
    }
	
	
}
