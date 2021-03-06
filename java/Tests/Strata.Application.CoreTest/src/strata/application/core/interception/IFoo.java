//////////////////////////////////////////////////////////////////////////////
// IFoo.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import java.util.concurrent.CompletionStage;

public
interface IFoo
{
    @Timed
    void
    synchronousMethod()
        throws Exception;

    @Timed
    CompletionStage<String>
    asynchronousMethod(String input);
}

//////////////////////////////////////////////////////////////////////////////
