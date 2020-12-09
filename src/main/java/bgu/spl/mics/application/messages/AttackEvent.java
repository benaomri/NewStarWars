package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import  bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class AttackEvent implements Event<Boolean> {
    long duration;
    List<Integer>serials=new Vector<>();

    public AttackEvent()
    {

    }

    public AttackEvent(long otherDuration,List<Integer>otherSerials){
        duration=otherDuration;
        serials.addAll(otherSerials);
        serials.sort(Comparator.naturalOrder());
    }

    public void att() throws InterruptedException {
         Vector<Ewok> EwokList=Ewoks.getInstance().getEwokList();
        System.out.println(serials);
        //Acquire
        System.out.println("Start Acquire");
        for (int i=0;i<serials.size();i++)
        {
            int serial=serials.get(i)-1;
            System.out.println("Try to acquire "+serial);
            EwokList.get(serial).acquire();
            System.out.println("Finish to acquire "+serial);
        }
        System.out.println("Finish sleep");
        Thread.sleep(duration);
        System.out.println("Finish sleep");

        //Release
        for (int i=0;i<serials.size();i++)
        {
            int serial=serials.get(i)-1;
            EwokList.get(serial).release();
        }
        Diary.getInstance().incAtt();
    }



}
