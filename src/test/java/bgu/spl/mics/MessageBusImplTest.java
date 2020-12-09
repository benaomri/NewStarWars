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
     */
    MessageBus messageBusToCheck;
    MicroService testHanMS;
    MicroService testC3MS;

    @BeforeEach
    void setUp() {
        messageBusToCheck=MessageBusImpl.getInstance();
        testHanMS=new HanSoloMicroservice();
        testC3MS=new C3POMicroservice();
    }
    //No need to tear down
    @AfterEach
    void tearDown() {
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
     */
    @Test
    void complete() {
        Event Att=new AttackEvent();
        Future<String> mission=messageBusToCheck.sendEvent(Att);
        String result="Finished";
        messageBusToCheck.complete(Att,result); //Do complete and setting result
        assertEquals(result,mission.get(),"Check if the result we get is the same as we set"); //Checking if equal
    }

    /**
     * Check if the message send in the broadcast is the same as we get
     */
    @Test
    void sendBroadcast() {
        messageBusToCheck.register(testHanMS);
        messageBusToCheck.subscribeBroadcast(Broadcast.class,testHanMS);
        Broadcast broad=new Broadcast() {};
        testHanMS.sendBroadcast(broad); //Send the broadcast
        //We use try catch to get the exception from the method that throws it
        try {
            assertEquals(messageBusToCheck.awaitMessage(testHanMS),broad,"Check if the result we get is the same as we set");
        } catch (InterruptedException e) {
            fail("We caught a Exception and that means failure"); //if caught exception then fail
        }
    }

    /**
     * Check if the message send in the Event is the same as we get
     */
    @Test
    void sendEvent() {
        messageBusToCheck.register(testHanMS);
        AttackEvent Att=new AttackEvent();
        messageBusToCheck.subscribeEvent(Att.getClass(),testHanMS);
        messageBusToCheck.sendEvent(Att);
        testC3MS.sendEvent(Att);
        //We use try catch to get the exception from the method that throws it
        try {
            assertEquals(messageBusToCheck.awaitMessage(testHanMS),Att,"Check if the result we get is the same as we set");
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
        AttackEvent Att=new AttackEvent(); //Creating Att
        messageBusToCheck.subscribeEvent(Att.getClass(),testHanMS);
        messageBusToCheck.sendEvent(Att);
        //Checking the first scenario
        try {
            assertEquals(messageBusToCheck.awaitMessage(testHanMS),Att,"Check if the result we get is the same as we set");

        } catch (InterruptedException e) {
            fail("We caught a Exception and that means failure"); //if caught exception then fail
        }
        //Checking the second scenario
        AttackEvent Att1=new AttackEvent();
        try {
            Message msg=messageBusToCheck.awaitMessage(testHanMS);
            messageBusToCheck.sendEvent(Att1);
            assertEquals(msg,Att1,"Check if the result we get is the same as we set");
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



}