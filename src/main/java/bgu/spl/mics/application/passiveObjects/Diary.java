package bgu.spl.mics.application.passiveObjects;



import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {


    public void resetNumberAttacks() {
        totalAttacks.set(0);
    }

    private static class SingletonDiaryHolder {
        private static final Diary instance=new Diary();
    }
    //Fields
    private final AtomicInteger totalAttacks;
    private long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;


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
       return SingletonDiaryHolder.instance;
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
     * set LeiaTerminate
     */
    public void setLeiaTerminate() { LeiaTerminate = System.currentTimeMillis();
    }

    /**
     *
     * set LandoTerminate
     */
    public void setLandoTerminate() {
        LandoTerminate =System.currentTimeMillis();
    }

    /**
     *
     * set R2D2Terminate
     */
    public void setR2D2Terminate() {
        R2D2Terminate = System.currentTimeMillis();
    }


    /**
     *
     * set C3POTerminate
     */
    public void setC3POTerminate() {
        C3POTerminate = System.currentTimeMillis();
    }



    /**
     *
     * set HanSoloTerminate
     */
    public void setHanSoloTerminate() {HanSoloTerminate =System.currentTimeMillis();
    }


    /**
     *
     * set R2D2Deactivate
     */
    public void setR2D2Deactivate() {
        R2D2Deactivate =System.currentTimeMillis();
    }

    /**
     *
     * set C3POFinish
     */
    public void setC3POFinish() {
        C3POFinish =System.currentTimeMillis();
    }

    /**
     *
     * set HanSoloFinish
     */
    public void setHanSoloFinish() {
        HanSoloFinish = System.currentTimeMillis();
    }

    public long getHanSoloFinish() {
        return HanSoloFinish;
    }

    public long getC3POFinish() {
        return C3POFinish;
    }

    public long getR2D2Deactivate() {
        return R2D2Deactivate;
    }

    public long getLeiaTerminate() {
        return LeiaTerminate;
    }

    public long getHanSoloTerminate() {
        return HanSoloTerminate;
    }

    public long getC3POTerminate() {
        return C3POTerminate;
    }

    public long getR2D2Terminate() {
        return R2D2Terminate;
    }

    public long getLandoTerminate() {
        return LandoTerminate;
    }

    public AtomicInteger getNumberOfAttacks() {
        return totalAttacks;
    }
}
