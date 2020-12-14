package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.Main;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.LeiaMFinishAtt;
import bgu.spl.mics.application.messages.TerminateBroadCast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.List;
import java.util.Vector;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {

    public HanSoloMicroservice() {
        super("Han");
    }

    /**
     * initialize HanSolo :
     *     register
     *     subscribe to Terminate Broadcast
     *     subscribe to Att Event
     */
    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        subscribeEvent(AttackEvent.class, this::HanAtt) ;
        subscribeBroadcast(TerminateBroadCast.class,c -> terminate());
        Main.CDL.countDown();// count down for init Leia

    }

    /**
     * Close Method
     */
    @Override
    protected void close()
    {
        Diary.getInstance().setHanSoloTerminate();
    }//write the terminate time in the dairy


    /**
     * Han Solo Attack
     * @param a - the event He att
     */
    private  void HanAtt(AttackEvent a){//Attacking
        Vector<Ewok> EwokList=Ewoks.getInstance().getEwokList();//All Ewoks
        List<Integer> serials=a.getSerials();// Ewoks for Att
        long duration=a.getDuration();//att duration

        //Acquire Ewoks
        for (Integer value : serials) {
            int serial = value - 1;
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
        Diary.getInstance().setHanSoloFinish();//write in dairy finish att
        Diary.getInstance().incAtt();// increment number of att
    }




}
