// ##########################################################################
// # File Name:	TypeAndAnnotationBindingIdentifier.java
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

import java.lang.annotation.Annotation;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TypeAndAnnotationBindingIdentifier<T>
    implements IBindingIdentifier<T>
{
    private Class<T>                    itsType;
    private Class<? extends Annotation> itsAnnotationKey;
    
    /************************************************************************
     * Creates a new {@code TypeAndAnnotationBindingIdentifier}. 
     *
     */
    public 
    TypeAndAnnotationBindingIdentifier(
        Class<T>                    type,
        Class<? extends Annotation> annotation)
    {
        itsType = type;
        itsAnnotationKey = annotation;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object o)
    {
        if ( o instanceof TypeAndAnnotationBindingIdentifier<?> )
        {
            TypeAndAnnotationBindingIdentifier<?> other = 
                (TypeAndAnnotationBindingIdentifier<?>)o;
            
            return 
                itsType.equals( other.itsType ) &&
                itsAnnotationKey.equals( other.itsAnnotationKey );
        }
        
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
        hash = 31 * hash + itsAnnotationKey.hashCode();
        return hash;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return 
            "Type=" + itsType.toString() + 
            ":Annotation=" + itsAnnotationKey.toString();
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
    public Class<? extends Annotation>
    getKey()
    {
        return itsAnnotationKey;
    }

}

// ##########################################################################
