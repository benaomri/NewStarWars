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
    List<Integer> serials=new Vector<>();
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


    public  List<Integer> getSerials()
    {
        return serials;
    }

    public long getDuration()
    {
        return duration;
    }
    public int getSerial(){return serial;}
}
