//////////////////////////////////////////////////////////////////////////////
// RequestOrThreadScope.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;


import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import org.jboss.resteasy.core.ResteasyContext;
import strata.foundation.core.inject.GuiceThreadScope;

public
class RequestOrThreadScope
    implements Scope
{
    private final Scope itsImplementation;

    public
    RequestOrThreadScope()
    {
        if (ResteasyContext.getContextDataMap() != null)
            itsImplementation = new RequestScope();
        else
            itsImplementation = new GuiceThreadScope();
    }

    @Override
    public <T> Provider<T>
    scope(Key<T> key,Provider<T> provider)
    {
        return itsImplementation.scope(key,provider);
    }
}

//////////////////////////////////////////////////////////////////////////////
