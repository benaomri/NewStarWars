package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

/**
 * AttEvent class implement Event ,
 */
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
}
