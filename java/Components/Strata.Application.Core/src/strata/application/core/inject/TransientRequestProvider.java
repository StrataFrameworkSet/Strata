//////////////////////////////////////////////////////////////////////////////
// TransientOperationProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;

import com.google.inject.Injector;

import javax.inject.Inject;

public
class TransientRequestProvider
    implements IRequestProvider
{
    private final Injector itsInjector;

    @Inject
    public
    TransientRequestProvider(Injector injector)
    {
        itsInjector = injector;
    }

    @Override
    public Request
    get()
    {
        return new Request(itsInjector);
    }
}

//////////////////////////////////////////////////////////////////////////////
