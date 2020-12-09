package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;


public class ResolveAtt implements Event<Boolean> {
    int serial;

    public ResolveAtt(int serial)
    {
        this.serial=serial;
    }

    public void resolve()
    {

    }

}
