package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {
    /**
     * Creating variables we use in all the tests
     * Before each test it will be initial
     *
     * We changed few things:
     *  1. Creating class for only tests: MicroServiceTest,TestEvent,TestB
     *  
     */
    MessageBusImpl messageBusToCheck;
    MicroService testMS1,testMS2;


    @BeforeEach
    void setUp() {
        messageBusToCheck=MessageBusImpl.getInstance();
        testMS1= new MicroServiceTest();
        testMS2= new MicroServiceTest();
    }
    @AfterEach
    void tearDown() {
        messageBusToCheck.unregister(testMS1);
        messageBusToCheck.unregister(testMS2);

    }

    /**
     * We use  subscribeEvent() in sendEvent(), so it is been checked there
     */
    @Test
    void subscribeEvent() {
    }
    /**
     * We use  subscribeBroadcast() in sendBroadcast(), so it is been checked there
     */
    @Test
    void subscribeBroadcast() {
    }


    /**
     * Testing if after finished event we get the same result
     *
     * Change: We didn't subscribe before for Event
     */
    @Test
    void complete() {
        TestEvent event= new TestEvent();
        messageBusToCheck.register(testMS1);
        messageBusToCheck.subscribeEvent(event.getClass(),testMS1);
        Future<String> mission=messageBusToCheck.sendEvent(event);
        String result="Finished";
        messageBusToCheck.complete(event,result); //Do complete and setting result
        assertEquals(result,mission.get(),"Check if the result we get is the same as we set"); //Checking if equal

    }

    /**
     * Check if the message send in the broadcast is the same as we get
     */
    @Test
    void sendBroadcast() {
        messageBusToCheck.register(testMS1);
        TestB broad= new TestB();
        messageBusToCheck.subscribeBroadcast(broad.getClass(),testMS1);
        testMS1.sendBroadcast(broad); //Send the broadcast
        //We use try catch to get the exception from the method that throws it
        try {
            assertEquals(messageBusToCheck.awaitMessage(testMS1),broad,"Check if the result we get is the same as we set");
        } catch (InterruptedException e) {
            fail("We caught a Exception and that means failure"); //if caught exception then fail
        }
    }

    /**
     * Check if the message send in the Event is the same as we get
     */
    @Test
    void sendEvent() {
        messageBusToCheck.register(testMS1);
        AttackEvent Att=new AttackEvent();
        messageBusToCheck.subscribeEvent(Att.getClass(),testMS1);
        messageBusToCheck.sendEvent(Att);
        testMS2.sendEvent(Att);
        //We use try catch to get the exception from the method that throws it
        try {
            assertEquals(messageBusToCheck.awaitMessage(testMS1),Att,"Check if the result we get is the same as we set");
        } catch (InterruptedException e) {
            fail(); //if caught exception then fail
        }
    }

    /**
     *  Check if it gets the right Event from Q
     *  We checking 2 scenarios:
     *      1. Getting the event from Q while Q has events waiting
     *      2. Getting the event from Q while need to wait for sending the event
     */
    @Test
    void awaitMessage() {
        TestEvent event= new TestEvent(); //Creating Att
        messageBusToCheck.register(testMS1);
        messageBusToCheck.subscribeEvent(event.getClass(),testMS1);
        messageBusToCheck.sendEvent(event);
        //Checking the first scenario
        try {
            assertEquals(messageBusToCheck.awaitMessage(testMS1),event,"Check if the result we get is the same as we set");

        } catch (InterruptedException e) {
            fail("We caught a Exception and that means failure"); //if caught exception then fail
        }


    }

    /**
     *
     * We check them in sending event and Broadcast
     */
    @Test
    void register() {
    }

    /**
     * As written in the forum we don't check it
     */
    @Test
    void unregister() {
    }

    /**
     * We created new Class for Tests
     */
    //Tests Class
    public static class TestB implements Broadcast {
        public TestB(){

        }
    }
    public static class TestEvent implements Event<Boolean>{
        public TestEvent(){

        }
    }
    public static class MicroServiceTest extends MicroService {

        public MicroServiceTest() {
            super("Test");
        }

        @Override
        protected void initialize() {

        }

        @Override
        protected void close() {

        }
    }
}