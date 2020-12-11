package bgu.spl.mics.application.services;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.Main;

import bgu.spl.mics.Callback;
import bgu.spl.mics.CallbackImpl;
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
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        subscribeBroadcast(TerminateBroadCast.class, c -> terminate());
        subscribeEvent(AttackEvent.class, (AttackEvent att)->C3POatt(att));
        Main.CDL.countDown();


    }
    @Override
    protected void close()
    {
        Diary.getInstance().setC3POTerminate();
    }

    private  void C3POatt(AttackEvent a){
        Vector<Ewok> EwokList= Ewoks.getInstance().getEwokList();
        List<Integer> serials=a.getSerials();
        long duration=a.getDuration();

        //Acquire
        for (int i=0;i<serials.size();i++)
        {
            int serial=serials.get(i)-1;
            EwokList.get(serial).acquire();
        }
        try {

            Thread.sleep(duration);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Release
        for (int i=0;i<serials.size();i++)
        {
            int serial=serials.get(i)-1;
            EwokList.get(serial).release();
        }
        MessageBusImpl.getInstance().sendBroadcast(new LeiaMFinishAtt(a));
        Diary.getInstance().setC3POFinish();
        Diary.getInstance().incAtt();
    }
}
