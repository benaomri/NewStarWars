package bgu.spl.mics.application.services;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.Main;


import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.LeiaMFinishAtt;
import bgu.spl.mics.application.messages.TerminateBroadCast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.List;
import java.util.Vector;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {
    public C3POMicroservice() {
        super("C3PO");
    }

    @Override
    /**
     * initialize C3PO :
     *     register
     *     subscribe to Terminate Broadcast
     *     subscribe to Att Event
     */
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        subscribeBroadcast(TerminateBroadCast.class, c -> terminate());
        subscribeEvent(AttackEvent.class, this::C3POAtt);
        Main.CDL.countDown();// count down for init Leia


    }
    @Override
    protected void close()//write the terminate time in the dairy
    {
        Diary.getInstance().setC3POTerminate();
    }

    private  void C3POAtt(AttackEvent a){//Attacking
        Vector<Ewok> EwokList= Ewoks.getInstance().getEwokList();//All Ewoks
        List<Integer> serials=a.getSerials();// Ewoks for Att
        long duration=a.getDuration();//att duration
        //Acquire Ewoks
        for (Integer integer : serials) {
            int serial = integer - 1;
            EwokList.get(serial).acquire();
        }
        try {

            Thread.sleep(duration);//attacking

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Release Ewoks
        for (Integer integer : serials) {
            int serial = integer - 1;
            EwokList.get(serial).release();
        }
        MessageBusImpl.getInstance().sendBroadcast(new LeiaMFinishAtt(a));//send to leia that att finished
        Diary.getInstance().setC3POFinish();//write in dairy finish att
        Diary.getInstance().incAtt();// increment number of att
    }
}
