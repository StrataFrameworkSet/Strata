// ##########################################################################
// # File Name:	JreConstructor.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.injector.jrereflection;

import strata1.injector.reflection.IConstructor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JreConstructor<T>
    implements IConstructor<T>
{
    private final Constructor<?> itsImp;
    
    /************************************************************************
     * Creates a new {@code JreConstructor}. 
     *
     */
    public 
    JreConstructor(Constructor<?> imp)
    {
        itsImp = imp;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public T 
    create()
    {
        try
        {
            return (T)itsImp.newInstance( new Object[0] );
        }
        catch(Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public T 
    create(Object...arguments)
    {
        try
        {
            return (T)itsImp.newInstance( arguments );
        }
        catch(Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Class<?>[] 
    getParameterTypes()
    {
        return itsImp.getParameterTypes();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Annotation[][] 
    getParameterAnnotations()
    {
        return itsImp.getParameterAnnotations();
    }

}

// ##########################################################################
