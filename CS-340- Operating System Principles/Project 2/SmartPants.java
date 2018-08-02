import java.util.Random;
import java.util.concurrent.Semaphore;


public class SmartPants implements Runnable{

	public static Semaphore mutex = new Semaphore(1); //semaphore that only has only one permit, only 1 thread can access it at a time
	private static Thread host; //calls the thread as host
	private static final long startTime = System.currentTimeMillis();
	public static boolean secgo = false;
	
	public SmartPants(String name){
		host = new Thread(this, name);
		host.start();
	}//calls this thread to start
	
	public void run(){
		System.out.println(age() + ": " + getName() + ": Welcome to the show!");
		while(Main.checkShowEnds()){
			if(Contestants.getCounter() == Main.getTotalContestants()){
				Main.changeShowEnds();
			}else{ // if counter is equal to the amount of contestants, then the show will end, else it will go back to sleep
				sleep();
			}
		}
		sleep();//delays it so that all contestants are done
		System.out.println(age() + ": " + getName() + " announces the end of the show");
		System.out.println(age() + ": " + getName() + " has terminated!");
		Dates.changeEnds(); //causes the dates threads to terminate
		System.out.println(age() + ": Dates have terminated!");
		setGo();//changes secgo boolean
		cLeave();//calls the contestants leaving function
	}
	
	
	private void cLeave() {
		int i = 1;
		int left = Main.getTotalContestants(); //sets amount of contestants left loitering outside the club
		while(secgo){
			left = left - Main.getGroup(); //subtracts amount of contestants with the size of a group
			if(left > 0){
				delay(); //brag time
				System.out.println(age() + ": Group " + i + " has left! There are " + left + " Contestants loitering outside.");
			}
			else if(left < 0){
				System.out.println(age() + ": Group " + i + " has left! There are no more Contestants loitering outside.");
				setFalse(); //sets secgo to false
			}
			i++;
		}
	}// method which facilitates Contestants leaving in groups

	private void setFalse() {
		secgo = false;
	}// sets secgo back to false to really terminate smartpants thread

	public void delay(){
		try {
			Thread.sleep(new Random().nextInt(3000));
		} catch (InterruptedException e) {
			//shouldn't need anything as this is just a sleep function
		}
	}// sleep method
	
	public void sleep(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			//shouldn't need anything as this is just a sleep function
		}
	}// sleep method 
	
	public static void setGo() {
		secgo = true;
	}// sets secgo to true so that Contestant leave method will run
	
	public static String getName(){
		return host.getName();
	}//gets the name of this thread
	
	public final static long age(){
		return System.currentTimeMillis() - startTime;
	}//returns the age of current thread
}
