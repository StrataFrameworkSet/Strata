// ##########################################################################
// # File Name:	JreType.java
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
import strata1.injector.reflection.IType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.inject.Inject;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JreType<T>
    implements IType<T>
{
    private final Class<T> itsImp;
    
    /************************************************************************
     * Creates a new {@code JreType}. 
     *
     */
    public 
    JreType(Class<T> imp)
    {
        itsImp = imp;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConstructor<T> 
    getConstructor(Class<? extends Annotation> annotation)
    {
        Constructor<?>[] constructors = itsImp.getConstructors();
        
        for (Constructor<?> constructor : constructors)
            if ( constructor.isAnnotationPresent( Inject.class ))
                return new JreConstructor<T>(constructor);
        
        throw 
            new IllegalStateException(
                "No suitable constructors for dependency injection." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConstructor<T> 
    getDefaultConstructor()
    {
        Constructor<?>[] constructors = itsImp.getConstructors();
        
        for (Constructor<?> constructor : constructors)
            if ( constructor.getParameterTypes().length == 0 )
                return new JreConstructor<T>(constructor);
               
        throw 
            new IllegalStateException(
                "No suitable constructors for dependency injection." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Field[] 
    getDeclaredFields()
    {
        return itsImp.getDeclaredFields();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Method[] 
    getMethods()
    {
        return itsImp.getMethods();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasConstructor(Class<? extends Annotation> annotation)
    {
        Constructor<?>[] constructors = itsImp.getConstructors();
        
        for (Constructor<?> constructor : constructors)
            if ( constructor.isAnnotationPresent( Inject.class ))
                return true;
        
        return false;
    }

    
}

// ##########################################################################
