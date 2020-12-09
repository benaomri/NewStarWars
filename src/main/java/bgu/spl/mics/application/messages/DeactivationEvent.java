package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import  bgu.spl.mics.application.passiveObjects.Diary;


public class DeactivationEvent implements Event<Boolean> {
    long duration;
    public DeactivationEvent() {

    }

    public DeactivationEvent(long duration)
    {
        this.duration=duration;
    }

    public void Deactive() throws InterruptedException {
            Thread.sleep(duration);
            Diary.getInstance().setR2D2Deactivate();

    }
}
