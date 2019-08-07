// ##########################################################################
// # File Name:	RequestScopedProvider.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.standardinjection;

import javax.inject.Provider;

/****************************************************************************
 * 
 */
public 
class RequestScopedProvider<T>
    extends    RequestScopedManager
    implements Provider<T>
{
    private final Provider<T> itsSource;
    private final String      itsKey;
    
    /************************************************************************
     * Creates a new RequestScopedProvider. 
     *
     */
    public 
    RequestScopedProvider(Provider<T> source)
    {
        itsSource = source;
        itsKey    = getClass().getName();
        
        if ( hasRequest() )
            getRequest().setAttribute( itsKey,itsSource.get() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public T 
    get()
    {
        if ( hasRequest() )
        {
            T output = (T)getRequest().getAttribute( itsKey );
            
            if ( output == null )
            {
                output = itsSource.get();           
                getRequest().setAttribute( itsKey,output );
                return output;
            }
            
            return output;
        }
        
        return itsSource.get();
    }
}

// ##########################################################################
