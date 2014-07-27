// ##########################################################################
// # File Name:	ConstructorBasedProvider.java
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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ConstructorBasedProvider<T>
    implements Provider<T>
{
    private IContainer         itsContainer;
    private Class<? extends T> itsType;
    private Constructor<?>     itsConstructor;
    
    /************************************************************************
     * Creates a new {@code ConstructorBasedProvider}. 
     *
     */
    public 
    ConstructorBasedProvider(
        IContainer         container,
        Class<? extends T> type,
        Constructor<?>     constructor)
    {
        itsContainer   = container;
        itsType        = type;
        itsConstructor = constructor;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public T 
    get()
    {
        try
        {
            T instance = 
                itsType.cast( 
                    itsConstructor.newInstance( getArguments() ) );       
            
            performFieldInjection(instance);
            performMethodInjection(instance);
            return instance;
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }

    }

    /************************************************************************
     *  
     *
     * @return
     */
    private Object[] 
    getArguments()
    {
        List<Object> arguments = new ArrayList<Object>();
        
        for (IBindingIdentifier<?> identifier : getBindingIdentifiers())
        {
            if ( !itsContainer.hasBinding( identifier ) )
                throw new IllegalStateException();
            
            arguments.add( itsContainer.getInstance( identifier ) );
        }
        
        return arguments.toArray();
    }


    /************************************************************************
     *  
     *
     * @return
     */
    private Object 
    getArgument(Field field)
    {
        IBindingIdentifier<?> identifier = getBindingIdentifier(field);
        
        if ( !itsContainer.hasBinding( identifier ) )
            throw new IllegalStateException();
        
        return itsContainer.getInstance( identifier );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<IBindingIdentifier<?>> 
    getBindingIdentifiers()
    {
        List<IBindingIdentifier<?>> identifiers =
            new ArrayList<IBindingIdentifier<?>>();
        Class<?>[] parameters = 
            itsConstructor.getParameterTypes();
        Annotation[][] annotations = 
            itsConstructor.getParameterAnnotations();
        
        if ( annotations.length != parameters.length )
            throw new IllegalStateException( "annotations != parameters" );
        
        for (int i=0;i<parameters.length;i++)
        {
            Class<?>     p = parameters[i];
            Annotation[] a = annotations[i];
            
            if ( a.length == 0 )
                identifiers.add( new TypeBindingIdentifier<Object>((Class<Object>)p) );
            else if ( a.length == 1 )
            {
                if ( a[0].annotationType().equals( Named.class ))
                    identifiers.add( 
                        new TypeAndNameBindingIdentifier<Object>(
                            (Class<Object>)p,
                            ((Named)a[0]).value()) );
                else
                    identifiers.add( 
                        new TypeAndAnnotationBindingIdentifier<Object>(
                            (Class<Object>)p,
                            a[0].annotationType()) );
            }
        }
        
        return identifiers;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private IBindingIdentifier<?> 
    getBindingIdentifier(Field field)
    {
        Class<?>     type = field.getType();
        Annotation[] annotations = field.getAnnotations();
        
        if ( annotations.length == 1 )
            return new TypeBindingIdentifier<Object>((Class<Object>)type );
        else if ( annotations.length == 2 )
        {
            if ( annotations[1].annotationType().equals( Named.class ))
                return 
                    new TypeAndNameBindingIdentifier<Object>(
                        (Class<Object>)type,
                            ((Named)annotations[1]).value());
            else
                return
                    new TypeAndAnnotationBindingIdentifier<Object>(
                        (Class<Object>)type,
                        annotations[1].annotationType());
        }
        
        throw new IllegalStateException("field annotation error");
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<IBindingIdentifier<?>> 
    getBindingIdentifiers(Method method)
    {
        List<IBindingIdentifier<?>> identifiers =
            new ArrayList<IBindingIdentifier<?>>();
        Class<?>[] parameters = 
            method.getParameterTypes();
        Annotation[][] annotations = 
            method.getParameterAnnotations();
        
        if ( annotations.length != parameters.length )
            throw new IllegalStateException( "annotations != parameters" );
        
        for (int i=0;i<parameters.length;i++)
        {
            Class<?>     p = parameters[i];
            Annotation[] a = annotations[i];
            
            if ( a.length == 0 )
                identifiers.add( new TypeBindingIdentifier<Object>((Class<Object>)p) );
            else if ( a.length == 1 )
            {
                if ( a[0].annotationType().equals( Named.class ))
                    identifiers.add( 
                        new TypeAndNameBindingIdentifier<Object>(
                            (Class<Object>)p,
                            ((Named)a[0]).value()) );
                else
                    identifiers.add( 
                        new TypeAndAnnotationBindingIdentifier<Object>(
                            (Class<Object>)p,
                            a[0].annotationType()) );
            }
        }
        
        return identifiers;
    }

    /************************************************************************
     *  
     *
     * @param instance
     */
    private void 
    performFieldInjection(T instance)
    {
        for (Field field : itsType.getDeclaredFields())
            if ( field.isAnnotationPresent( Inject.class ))
                injectMember( instance,field );
    }

    /************************************************************************
     *  
     *
     * @param instance
     */
    private void 
    performMethodInjection(T instance)
    {
        for (Method method : itsType.getMethods())
            if ( method.isAnnotationPresent( Inject.class ))
                injectMember( instance,method );
    }

    /************************************************************************
     *  
     *
     * @param method
     */
    private void 
    injectMember(T instance,Field field)
    {
        try
        {
            field.setAccessible( true );
            field.set( instance,getArgument(field) );
        }
        catch(Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************************
     *  
     *
     * @param method
     */
    private void 
    injectMember(T instance,Method method)
    {
        try
        {
            method.setAccessible( true );
            method.invoke( instance,getArguments(method) );
        }
        catch(Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private Object[] 
    getArguments(Method method)
    {
        List<Object> arguments = new ArrayList<Object>();
        
        for (IBindingIdentifier<?> identifier : getBindingIdentifiers(method))
        {
            if ( !itsContainer.hasBinding( identifier ) )
                throw new IllegalStateException();
            
            arguments.add( itsContainer.getInstance( identifier ) );
        }
        
        return arguments.toArray();
    }

}

// ##########################################################################
