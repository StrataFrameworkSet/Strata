//////////////////////////////////////////////////////////////////////////////
// IUnitOfWorkPropertySupplier.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.foundation.core.action.IActionQueue;

public
interface IUnitOfWorkPropertySupplier
{
    IUnitOfWorkProvider
    getProvider();

    IActionQueue
    getQueue();
}

//////////////////////////////////////////////////////////////////////////////