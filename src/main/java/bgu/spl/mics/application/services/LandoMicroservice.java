package bgu.spl.mics.application.services;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Main;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminateBroadCast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private final long duration;

    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration=duration;
    }
    /**
     * initialize Lando :
     *     register
     *     subscribe to Terminate Broadcast
     *     subscribe to BombDestroyer Event
     */
    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        subscribeBroadcast(TerminateBroadCast.class, c -> terminate());
        subscribeEvent(BombDestroyerEvent.class, c -> Bomb());
        Main.CDL.countDown();// count down for init Leia

    }
    @Override
    protected void close()
    {
        Diary.getInstance().setLandoTerminate();
    }//write the terminate time in the dairy


    public void Bomb()
    {
        try {
            Thread.sleep(duration);//bombing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//finish bombing
        MessageBusImpl.getInstance().sendBroadcast(new TerminateBroadCast());//seng terminate broad cast
    }
}
