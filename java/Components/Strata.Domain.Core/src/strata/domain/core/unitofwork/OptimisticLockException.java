//////////////////////////////////////////////////////////////////////////////
// OptimisticLockException.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.unitofwork;

public
class OptimisticLockException
    extends CommitFailedException
{
    public
    OptimisticLockException(String message)
    {
        super(message);
    }

    public
    OptimisticLockException(String message,Throwable cause)
    {
        super(message,cause);
    }
}

//////////////////////////////////////////////////////////////////////////////
