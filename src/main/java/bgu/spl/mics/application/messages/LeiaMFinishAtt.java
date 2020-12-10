package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class LeiaMFinishAtt implements Broadcast {
    int serial;
    public  LeiaMFinishAtt(int serial){
        this.serial=serial;
    }

    public int getSerial() {
        return serial;
    }
}
