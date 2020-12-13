package bgu.spl.mics.application.services;


import java.util.concurrent.atomic.AtomicBoolean;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.Main;


/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
    private final Attack[] attacks;
    static AtomicBoolean FinishedSend;
    private final Future[] futures;


    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
        this.attacks = attacks;
        FinishedSend=new AtomicBoolean(false);
        futures=new Future[attacks.length];

    }

    @Override
    protected void initialize() {
        while (Main.CDL.getCount()>0)
        {
            try {
                Main.CDL.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        MessageBusImpl.getInstance().register(this);
        subscribeBroadcast(TerminateBroadCast.class, c -> terminate());
        subscribeBroadcast(LeiaMFinishAtt.class,(LeiaMFinishAtt l)->changeComplete(l.getEvent()) );
        sendAttEvent();


    }
    private void sendAttEvent(){
        int i=0;
        for(Attack att:attacks){
            futures[i]=sendEvent(new AttackEvent(att.getDuration(),att.getSerials(),i));
            i++;
        }
    }



    @Override
    protected void close()
    {
        Diary.getInstance().setLeiaTerminate();
    }

    public void changeComplete(Event<Boolean> e){
        complete(e,true);
        if(isComplete())
        {
            MessageBusImpl.getInstance().sendEvent(new DeactivationEvent());
        }
    }
    public boolean isComplete(){
        for (Future future : futures) {
            if (!future.isDone())
                return false;
        }
        return true;
    }

}
