//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bgu.spl.mics.application.passiveObjects;

/**
 * public class that init all data  for the application
 */
public class InputApp {
    private Attack[] attacks;
    int R2D2;
    int Lando;
    int Ewoks;

    public InputApp() {
    }

    public int getEwoks() {
        return this.Ewoks;
    }

    public void setEwoks(int ewoks) {
        this.Ewoks = ewoks;
    }

    public int getLando() {
        return this.Lando;
    }

    public void setLando(int lando) {
        this.Lando = lando;
    }

    public int getR2D2() {
        return this.R2D2;
    }

    public void setR2D2(int r2d2) {
        this.R2D2 = r2d2;
    }

    public Attack[] getAttacks() {
        return this.attacks;
    }


    public void setAttacks(Attack[] attacks) {
        this.attacks = attacks;
    }
}
