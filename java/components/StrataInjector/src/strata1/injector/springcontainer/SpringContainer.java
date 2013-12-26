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

import strata1.injector.container.ConstructorInjector;
import strata1.injector.container.IConstructorInjector;
import strata1.injector.container.IContainer;
import strata1.injector.container.ILinkedBindingBuilder;
import strata1.injector.container.IPropertyInjector;
import strata1.injector.container.ITypeDefinition;
import strata1.injector.container.PropertyInjector;
import strata1.injector.container.TypeLiteral;
import org.springframework.context.support.GenericApplicationContext;

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
    public <T> ILinkedBindingBuilder<T>
    bindType(Class<T> type)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> ILinkedBindingBuilder<T> 
    bindType(TypeLiteral<T> type)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
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

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> void 
    registerInstance(T instance)
    {
        registerInstance( 
            instance.getClass().getName(),
            instance );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> void 
    registerInstance(String name,T instance)
    {
        itsContext
            .getBeanFactory()
            .registerSingleton( name,instance );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    resolveType(Class<T> type)
    {
        String beanName = type.getName();
        
        return type.cast( itsContext.getBean( beanName ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    resolveType(Class<T> type,String name)
    {
        return type.cast( itsContext.getBean( name ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    resolveInstance(Class<T> type)
    {
        String beanName = type.getName();
        
        return resolveInstance( type,beanName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    resolveInstance(Class<T> type,String name)
    {
        return 
            type.cast( 
                itsContext
                    .getBeanFactory()
                    .getSingleton( name ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasType(ITypeDefinition definition)
    {
        String beanName = null;
        
        if ( definition.hasName() )
            beanName = definition.getName();
        else
            beanName = definition.getInterfaceType().getName();

        return hasType( definition.getInterfaceType(),beanName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasType(Class<?> type)
    {
        String beanName = type.getName();
        
        return hasType( type,beanName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasType(Class<?> type,String name)
    {
        return itsContext.isTypeMatch( name,type );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasInstance(Class<?> type)
    {
        String beanName = type.getName();
        
        return hasInstance( type,beanName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasInstance(Class<?> type,String name)
    {
        return 
            itsContext
                .getBeanFactory()
                .containsSingleton( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ITypeDefinition 
    createTypeDefinition()
    {
        return new SpringTypeDefinition();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConstructorInjector 
    createConstructorInjector()
    {
        return new ConstructorInjector();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPropertyInjector 
    createPropertyInjector()
    {
        return new PropertyInjector();
    }

}

// ##########################################################################
