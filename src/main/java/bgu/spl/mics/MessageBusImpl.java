package bgu.spl.mics;

import java.util.Vector;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import static java.lang.Thread.sleep;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl<microServiceVector> implements MessageBus {
	private  static MessageBusImpl instance=null;//singleton expression

	/**
	 * We Use 3 Maps:
	 * 1. Key: MS Hash Code, val: Messeges vector
	 * 2. Key: Event , val: MS string that subscribe it
	 * 3. Key: Event , val: Future
	 */
	private Vector microServiceVector;
	private ConcurrentHashMap<Integer, Vector<Message>> msgBusMS;
	private ConcurrentHashMap<Class<? extends Event>, Vector<Integer>> msgBusEV;
	private ConcurrentHashMap<Class<? extends Broadcast>, Vector<Integer>> msgBusB;
	private ConcurrentHashMap<Event, Future> msgBusFuture;
	private BlockingDeque bQueue;


	/**
	 * Empty constructor
	 */
	private MessageBusImpl()
	{
		msgBusMS = new ConcurrentHashMap<>(); //We had to this when MS is register
		msgBusEV = new ConcurrentHashMap<>(); //We had to this when MS is subscribe it
		msgBusFuture = new ConcurrentHashMap<>();
		msgBusB=new ConcurrentHashMap<>();
		bQueue=new LinkedBlockingDeque();
	}

	/**
	 * Creating singlteon MessageBusImpl or Return the same instance
	 * @return Singelton instance of MessageBusImpl
	 */
	public static MessageBusImpl getInstance(){
		if(instance==null)
		{
			instance=new MessageBusImpl();
		}
		return instance;
	}

	/**
	 * We will add the MicroService to the vector of the Event
	 * @param type The type to subscribe to,
	 * @param m    The subscribing micro-service.
	 * @param <T>
	 */
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		if (!msgBusEV.contains(type))
			msgBusEV.put(type,new Vector<>());
		msgBusEV.get(type).add(m.hashCode());
		System.out.println("Inserting- "+m.hashCode()+" "+m.getName());
		System.out.println(msgBusEV.toString());

	}


	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if (!msgBusB.contains(type))
			msgBusB.put(type,new Vector<>());
		msgBusB.get(type).add(m.hashCode());
	}



	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		msgBusFuture.get(e).resolve(result);

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		Vector<Integer>broadcastMicroS= msgBusB.get(b);//get all microService that subscribe to the broadCast
		for(Integer HashCode:broadcastMicroS){//insert to each microservice the broadcast
			msgBusMS.get(HashCode).add(b);
		}
		notifyAll();//notify to all thered that is their new massage
	}

	/**
	 *
	 * @param e     	The event to add to the queue.
	 * @param <T>
	 * @return
	 */
	@Override
	public synchronized <T> Future sendEvent(Event<T> e) {
		Vector<Integer> toRoundRobin= msgBusEV.get(e.getClass());//
		for(Integer serial:toRoundRobin)
			System.out.println(serial);

		Integer chosenMicro= round_robin(e,toRoundRobin);//send to round robin all microservice that subscribe to this event
		msgBusMS.get(chosenMicro).add(e);
		notifyAll();
		return msgBusFuture.get(e);
	}
	private  void printMSG()
	{
		System.out.println(msgBusMS.toString());
	}

	@Override
	public void register(MicroService m) {
		if(!msgBusMS.contains(m.hashCode())) {
			msgBusMS.put(m.hashCode(), new Vector<Message>());
			System.out.println(m.getName() + "," + m.hashCode() + " Has Been Registered successfully");
			printMSG();
		}
	}

	@Override
	public void unregister(MicroService m) {
		msgBusMS.remove(m.hashCode());
	}

	@Override
	public synchronized Message awaitMessage(MicroService m) throws InterruptedException {
		if(!checkIfRegister(m))
			throw new IllegalStateException(m.getName()+" is not register");

		while(msgBusMS.get(m.hashCode()).isEmpty()){//wait until is massage to take
			wait();
		}
		Message msgOut=msgBusMS.get(m.hashCode()).get(0);
		msgBusMS.get(m.hashCode()).remove(0);
		return  msgOut;
	}

	private Integer round_robin(Event e,Vector<Integer> microSVector){
		Integer microHashCode= microSVector.firstElement();
		msgBusEV.get(e.getClass()).remove(0);
		msgBusEV.get(e.getClass()).add(microHashCode);//add to the end of the quque
		return microHashCode;
	}

	private boolean checkIfRegister(MicroService m){
		return msgBusMS.containsKey(m.hashCode());
	}
}
