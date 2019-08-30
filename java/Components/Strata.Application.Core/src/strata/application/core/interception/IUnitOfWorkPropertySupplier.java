//////////////////////////////////////////////////////////////////////////////
// IUnitOfWorkPropertySupplier.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import strata.application.core.action.IActionQueue;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

import java.util.Properties;

public
interface IUnitOfWorkPropertySupplier
{
    IUnitOfWorkProvider
    getProvider();

    IActionQueue
    getQueue();

    Properties
    getConfiguration();
}

//////////////////////////////////////////////////////////////////////////////