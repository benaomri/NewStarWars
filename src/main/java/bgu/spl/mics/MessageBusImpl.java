package bgu.spl.mics;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl<microServiceVector> implements MessageBus {
	private static class SingletonMasBusHolder {
		private static final MessageBusImpl instance=new MessageBusImpl();
	}

	ReadWriteLock thisLock=new ReentrantReadWriteLock();
	/**
	 * We Use 4 Maps:
	 * 1. Key: MS Hash Code, val: Messages vector
	 * 2. Key: Event , val: MS string that subscribe it
	 * 3. Key: Broadcast , val: MS string that subscribe it
	 * 4. Key: Event , val: Future
	 */
	private final  ConcurrentHashMap<Integer, Vector<Message>> msgBusMS;
	private final ConcurrentHashMap<Class<? extends Event>, Vector<Integer>> msgBusEV;
	private final ConcurrentHashMap<Class<? extends Broadcast>, Vector<Integer>> msgBusB;
	private final ConcurrentHashMap<Integer, Future> msgBusFuture;


	/**
	 * Empty constructor
	 */
	private MessageBusImpl()
	{
		msgBusMS = new ConcurrentHashMap<>(); //We add to this when MS is register
		msgBusEV = new ConcurrentHashMap<>(); //We add to this when MS is subscribe it
		msgBusFuture = new ConcurrentHashMap<>();
		msgBusB=new ConcurrentHashMap<>();
	}

	/**
	 * Creating singleton MessageBusImpl or Return the same instance
	 * @return singleton instance of MessageBusImpl
	 */
	public static MessageBusImpl getInstance() {
		return SingletonMasBusHolder.instance;
	}

	/**
	 * We will add the MicroService to the vector of the Event
	 * @param type The type to subscribe to,
	 * @param m    The subscribing micro-service.
	 * @param <T>  The type of Event
	 */
	@Override
	public synchronized  <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		if (!msgBusEV.containsKey(type))
			msgBusEV.put(type,new Vector<>());
		msgBusEV.get(type).add(m.hashCode());
	}


	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if (!msgBusB.containsKey(type))
			msgBusB.put(type,new Vector<>());
		msgBusB.get(type).add(m.hashCode());
	}



	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		msgBusFuture.get(e.hashCode()).resolve(result);

	}



	@Override
	public synchronized void sendBroadcast(Broadcast b) {
		Vector<Integer>broadcastMicroS= msgBusB.get(b.getClass());//get all microService that subscribe to the broadCast
		for(Integer HashCode:broadcastMicroS){//insert to each microservice the broadcast
			msgBusMS.get(HashCode).add(b);
		}
		notifyAll();//notify to all Thread that is their new massage
	}

	/**
	 *
	 * @param e     	The event to add to the queue.
	 * @param <T>
	 * @return    return the Future that we create
	 */
	@Override
	public synchronized <T> Future sendEvent(Event<T> e) {

		Vector<Integer> toRoundRobin = msgBusEV.get(e.getClass());
		if(toRoundRobin!=null&&!toRoundRobin.isEmpty())
		 {
			Integer chosenMicro = round_robin(e, toRoundRobin);//send to round robin all microservice that subscribe to this event
			msgBusMS.get(chosenMicro).add(e);
			msgBusFuture.put(e.hashCode(), new Future());
			notifyAll();
			return msgBusFuture.get(e.hashCode());

		}
		else
			return null;
	}
	@Override
	public  void register(MicroService m) {
		thisLock.writeLock().lock();
		try {
			if (!msgBusMS.containsKey(m.hashCode())) {
				msgBusMS.put(m.hashCode(), new Vector<>());
			}
		}
		finally {
			thisLock.writeLock().unlock();
		}
	}

	@Override
	public void unregister(MicroService m) {
		try {
			msgBusMS.remove(m.hashCode());
		}catch (Exception e){throw new NullPointerException(m.getName()+"is already unregister");}

		for (Class<? extends Broadcast> broad:msgBusB.keySet())
		{
			if(msgBusB.get(broad).contains(m.hashCode()))
				msgBusB.get(broad).removeElement(m.hashCode());
		}
		for (Class<? extends Event> event:msgBusEV.keySet())
		{
			if(msgBusEV.get(event).contains(m.hashCode()))
				msgBusEV.get(event).removeElement(m.hashCode());
		}


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
		msgBusEV.get(e.getClass()).add(microHashCode);
		return microHashCode;
	}

	private boolean checkIfRegister(MicroService m){
		return msgBusMS.containsKey(m.hashCode());
	}
}
