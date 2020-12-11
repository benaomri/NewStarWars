package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.application.Main;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private static class SingeltonDiaryHolder{
        private static Diary instance=new Diary();
    }
    //Fields
    private AtomicInteger totalAttacks;
    private long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;

    //Singleton reference
    private  static Diary instance=null;


    /**
     * Init numberOfAttacks to zero
     */
    private Diary() {
        totalAttacks =new AtomicInteger(0);
        HanSoloFinish=0;
        C3POFinish=0;
        R2D2Deactivate=0;
        LeiaTerminate=0;
        HanSoloTerminate=0;
        C3POTerminate=0;
        R2D2Terminate=0;
        LandoTerminate=0;

    }

    public static Diary getInstance(){
       return SingeltonDiaryHolder.instance;
    }

    /**
     * Increment number of Attacks
     */
    public void incAtt()
    {
        totalAttacks.addAndGet(1);
    }

    /**
     *
     * @return LeiaTerminate
     */
    public long getLeiaTerminate() {
        return LeiaTerminate;
    }

    /**
     *
     * set LeiaTerminate
     */
    public void setLeiaTerminate() {
        LeiaTerminate = System.currentTimeMillis()- Main.getStartTime();
    }

    /**
     *
     * @return LandoTerminate
     */
    public long getLandoTerminate() {
        return LandoTerminate;
    }

    /**
     *
     * set LandoTerminate
     */
    public void setLandoTerminate() {
        LandoTerminate =System.currentTimeMillis()- Main.getStartTime();
    }

    /**
     *
     * @return R2D2Terminate
     */
    public long getR2D2Terminate() {
        return R2D2Terminate;
    }

    /**
     *
     * set R2D2Terminate
     */
    public void setR2D2Terminate() {
        R2D2Terminate = System.currentTimeMillis()-Main.getStartTime();
    }

    /**
     *
     * @return C3POTerminate
     */
    public long getC3POTerminate() {
        return C3POTerminate;
    }

    /**
     *
     * set C3POTerminate
     */
    public void setC3POTerminate() {
        C3POTerminate = System.currentTimeMillis()-Main.getStartTime();
    }

    /**
     *
     * @return HanSoloTerminate
     */
    public long getHanSoloTerminate() {
        return HanSoloTerminate;
    }


    /**
     *
     * set HanSoloTerminate
     */
    public void setHanSoloTerminate() {

        HanSoloTerminate =System.currentTimeMillis()- Main.getStartTime();
    }

    /**
     *
     * @return R2D2Deactivate
     */
    public long getR2D2Deactivate() {
        return R2D2Deactivate;
    }

    /**
     *
     * set R2D2Deactivate
     */
    public void setR2D2Deactivate() {
        R2D2Deactivate =System.currentTimeMillis() - Main.getStartTime();
    }

    /**
     *
     * @return C3POFinish
     */
    public long getC3POFinish() {
        return C3POFinish;
    }

    /**
     *
     * set C3POFinish
     */
    public void setC3POFinish() {
        C3POFinish =System.currentTimeMillis() - Main.getStartTime();
    }

    /**
     *
     * @return HanSoloFinish
     */
    public long getHanSoloFinish() {
        return HanSoloFinish;
    }

    /**
     *
     * set HanSoloFinish
     */
    public void setHanSoloFinish() {
        HanSoloFinish = System.currentTimeMillis()-Main.getStartTime();
    }

    public AtomicInteger getNumberOfAttacks() {
        return totalAttacks;
    }


}
