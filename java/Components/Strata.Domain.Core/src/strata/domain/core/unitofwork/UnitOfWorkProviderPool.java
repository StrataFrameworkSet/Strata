//////////////////////////////////////////////////////////////////////////////
// UnitOfWorkProviderPool.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.unitofwork;

import strata.foundation.core.pool.FixedSizePool;

import java.util.function.Supplier;

public
class UnitOfWorkProviderPool
    extends FixedSizePool<IUnitOfWorkProvider,IUnitOfWorkProviderPool>
    implements IUnitOfWorkProviderPool
{
    public
    UnitOfWorkProviderPool(int size,Supplier<IUnitOfWorkProvider> supplier)
    {
        super(size,supplier);
    }
}

//////////////////////////////////////////////////////////////////////////////
