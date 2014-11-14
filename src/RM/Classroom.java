package RM;

// Author : Chirag Vartak

// When the process() in Waitlist is done, ALL the classrooms will be created as objects in the memory.
// Thus, we will have a 'classrooms' array that will be created in the process() method.
// Assumption: The database has the classrooms listed in the order of their size, largest size first
// Assumption: Their ids in database will be consecutive integers in ascending order.

public class Classroom{
	public int id = -1;
	public int size = -1;
	public String name = "noname";
	public boolean isItBooked = false;
	public String bookedUser = "noname";
}