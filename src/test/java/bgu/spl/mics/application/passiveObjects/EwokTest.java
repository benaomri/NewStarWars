package bgu.spl.mics.application.passiveObjects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EwokTest {
    /**
     * Creating variables we use in all the tests
     * Before each test it will be initial
     */
    private Ewok EwokToTest;


    @BeforeEach
    void setUp() {
        EwokToTest=new Ewok(1) ;
    }


    @AfterEach
    void tearDown() {
    }

    /**
     * @PRE: available=false
     * @POST: available=true
     */
    @Test
    void acquire() {
        assertFalse(EwokToTest.available,"Check if acquire changed to False");
        EwokToTest.acquire();
//        assertTrue(EwokToTest.available,"Check if acquire changed to True");
    }

    /**
     * @PRE: available=true
     * @POST: available=false
     */
    @Test
    void release() {
        assertTrue(EwokToTest.available,"Check if release changed to True");
        EwokToTest.release();
        assertFalse(EwokToTest.available,"Check if release changed to False");
    }
}