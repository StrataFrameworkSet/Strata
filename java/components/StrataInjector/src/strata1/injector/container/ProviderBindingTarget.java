// ##########################################################################
// # File Name:	ProviderBindingTarget.java
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
class ProviderBindingTarget<T>
    implements IBindingTarget<T>
{
    private Class<? extends Provider<? extends T>> itsType;
    
    /************************************************************************
     * Creates a new {@code ProviderBindingTarget}. 
     *
     * @param providerType
     */
    public 
    ProviderBindingTarget(Class<? extends Provider<? extends T>> type)
    {
        itsType = type;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    accept(IBindingVisitor<T> visitor)
    {
        visitor.visitTarget( this );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public Class<? extends Provider<? extends T>> 
    getProviderType()
    {
        return itsType;
    }

}

// ##########################################################################
