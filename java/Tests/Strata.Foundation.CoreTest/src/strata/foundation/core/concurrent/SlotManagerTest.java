//////////////////////////////////////////////////////////////////////////////
// SlotManagerTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertFalse;
import static strata.foundation.core.utility.Awaiter.await;

public
class SlotManagerTest
{
    private ISlotManager itsTarget;

    @Before
    public void
    setUp()
    {
        itsTarget = new SlotManager(4);
    }

    @After
    public void
    tearDown()
    {
        itsTarget = null;
    }

    @Test
    public void
    testCheckOut()
    {
        Slot slot1 = itsTarget.checkOut();
        Slot slot2 = itsTarget.checkOut();
        Slot slot3 = itsTarget.checkOut();
        Slot slot4 = itsTarget.checkOut();

        assertFalse(itsTarget.hasAvailability());
    }

    @Test
    public void
    testCheckOutMultiThreaded()
    {
        CompletionStage<Boolean> slot1 =
            CompletableFuture
                .supplyAsync(() -> itsTarget.checkOut())
                .thenApply(
                    slot ->
                    {
                        sleep(500);
                        itsTarget.checkIn(slot);
                        return itsTarget.hasAvailability();
                    });
        CompletionStage<Boolean> slot2 =
            CompletableFuture
                .supplyAsync(() -> itsTarget.checkOut())
                .thenApply(
                    slot ->
                    {
                        sleep(500);
                        itsTarget.checkIn(slot);
                        return itsTarget.hasAvailability();
                    });
        CompletionStage<Boolean> slot3 =
            CompletableFuture
                .supplyAsync(() -> itsTarget.checkOut())
                .thenApply(
                    slot ->
                    {
                        sleep(500);
                        itsTarget.checkIn(slot);
                        return itsTarget.hasAvailability();
                    });
        CompletionStage<Boolean> slot4 =
            CompletableFuture
                .supplyAsync(() -> itsTarget.checkOut())
                .thenApply(
                    slot ->
                    {
                        sleep(500);
                        itsTarget.checkIn(slot);
                        return itsTarget.hasAvailability();
                    });
        CompletionStage<Boolean> slot5 =
            CompletableFuture
                .supplyAsync(() -> itsTarget.checkOut())
                .thenApply(
                    slot ->
                    {
                        sleep(500);
                        itsTarget.checkIn(slot);
                        return itsTarget.hasAvailability();
                    });
        CompletionStage<Boolean> slot6 =
            CompletableFuture
                .supplyAsync(() -> itsTarget.checkOut())
                .thenApply(
                    slot ->
                    {
                        sleep(500);
                        itsTarget.checkIn(slot);
                        return itsTarget.hasAvailability();
                    });


        await(slot1);
        await(slot2);
        await(slot3);
        await(slot4);
        await(slot5);
        await(slot6);
    }

    private void
    sleep(int millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {}
    }
}

//////////////////////////////////////////////////////////////////////////////
