// ##########################################################################
// # File Name:	GuiceScopeAdapter.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInjector Framework.
// #
// #   			The StrataInjector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInjector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInjector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.injector.guicecontainer;

import strata1.injector.container.IBindingIdentifier;
import strata1.injector.container.IBindingScope;
import strata1.injector.container.TypeAndAnnotationBindingIdentifier;
import strata1.injector.container.TypeAndNameBindingIdentifier;
import strata1.injector.container.TypeBindingIdentifier;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.name.Named;
import java.lang.annotation.Annotation;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GuiceScopeAdapter
    implements Scope
{
    private IBindingScope<?> itsAdaptee;
    
    /************************************************************************
     * Creates a new {@code GuiceScopeAdapter}. 
     *
     */
    public 
    GuiceScopeAdapter(IBindingScope<?> adaptee)
    {
        itsAdaptee = adaptee;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> Provider<T> 
    scope(Key<T> key,Provider<T> source)
    {
        return 
            new GuiceProvider<T>( 
                itsAdaptee.getScopedProvider( 
                    new JavaxProvider<T>(source) ));
    }

    /************************************************************************
     *  
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> IBindingIdentifier<T>
    toIdentifier(Key<T> key)
    {
        Class<? extends Annotation> annotationType = key.getAnnotationType();
        Annotation                  annotation = key.getAnnotation();
        
        if ( annotationType == null  )
            return 
                new TypeBindingIdentifier<T>((Class<T>)key.getTypeLiteral().getType());
        
        if ( annotationType.isAssignableFrom( Named.class ))
            return 
                new TypeAndNameBindingIdentifier<T>(
                    (Class<T>)key.getTypeLiteral().getType(),
                    ((Named)annotation).value() );
        
        return 
            new TypeAndAnnotationBindingIdentifier<T>(
                (Class<T>)key.getTypeLiteral().getType(),
                annotationType);
    }
}

// ##########################################################################
