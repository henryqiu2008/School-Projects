import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.Semaphore;

public class Dates implements Runnable{

	private Thread Dthread; //names this program the dThread
	private static LinkedTransferQueue<Dates> Dqueue = new LinkedTransferQueue<Dates>(); //a queue for the dates to enter as they become available
	//also used to prevent Dthread from needing to become static
	public static Semaphore dates = new Semaphore(Main.getTotalDates()); //A semaphore that makes it so that contestants can interact with all available dates if they're there
	private final static long startTime = System.currentTimeMillis();
	public static boolean go = true;
		
	public Dates(String name){
		Dthread = new Thread(this, name);
		Dthread.start();
	}// starts the threads in this method
	
	public void run() {
		while(go){
			while(Contestants.getCount() == Main.getTotalContestants() && Main.getTotalContestants() != Contestants.getCounter()){
				Available(); //dates are placed into a queue as they come alive
				sleep(); //sleeps for a bit to mimic waiting
				isApproached(); //priority of this thread increases when approached by contestant
				sleep(); //sleeps for a bit to mimic talking
				choose(); //function which makes the date choose to say yes or no
			}//while loop that makes it so that Dates will keep running until all contestants are done
		}//while loop that makes this thread keep going until smartpants signals it to stop
	}
	
	public void Available(){// dates are available for contestants
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
	
	public void sleep(){
		try{
			Thread.sleep(new Random().nextInt(3000));
		} catch(InterruptedException e){
			//nothing is needed
		}
	}//sleep method to delay the thread
	
	public void isApproached(){ //when Date is approached Priority goes up
		setPriority(Dthread.getPriority() + 1);	
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
	
	public static boolean choose(){ //decides whether or not date will say yes or no
		if(new Random().nextInt(2) == 1){
			return true;
		}else
			return false;
	}
	
	public String getName() {
		return Dthread.getName();
	}//gets name of the current thread

	public static boolean changeEnds(){
		go = false;
		return go;
	} //changes the go boolean to false and returns it
	
	public final long age(){
		return System.currentTimeMillis() - startTime;
	}//returns the age of current thread


	
}
