import java.util.Random;

public class Contestants implements Runnable{

	private Thread Cthread; //calls a thread as Cthread
	static int count = 0;
	static int counter = 0;
	//counters which will help keep track of the number of times each contestant has interacted with dates
	private static final long startTime = System.currentTimeMillis();
	public static boolean go = true;
	
	public Contestants(String name){
		Cthread = new Thread(this, name);
		Cthread.start();
	}//starts this classes threads
	
	public void run() {
		int rounds = 0;
		int done = 0;
		sleep();// contestants arrive randomly
		System.out.println(age() + ": " + getName() + " arrives at the club");
		try {
			sleep();//delays the time of getting explanation so order is random
			SmartPants.mutex.acquire(); //grabs a permit from mutex and makes it so no other thread can use it
			giveLecture(); //contestants get a lecture from smartpants
			SmartPants.mutex.release(); //releases the permit back to mutex so it is available to the next thread
		} catch (InterruptedException e) {
			//no interruption needed
		}
		Main.startDates(); //starts the dates thread
		while(go){
			sleep();
			if(count == Main.getTotalContestants() && rounds != Main.getRounds()){
				try {
					Dates.dates.acquire();
					Dates temp = Dates.getDate(); //grabs a date from the available queue
					sleep();
					if(Dates.choose() == true){
						System.out.println(age() + ": " + temp.getName() + " is interested in " + getName());
						rounds++;
					}// returns a msg saying date is interested
					else{
						System.out.println(age() + ": " + temp.getName() + " is not interested in " + getName());
						rounds++;
					}// returns a msg saying date isnt interested
					Dates.dates.release();
				} catch (InterruptedException e) {
					//nothing needed
				} 
			}
			done++;
			if(done == Main.getRounds()){ //makes sure the correct number of rounds has been done for each contestant
				counter++;
			}
		}
	}

	public void giveLecture(){
		String temp = SmartPants.getName();
		System.out.println(age() + ": " + getName() + " is getting an explanation from " + temp);
		count++;
	}//gives a lecture to the contestant and causes the threads to start interacting with each other
	
	public void sleep(){
		try {
			Thread.sleep(new Random().nextInt(3000));
		} catch (InterruptedException e) {
				// shouldn't need any interrupts
		}
	}// a sleep method with a random sleep timer
	
	public String getName() {
		return Cthread.getName();
	}//gets name of current thread

	public static int getCount(){
		//System.out.println("Count : " + count);
		return count;
	}//returns the current count
	
	public static int getCounter(){
		//System.out.println("Counter : " + counter);
		return counter;
	}// returns the current counter
	
	public final static long age(){
		return System.currentTimeMillis() - startTime;
	}//returns the age of current thread
	
}
