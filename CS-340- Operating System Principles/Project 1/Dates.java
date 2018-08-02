import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;


public class Dates implements Runnable{
	
	private Thread Dthread; //names this program the dThread
	private static LinkedTransferQueue<Dates> Dqueue = new LinkedTransferQueue<Dates>(); //a queue for the dates to enter as they become available
	private static final long startTime = System.currentTimeMillis();
	private volatile boolean waitingforcontestant = true; // busy wait boolean
	
	public Dates(String name){
		Dthread = new Thread(this, name);
		Dthread.start();
	}// starts the threads in this method
	
	public void run(){
		//System.out.println(age() + ": " + getName() + " is at the club");
		Available(); //puts the dates in the dates queue as they arrive
		while(Contestants.setDatesGo(true)){
			WaitingForContestant(); //busy waiting for a contestant
			isApproached(); //priority of this thread increases when approached by contestant
			sleep(); //sleeps for a bit to mimic talking
			choose(); //function which makes the date choose to say yes or no
			Available(); //date is returned to the dates queue
		}
		//System.out.println(age() + ": " + getName() + " has gone home");
	}
	
	public void sleep(){
		try{
			Thread.sleep(new Random().nextInt(3000));
		} catch(InterruptedException e){
			
		}
	}//sleep method to delay the thread
	
	public void Available(){// dates are available for contestants
		sleep();
		addDatesQueue(this);
	}//call a method that adds the thread to a queue
	
	public static void addDatesQueue(Dates date) { //Dates enter the queue
		Dqueue.add(date);
	}//adds the thread to a queue
	
	public static Dates getDate(){
		Dates date = null;
		try{
			date = Dqueue.take();
		} catch(InterruptedException e){
			//should not have an exception
		}
		return date;
	}//returns the current thread as a date
	
	public static boolean choose(){ //decides whether or not date will say yes or no
		if(new Random().nextInt(2) == 1){
			return true;
		}else
			return false;
	}
	
	public void isApproached(){ //when Date is approached Priority goes up
		setPriority(Dthread.getPriority() + 3);	
	}
	
	private void setPriority(int priority) {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + (10);
		
		while(System.currentTimeMillis() < endTime){
			Dthread.setPriority(priority);
		}
		Dthread.setPriority(Thread.NORM_PRIORITY);
		
	}//sets the priority of the thread, when 10 seconds elapse, the thread priority returns to normal
	
	public int getPriority(){
		return Dthread.getPriority();
	}//gets the priority of the thread

	public static synchronized boolean checkDatesQueue(){
		return Dqueue.isEmpty();
	}//checks if dates queue is empty
	
	public void WaitingForContestant(){
		while(this.waitingforcontestant == true){
			// System.out.println(Thread.currentThread().getName() +  "is busy waiting");
		}
	}//busy waiting for a contestant
	
	public void setWaitingForContestant(boolean waiting){
		waitingforcontestant = waiting;
	}//sets it so that you are no longer busy waiting
	
	public String getName() {
		return Dthread.getName();
	}//gets name of the current thread
	
	public final long age(){
		return System.currentTimeMillis() - startTime;
	}//returns the age of current thread
}
