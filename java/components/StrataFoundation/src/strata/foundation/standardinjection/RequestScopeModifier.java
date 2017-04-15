// ##########################################################################
// # File Name:	RequestScopeModifier.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.standardinjection;

import javax.inject.Provider;
import strata.foundation.injection.IBindingVisitor;
import strata.foundation.injection.IScopeModifier;


/****************************************************************************
 * 
 */
public 
class RequestScopeModifier
    implements IScopeModifier
{
    
    /************************************************************************
     * Creates a new RequestScopeModifier. 
     *
     */
    public 
    RequestScopeModifier() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> void 
    accept(IBindingVisitor<T> visitor)
    {
        visitor.visitScope( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> Provider<T> 
    getScopedProvider(Provider<T> source)
    {
        return new RequestScopedProvider<T>(source);
    }

}

// ##########################################################################
