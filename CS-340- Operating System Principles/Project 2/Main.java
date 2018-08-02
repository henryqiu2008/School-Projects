
public class Main {

	public static int num_rounds; // calls num_rounds as int
	public static int num_dates;  // calls num_dates as int
	public static int num_contestants; // calls num_contestants as int
	public static int num_group; // calls num_group as int
	private static SmartPants host[]; //creates an array for SmartPants class
	private static Contestants contestants[]; //creates an array for Contestants class
	private static Dates dates[]; //creates an array for Dates class
	private static boolean show_ends = true; //creates a boolean variable that will tell if the show ends or not
	
	public static void main(String[] args){
		
		if(args.length != 4){
			System.out.println("Error! You have not inputted the correct amount of arguments!");
		} // if there are not at least four arguments, error is returned
		else{
			try{ //converts the arguments into proper integers
				num_contestants = Integer.parseInt(args[0]);
				num_dates = Integer.parseInt(args[1]);
				num_rounds = Integer.parseInt(args[2]);
				num_group = Integer.parseInt(args[3]);
			}catch(NumberFormatException nfe){
				System.out.println("Error! The command line arguements you used are invalid! Please enter proper integers!");
				System.exit(0);
			}//if arguments are not integers, error is returned
			createThreads(); //function to create all the threads needed for the program
		}	
		
	}

	private static void createThreads() {

		host = new SmartPants[1];
		for(int i = 0; i < host.length; i++){
			host[i] = new SmartPants("Host:SmartPants");
		}
		
		contestants = new Contestants[num_contestants];
		for(int i = 0; i < num_contestants; i++){
			contestants[i] = new Contestants("Contestant # " + (i + 1));
		}
		
	}// function which creates smartpants and contestant threads for this program
	
	public static void startDates() {
		
		dates = new Dates[num_dates];
		for(int i = 0; i < num_dates; i++){
			dates[i] = new Dates("Date # " + (i + 1));
		}
	}// starts the dates threads
	
	public static boolean checkShowEnds(){
		return show_ends;
	}// returns the boolean show_ends 
	
	public static boolean changeShowEnds(){
		show_ends = false;
		return show_ends;
	} //changes the show_ends boolean to false and returns it
	
	public static int getTotalContestants(){
		//System.out.println("Number of contestants: " + num_contestants);
		return num_contestants;
	}// returns the amount of contestants to all classes
	
	public static int getRounds(){
		return num_rounds;
	}//returns the argument for number of rounds

	public static int getTotalDates() {
		return num_dates;
	}//returns the argument for number of dates

	public static int getGroup(){
		return num_group;
	}//returns the argument for group size
	
}
