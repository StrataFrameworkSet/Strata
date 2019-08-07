// ##########################################################################
// # File Name:	TypeBindingIdentifier.java
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

package strata.foundation.standardinjection;

import strata.foundation.injection.IBindingIdentifier;
import strata.foundation.injection.IBindingVisitor;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TypeBindingIdentifier<T>
    implements IBindingIdentifier<T>
{
    private Class<T> itsType;
    
    /************************************************************************
     * Creates a new {@code TypeBindingIdentifier}. 
     *
     */
    public 
    TypeBindingIdentifier(Class<T> type)
    {
        itsType = type;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
        if ( other instanceof TypeBindingIdentifier<?> )
            return 
                itsType.equals( 
                    ((TypeBindingIdentifier<?>)other).itsType );
        
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode()
    {
        int hash = 7;
        
        hash = 31 * hash + itsType.hashCode();
        return hash;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return "Type=" + itsType.toString();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    accept(IBindingVisitor<T> visitor)
    {
        visitor.visitIdentifier( this );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public Class<T> 
    getType()
    {
        return itsType;
    }

}

// ##########################################################################
