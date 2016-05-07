// ##########################################################################
// # File Name:	GwtType.java
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

package strata1.injector.gwtreflection;

import strata1.injector.reflection.IConstructor;
import strata1.injector.reflection.IType;
import com.google.gwt.reflect.shared.GwtReflect;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GwtType<T>
    implements IType<T>
{
    private final Class<T> itsImp;
    
    /************************************************************************
     * Creates a new {@code GwtType}. 
     *
     */
    public 
    GwtType(Class<T> type)
    {
        itsImp = type;
        GwtReflect.magicClass( itsImp );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConstructor<T> 
    getConstructor(Class<? extends Annotation> annotation)
    {
        for (Constructor<T> ctor:GwtReflect.getPublicConstructors( itsImp ))
            if ( ctor.isAnnotationPresent( annotation ) )
                return new GwtConstructor<T>(ctor);
        
         throw 
             new IllegalStateException(
                 "No annotated constructors for dependency injection." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConstructor<T> 
    getDefaultConstructor()
    {
        for (Constructor<T> ctor:GwtReflect.getPublicConstructors( itsImp ))
            if ( ctor.getParameterTypes().length == 0 )
                return new GwtConstructor<T>(ctor);
        
         throw 
             new IllegalStateException(
                 "No default constructor for dependency injection." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Field[] 
    getDeclaredFields()
    {
        return GwtReflect.getDeclaredFields( itsImp );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Method[] 
    getMethods()
    {
        return GwtReflect.getDeclaredMethods( itsImp );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasConstructor(Class<? extends Annotation> annotation)
    {
        for (Constructor<T> ctor:GwtReflect.getPublicConstructors( itsImp ))
            if ( ctor.isAnnotationPresent( annotation ) )
                return true;

        return false;
    }

}

// ##########################################################################
