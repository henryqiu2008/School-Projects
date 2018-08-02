
import java.util.concurrent.LinkedTransferQueue;

public class DatingGame {
	
	public static volatile int num_rounds; // calls num_rounds as volatile int
	public static volatile int num_dates;  // calls num_dates as volatile int
	public static volatile int num_contestants; // calls num_contestants as volatile int
	private static SmartPants host[]; //creates an array for SmartPants class
	private static Contestants contestants[]; //creates an array for Contestants class
	private static Dates dates[]; //creates an array for Dates class
	private static LinkedTransferQueue<Contestants> queue = new LinkedTransferQueue<Contestants>(); //creates a queue for contestants to enter as they arrive
	private static volatile boolean show_ends = true; //creates a boolean variable that will tell if the show ends or not
	private static volatile boolean go = false; //creates a boolean to tell contestant while loop to go
	
	public static void main(String[] args){
		
		if(args.length != 3){
			System.out.println("Error! You have not inputted the correct amount of arguments!");
		} // if there are not at least three arguements, error is returned
		else{
			try{ //converts the arguments into proper integers
				num_contestants = Integer.parseInt(args[0]);
				num_dates = Integer.parseInt(args[1]);
				num_rounds = Integer.parseInt(args[2]);
			}catch(NumberFormatException nfe){
				System.out.println("Error! The command line arguements you used are invalid! Please enter proper integers!");
				System.exit(0);
			}//if arguments are not integers, error is returned
			createThreads(); //function to create all the threads needed for the program
		}	
		
	}
	
	public static void createThreads(){
		
		host = new SmartPants[1];
		for(int i = 0; i < host.length; i++){
			host[i] = new SmartPants("Host:SmartPants");
		}
		
		contestants = new Contestants[num_contestants];
		for(int i = 0; i < num_contestants; i++){
			contestants[i] = new Contestants("Contestant # " + (i + 1));
		}
		
		dates = new Dates[num_dates];
		for(int i = 0; i < num_dates; i++){
			dates[i] = new Dates("Date # " + (i + 1));
		}
		
	}// function which creates all threads for this program
	
	public static void setContestantQueue(Contestants contestant){
		queue.add(contestant);
	}//function which adds contestants to a queue as they start up
	
	public static Contestants getContestantQueue(){
		Contestants temp = null;
		try{
			temp = queue.take();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return temp;
	}//takes a contestant from a queue
	
	public static synchronized boolean setGo(boolean go){
		return go;
	}//sychronizes the boolean go across all classes
	
	public static synchronized boolean checkShowEnds(){
		return show_ends;
	}// returns the boolean show_ends across all classes
	
	public static synchronized boolean changeSetGo(){
		go = false;
		return go;
	}// changes the go boolean to false and returns it
	
	public static synchronized boolean changeShowEnds(){
		show_ends = false;
		return show_ends;
	} //changes the show_ends boolean to false and returns it
	
	public static synchronized int getTotalContestants(){
		return num_contestants;
	}// returns the amount of contestants to all classes
	
	public static synchronized int getRounds(){
		return num_rounds;
	}// returns the amount of rounds each one is expected to go through
	
	public static synchronized boolean checkContestantQueue(){
		return queue.isEmpty();
	}// checks if the queue is empty

}
