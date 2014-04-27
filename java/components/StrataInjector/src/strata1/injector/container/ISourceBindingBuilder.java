// ##########################################################################
// # File Name:	ISourceBindingBuilder.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.injector.container;

import javax.inject.Provider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ISourceBindingBuilder<T>
    extends IScopeBindingBuilder<T>
{
    public IScopeBindingBuilder<T>
    toType(Class<? extends T> type);
    
    public IScopeBindingBuilder<T>
    toProvider(Provider<? extends T> provider);
    
    public <P extends Provider<? extends T>> IScopeBindingBuilder<T>
    toProvider(Class<P> provider);
        
    public IBindingBuilder<T>
    toInstance(T instance);

}

// ##########################################################################
