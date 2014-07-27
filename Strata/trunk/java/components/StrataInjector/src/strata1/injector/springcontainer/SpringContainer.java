// ##########################################################################
// # File Name:	SpringContainer.java
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

package strata1.injector.springcontainer;

import strata1.injector.container.IBinding;
import strata1.injector.container.IBindingBuilder;
import strata1.injector.container.IBindingIdentifier;
import strata1.injector.container.IContainer;
import strata1.injector.old.ITypeDefinition;
import org.springframework.context.support.GenericApplicationContext;
import java.lang.annotation.Annotation;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SpringContainer
    implements IContainer
{
    private GenericApplicationContext itsContext;
    
    /************************************************************************
     * Creates a new {@code SpringContainer}. 
     *
     */
    public 
    SpringContainer()
    {
        itsContext = new GenericApplicationContext();
    }

    /************************************************************************
     * Creates a new {@code SpringContainer}. 
     *
     */
    public 
    SpringContainer(GenericApplicationContext context)
    {
        itsContext = context;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IContainer 
    insertBinding(IBindingBuilder<T> builder)
    {
        return insertBinding( builder.getBinding() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IContainer 
    insertBinding(IBinding<T> binding)
    {
        //binding.accept( new SpringBindingVisitor<T> );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type)
    {
        String beanName = type.getName();
        
        return getInstance( type,beanName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,String name)
    {
        return type.cast( itsContext.getBean( name ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>T 
    getInstance(Class<T> type,Class<? extends Annotation> key)
    {
        return getInstance( type,key.toString() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>T getInstance(IBindingIdentifier<T> id)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>boolean hasBinding(Class<T> type)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>boolean hasBinding(Class<T> type,String key)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>boolean hasBinding(Class<T> type,Class<? extends Annotation> key)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>boolean 
    hasBinding(IBindingIdentifier<T> id)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     *  
     */
    protected void 
    registerType(ITypeDefinition definition)
    {
        String beanName = null;
        
        if ( definition.hasName() )
            beanName = definition.getName();
        else
            beanName = definition.getInterfaceType().getName();
        
        itsContext.registerBeanDefinition( 
            beanName,
            ((SpringTypeDefinition)definition).getBeanDefinition() );
    }
}

// ##########################################################################
