package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;

public class LeiaMFinishAtt implements Broadcast {
    Event<Boolean> e;
    public  LeiaMFinishAtt(Event<Boolean> e){
        this.e=e;
    }

    public Event<Boolean> getEvent() {
        return e;
    }
}
