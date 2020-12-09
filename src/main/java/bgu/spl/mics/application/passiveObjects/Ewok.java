package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
    int serialNumber;
    boolean available;
    AtomicBoolean isAvailable;

    public Ewok(int serialNumber)
    {
        this.serialNumber=serialNumber;
        this.available=true;
        isAvailable=new AtomicBoolean(true);
    }
    /**
     * Acquires an Ewok
     * @PRE: available is true
     * @POST:  available is false
     */
    public synchronized void acquire(){
        while (!isAvailable.compareAndSet(true,false))
        {
            try {
               wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.available=false;
        System.out.println("In Ewok acquire "+serialNumber);
    }

    /**
     * release an Ewok
     *  @PRE: available is false
     *  @POST:  available is true
     */
    public synchronized void release() {

        this.available=true;
        this.isAvailable.set(true);
        notifyAll();

    }

    public String toString()
    {
        return "Ewok: "+serialNumber+" is available: "+available;
    }
}
