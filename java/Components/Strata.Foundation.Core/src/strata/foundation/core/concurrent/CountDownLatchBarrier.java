//////////////////////////////////////////////////////////////////////////////
// CountDownLatchBarrier.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public
class CountDownLatchBarrier
    implements IBarrier
{
    private final CountDownLatch itsImp;

    public
    CountDownLatchBarrier() { itsImp = new CountDownLatch(1); }

    @Override
    public void
    await()
    {
        try
        {
            itsImp.await();
        }
        catch (InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean
    await(long timeout,TimeUnit unit)
    {
        try
        {
            return itsImp.await(timeout,unit);
        }
        catch (InterruptedException e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void
    signal()
    {
        itsImp.countDown();
    }
}

//////////////////////////////////////////////////////////////////////////////
