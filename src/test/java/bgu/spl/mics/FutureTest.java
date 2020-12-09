package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;

class FutureTest {
    private  Future<String> futureToTest;

    @BeforeEach
    void setUp() {
        futureToTest=new Future<String>();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * @PRE:
     * @POST:  Creaete object of futrue with isDone=false, and Result=null;
     */
    @Test
    void Future()
    {
        assertFalse(futureToTest.isDone(),"Check if Future isDone init  to False");
        assertNull(futureToTest.get(),"Check if Future Result is init to Null");
    }


    /**
     * Check if we get the same str as we inserted when we resolved the task
     */
    @Test
    void get() {
        String str="Result";
        futureToTest.resolve(str);
        assertEquals(futureToTest.get(),str,"Check if the result we get Equal to the result we Send");
    }


    /**
     * @PRE: not resolved
     * @POST: isDone=true,Result= the result we inserted
     */
    @Test
    void resolve() {
        assertFalse(futureToTest.isDone(),"Check if isDone is False ");
        String str = "Result";
        futureToTest.resolve(str);
        assertTrue(futureToTest.isDone(),"Check if isDone is True ");
        assertEquals(futureToTest.get(),str,"Check if the result we get Equal to the result we Send");
    }


    /**
     * @PRE: isDone =false
     * @POST: isDone=true
     */
    @Test
    void isDone() {
        assertFalse(futureToTest.isDone(),"Check if isDone is False" );
         futureToTest.resolve("finished");
        assertTrue(futureToTest.isDone(),"Check if isDone is True ");

    }
    /**
     * Check if we get the same str as we inserted when we resolved the task
     */
    @Test
    void testGet() {
        String str = "Result";
        assertFalse(futureToTest.isDone(),"Check if isDone is False ");
        futureToTest.get(300,TimeUnit.MILLISECONDS);
        assertFalse(futureToTest.isDone(),"Check if isDone is False after try to get in the first time");
        futureToTest.resolve(str);
        assertEquals(futureToTest.get(300,TimeUnit.MILLISECONDS),str,"Check if the result we get Equal to the result we Send");
    }
}