package bgu.spl.mics.application.passiveObjects;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
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
        HanSoloFinish=System.currentTimeMillis();
        C3POFinish=System.currentTimeMillis();
        R2D2Deactivate=System.currentTimeMillis();
        LeiaTerminate=System.currentTimeMillis();
        HanSoloTerminate=System.currentTimeMillis();
        C3POTerminate=System.currentTimeMillis();
        R2D2Terminate=System.currentTimeMillis();
        LandoTerminate=System.currentTimeMillis();

    }

    public static Diary getInstance(){
        if(instance==null)
        {
            instance=new Diary();
        }
        return instance;
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
        LeiaTerminate = System.currentTimeMillis()- LeiaTerminate;
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
        LandoTerminate =System.currentTimeMillis()- LandoTerminate;
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
        R2D2Terminate = System.currentTimeMillis()-R2D2Terminate;
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
        C3POTerminate = System.currentTimeMillis()-C3POTerminate;
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

        HanSoloTerminate =System.currentTimeMillis()- HanSoloTerminate;
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
        R2D2Deactivate =System.currentTimeMillis() - R2D2Deactivate;
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
        C3POFinish =System.currentTimeMillis() - C3POFinish;
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
        HanSoloFinish = System.currentTimeMillis()-HanSoloFinish;
    }

    public AtomicInteger getNumberOfAttacks() {
        return totalAttacks;
    }


}
