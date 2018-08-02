import java.util.Random;


public class Contestants implements Runnable{

	private Thread Cthread; //calls a thread as Cthread
	private volatile boolean waitingforlecture = true; //boolean for waiting on lecture
	private volatile boolean waitingfordate = true; //boolean for waiting for date
	private volatile boolean waitingforhost = true; //boolean for waiting for host
	private static volatile boolean dgo = false; //sets dgo as false
	private static final long startTime = System.currentTimeMillis();//starts a system timer
	
	public Contestants(String name){
		Cthread = new Thread(this, name);
		Cthread.start();
	}//starts this classes threads
	
	public void run(){
		int rounds = 0;
		sleep();// contestants arrive randomly
		System.out.println(age() + ": " + getName() + " arrives at the club");
		getExplanation(); //contestants get an explanation from SmartPants
		while(DatingGame.setGo(true) && rounds != DatingGame.getRounds()){
			WaitingForHost(); //busy waiting for host
			if(Dates.checkDatesQueue() != true && rounds != DatingGame.getRounds()){
				setDatesGo(true); //starts the Dates threads loose from sleep
				Dates temp = Dates.getDate(); //gets an available date from queue
				temp.setWaitingForContestant(false);// stops dates from busy waiting for contestant
				delay(); //short delay for dates to choose yes or no
				if(Dates.choose() == true){
					System.out.println(age() + ": " + temp.getName() + " is interested in " + getName());
					rounds++;
				}// returns a msg saying dtae is interested
				else{
					System.out.println(age() + ": " + temp.getName() + " is not interested in " + getName());
					rounds++;
				}// returns a msg saying date isnt interested
				SmartPants.setAgainQueue(this);//adds this thread to the again queue
			}//checks to make sure dates queue is not empty and thread has not exceeded the number of rounds
		}// while boolean Go from DatingGame class and rounds is not equal to num_rounds , then continue to loop
		
		SmartPants.setPatQueue(this); //adds done threads to a queue
		delay(); //delays for a bit to get a bit more
		SmartPants.InterruptSmartPants(); // interrupts SmartPants
	}
	
	
	private void getExplanation(){
		DatingGame.setContestantQueue(this);
	}// puts the arriving contestants in a queue in Dating Game class
	
	private void delay(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
				// shouldn't need any interrupts
		}
	}// a static sleep method for 3 seconds
	
	private void sleep(){
		try {
			Thread.sleep(new Random().nextInt(2000));
		} catch (InterruptedException e) {
				// shouldn't need any interrupts
		}
	}// a sleep method with a random sleep timer
	
	public void WaitingForDate(){
		while(this.waitingfordate == true){
			// System.out.println(Thread.currentThread().getName() +  "is busy waiting");
		}
	}//busy waiting
	public void WaitingForLecture(){
		while(this.waitingforlecture == true){
			// System.out.println(Thread.currentThread().getName() +  "is busy waiting");
		}
	}//busy waiting
	
	public void WaitingForHost(){
		while(this.waitingforhost == true){
			// System.out.println(Thread.currentThread().getName() +  "is busy waiting");
		}
	}//busy waiting

	public void setWaitingForDate(boolean waiting){
		waitingfordate = waiting;
	}//changes the busy wait methods
	
	public void setWaitingForHost(boolean waiting) {
		waitingforhost = waiting;
		
	}//changes the busy wait method
	
	public void setWaitingForLecture(boolean waiting){
		waitingforlecture = waiting;
	}//changes the busy wait method

	public String getName() {
		return Cthread.getName();
	}//gets name of current thread

	public static synchronized boolean setDatesGo(boolean dgo){
		return dgo;
	}// boolean method for variable dgo which starts the Dates threads
	
	public static synchronized boolean changeDatesGo(){
		dgo = false;
		return dgo;
	}//sets the dgo to false so that they no longer interact with dates threads 
	
	public final long age(){
		return System.currentTimeMillis() - startTime;
	}//returns current age of thread
	
}
