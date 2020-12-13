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


    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        subscribeEvent(AttackEvent.class, this::HanAtt) ;
        subscribeBroadcast(TerminateBroadCast.class,c -> terminate());
        Main.CDL.countDown();

    }
    @Override
    protected void close()
    {
        Diary.getInstance().setHanSoloTerminate();
    }

    private  void HanAtt(AttackEvent a){
        Vector<Ewok> EwokList=Ewoks.getInstance().getEwokList();
        List<Integer> serials=a.getSerials();
        long duration=a.getDuration();

        //Acquire
        for (Integer value : serials) {
            int serial = value - 1;
            EwokList.get(serial).acquire();
        }
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Release
        for (Integer integer : serials) {
            int serial = integer - 1;
            EwokList.get(serial).release();
        }
        MessageBusImpl.getInstance().sendBroadcast(new LeiaMFinishAtt(a));
        Diary.getInstance().setHanSoloFinish();
        Diary.getInstance().incAtt();
    }




}
