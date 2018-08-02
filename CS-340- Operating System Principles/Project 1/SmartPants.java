import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;


public class SmartPants implements Runnable{

	private static LinkedTransferQueue<Contestants> Again = new LinkedTransferQueue<Contestants>();
	private static LinkedTransferQueue<Contestants> PatOnBack = new LinkedTransferQueue<Contestants>();
	//queues for when the contestant tries to score another date and when they return to SmartPants to get a pat on the back
	private static Thread host; //calls the thread as host
	private static final long startTime = System.currentTimeMillis(); //keeps track of current system time
	static int counter = 0; // A global counter for helping smartpants know when the show is over
	
	public SmartPants(String name){
		host = new Thread(this, name);
		host.start();
	}//calls this thread to start
	
	public void run(){
		System.out.println(age() + ": " + getName() + ": Welcome to the show!");
		while(DatingGame.checkShowEnds()){
			sleep();// calls the sleep function so that contestants can arrive
			giveLecture(); //calls the lecture function so that contestants get an explanation
			if(counter == DatingGame.getTotalContestants()){
				DatingGame.changeShowEnds();
			}else{ // if counter is equal to the amount of contestants, then the show will end, else it will go back to sleep
				sleep();
			}
		}
		System.out.println(age() + ": " + getName() + " announces the end of the show");
		Contestants.changeDatesGo(); // calls the changeDatesGo() in contestants
	}
	
	public void sleep(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			if(checkPatOnBackQueue() != true){
				Contestants temp = getPatOnBackQueue();
				temp.setWaitingForHost(false);
				System.out.println(age() + ": " + temp.getName() + " got pat on the back by " + getName());
				counter++;
			}
		}
	}// sleep method unless an interrupt happens, which then calls the PatOnBackQueue for the contestants
	
	public void giveLecture(){
		while(DatingGame.checkContestantQueue() != true){
			
			Contestants temp = DatingGame.getContestantQueue();
			temp.setWaitingForLecture(false);
			delay();
			System.out.println(age() + ": " + temp.getName() + " is getting an explanation from " + getName());
			DatingGame.setGo(true);
			temp.setWaitingForHost(false);
		} 
	}//gives a lecture to the waiting contestants and causes the threads to start interacting with each other
	
	public void delay(){
		try{
			Thread.sleep(new Random().nextInt(3000));
		} catch(InterruptedException e){
			// shouldn't need this
		}
	}// a secondary sleep function - used for when I want times to be random
	
	public static void setAgainQueue(Contestants contestant){
		Again.add(contestant);
	}// adds the done contestant onto a queue again to go again
	
	public static Contestants getAgainQueue(){
		Contestants temp = null;
		try{
			temp = Again.take();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return temp;
	}// returns the contestant to the forefront so that it can go through the loop again for another date
	
	public static synchronized boolean checkAgainQueue(){
		return Again.isEmpty();
	}// checks if the again queue is empty
	
	public static synchronized boolean checkPatOnBackQueue(){
		return PatOnBack.isEmpty();
	}// checks if the PatOnBack queue is empty
	
	public static void setPatQueue(Contestants contestant){
		PatOnBack.add(contestant);
	}// adds a contestant thread to the PatOnBack queue
	
	
	public static Contestants getPatOnBackQueue(){
		Contestants temp = null;
		try{
			temp = PatOnBack.take();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return temp;
	}// returns the forefront thread on this queue for a pat on the back
	
	public String getName(){
		return host.getName();
	}//gets the name of this thread
	
	public final long age(){
		return System.currentTimeMillis() - startTime;
	}// current time of the program

	public static synchronized void InterruptSmartPants() {
		host.interrupt();
		
	}// function which will interrupt SmartPants from his sleep
}
