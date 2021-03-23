//////////////////////////////////////////////////////////////////////////////
// IAction.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.presenter;

import java.util.concurrent.CompletionStage;

public
interface IAction<M>
{
    Class<M>
    getKey();

    CompletionStage<M>
    apply(M model);
}

//////////////////////////////////////////////////////////////////////////////