// ##########################################################################
// # File Name:	ILinkedBindingBuilder.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.injector.container;

import java.lang.annotation.Annotation;
import javax.inject.Provider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ILinkedBindingBuilder<T>
{
    public ILinkedBindingBuilder<T>
    toType(Class<? extends T> type);
    
    public ILinkedBindingBuilder<T>
    toType(TypeLiteral<? extends T> type);
    
    public <P extends Provider<? extends T>> ILinkedBindingBuilder<T>
    toProvider(Class<P> provider);
    
    public <P extends Provider<? extends T>> ILinkedBindingBuilder<T>
    toProvider(TypeLiteral<? extends P> provider);
    
    public ILinkedBindingBuilder<T>
    toInstance(T instance);
    
    public ILinkedBindingBuilder<T>
    withKey(String key);
    
    public <A extends Annotation> ILinkedBindingBuilder<T>
    withKey(A key);
    
    public ILinkedBindingBuilder<T>
    withLifetime(LifetimeKind lifetime);
}

// ##########################################################################
