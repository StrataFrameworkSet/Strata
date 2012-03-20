// ##########################################################################
// # File Name:	SpringContainerProvider.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
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
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.initializer.springprovider;

import strata1.initializer.provider.IComponentDefinition;
import strata1.initializer.provider.ComponentScope;
import strata1.initializer.provider.IConstructorInjector;
import strata1.initializer.provider.IContainerProvider;
import strata1.initializer.provider.IPropertyInjector;
import strata1.initializer.provider.PropertyInjectorManager;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

/****************************************************************************
 * {@code ComponentContainer} adapter for 
 * Spring {@code ApplicationContext}s.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SpringContainerProvider
	implements IContainerProvider
{
	private GenericApplicationContext itsContext;
	
	/************************************************************************
	 * Creates a new {@code SpringContainerProvider}. 
	 *
	 * @param context
	 */
	public 
	SpringContainerProvider(GenericApplicationContext context)
	{
		super();
		itsContext = context;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	public void 
	open()
	{
		itsContext.start();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	public void 
	refresh()
	{
		itsContext.refresh();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	public void 
	close()
	{
		itsContext.stop();
		itsContext.close();
	}
    
	/************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    registerDefinition(IComponentDefinition definition)
    {
        GenericBeanDefinition bean = new GenericBeanDefinition();
        
        bean.setBeanClass( definition.getComponentType() );
        registerConstructorInjector( definition,bean );
        registerPropertyInjectors( definition,bean );
        bean.setScope( toString( definition.getScope() ) );
        
        itsContext.registerBeanDefinition( 
            definition.getComponentName(),
            bean );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> void 
    registerInstance(String instanceName,T instance)
    {
        itsContext
            .getBeanFactory()
            .registerSingleton( instanceName,instance );
    }

    /************************************************************************
	 * {@inheritDoc} 
	 */
    @Override
	public <T> T 
	getInstance(Class<T> componentClass,String instanceName)
	{
		return componentClass.cast( itsContext.getBean( instanceName ) );
	}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasInstance(Class<T> componentClass,String instanceName)
    {
        try
        {
            return itsContext.isTypeMatch( instanceName,componentClass );
        }
        catch (NoSuchBeanDefinitionException e)
        {
            return false;
        }
    }

    /************************************************************************
     *  
     *
     * @param definition
     * @param bean
     */
    private void 
    registerConstructorInjector(
        IComponentDefinition definition,
        GenericBeanDefinition bean)
    {
       IConstructorInjector constructor = null;
       
       if ( definition.hasConstructorInjector() )
            constructor = definition.getConstructorInjector();
        
        for (String value:constructor.getConstructorValues())
        {
            bean
                .getConstructorArgumentValues()
                .addGenericArgumentValue( 
                    new RuntimeBeanReference(value));
        }
    }

    /************************************************************************
     *  
     *
     * @param definition
     * @param bean
     */
    private void 
    registerPropertyInjectors(
        IComponentDefinition definition,
        GenericBeanDefinition bean)
    {
        PropertyInjectorManager properties = 
            definition.getPropertyInjectorManager();
        
        for (IPropertyInjector injector:properties.getInjectors())
        {
            bean
                .getPropertyValues()
                .add( 
                    injector.getPropertyName(),
                    new RuntimeBeanReference(
                        injector.getPropertyValue()));
            
        }
    }

    /************************************************************************
     *  
     *
     * @param scope
     * @return
     */
    private String
    toString(ComponentScope scope)
    {
        switch (scope)
        {
        case SINGLETON:
            return "singleton";
        
        case PROTOTYPE:
            return "prototype";
        
        default:
            return "unknown";
        }
    }
}


// ##########################################################################
