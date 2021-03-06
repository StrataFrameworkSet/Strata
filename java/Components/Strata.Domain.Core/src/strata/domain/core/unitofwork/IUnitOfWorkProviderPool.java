//////////////////////////////////////////////////////////////////////////////
// IUnitOfWorkProviderPool.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.unitofwork;

import strata.foundation.core.pool.IPool;

public
interface IUnitOfWorkProviderPool
    extends IPool<IUnitOfWorkProvider,IUnitOfWorkProviderPool> {}

//////////////////////////////////////////////////////////////////////////////