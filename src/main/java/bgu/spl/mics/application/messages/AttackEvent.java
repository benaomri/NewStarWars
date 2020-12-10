package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.MessageBusImpl;
import  bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.LeiaMicroservice;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class AttackEvent implements Event<Boolean> {
    long duration;
    List<Integer>serials=new Vector<>();
    int serial;

    public AttackEvent()
    {

    }

    public AttackEvent(long otherDuration,List<Integer>otherSerials,int serial){
        duration=otherDuration;
        serials.addAll(otherSerials);
        serials.sort(Comparator.naturalOrder());
        this.serial=serial;
    }

    public void att() throws InterruptedException {
        Vector<Ewok> EwokList=Ewoks.getInstance().getEwokList();
        System.out.println(serials);
        //Acquire
        for (int i=0;i<serials.size();i++)
        {
            int serial=serials.get(i)-1;
            EwokList.get(serial).acquire();
        }
        Thread.sleep(duration);
        MessageBusImpl.getInstance().sendBroadcast(new LeiaMFinishAtt(serial));

        //Release
        for (int i=0;i<serials.size();i++)
        {
            int serial=serials.get(i)-1;
            EwokList.get(serial).release();
        }
        Diary.getInstance().incAtt();
        LeiaMicroservice.getFuture()[serial].resolve("");
    }
    public int getSerial(){
        return serial;
    }

}
