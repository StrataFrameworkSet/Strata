//////////////////////////////////////////////////////////////////////////////
// RequestOrOperationScope.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;


import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import org.jboss.resteasy.core.ResteasyContext;
import strata.foundation.core.inject.OperationScope;

public
class RequestOrOperationScope
    implements Scope
{
    private final Scope itsImplementation;

    public RequestOrOperationScope()
    {
        if (ResteasyContext.getContextDataMap() != null)
            itsImplementation = new GuiceRequestScope();
        else
            itsImplementation = new OperationScope();
    }

    @Override
    public <T> Provider<T>
    scope(Key<T> key,Provider<T> provider)
    {
        return itsImplementation.scope(key,provider);
    }
}

//////////////////////////////////////////////////////////////////////////////
