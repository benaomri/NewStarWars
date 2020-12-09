package bgu.spl.mics.application.services;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminateBroadCast;
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
    private Attack[] attacks;
    //    private Map<Message, CallbackImpl> callbackMap;
    static AtomicBoolean FinishedSend;


    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
        this.attacks = attacks;
        FinishedSend=new AtomicBoolean(false);
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
        sendAttEvent();
    }
    private void sendAttEvent(){
        int i=1;
        for(Attack att:attacks){
            sendEvent(new AttackEvent(att.getDuration(),att.getSerials(),i));
            i++;
        }


    }

    @Override
    protected void close()
    {
        Diary.getInstance().setLeiaTerminate();
    }
}
