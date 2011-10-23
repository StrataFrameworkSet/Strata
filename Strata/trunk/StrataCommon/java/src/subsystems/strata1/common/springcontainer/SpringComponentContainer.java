// ##########################################################################
// # File Name:	SpringComponentContainer.java
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

package strata1.common.springcontainer;


import strata1.common.container.ComponentContainer;
import strata1.common.container.ComponentDefinition;
import strata1.common.container.ComponentScope;
import strata1.common.container.ConstructorInjector;
import strata1.common.container.PropertyInjector;
import strata1.common.container.PropertyInjectorManager;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;
import java.util.ArrayList;
import java.util.List;

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
class SpringComponentContainer
	implements ComponentContainer
{
	private GenericApplicationContext itsContext;
	
	/************************************************************************
	 * Creates a new {@code SpringComponentContainer}. 
	 *
	 * @param context
	 */
	public 
	SpringComponentContainer(GenericApplicationContext context)
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
    registerDefinition(ComponentDefinition definition)
    {
        GenericBeanDefinition      bean = new GenericBeanDefinition();
        ConstructorInjector        constructor = null;
        PropertyInjectorManager    properties = null;
        List<String>               dependencies = new ArrayList<String>();
        
        bean.setBeanClass( definition.getComponentType() );
        
        if ( definition.hasConstructorInjector() )
            constructor = definition.getConstructorInjector();
        
        for (String value:constructor.getConstructorValues())
        {
            bean
                .getConstructorArgumentValues()
                .addGenericArgumentValue( 
                    new RuntimeBeanReference(value));
            
            dependencies.add( value );
        }
        
        properties = definition.getPropertyInjectorManager();
        
        for (PropertyInjector injector:properties.getInjectors())
        {
            bean
                .getPropertyValues()
                .add( 
                    injector.getPropertyName(),
                    new RuntimeBeanReference(
                        injector.getPropertyValue()));
            
            dependencies.add( injector.getPropertyValue() );
        }
        
        bean.setDependsOn( toArray(dependencies) );
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
	public 
	<T> T 
	getComponent(Class<T> componentClass,String componentName)
	{
		return componentClass.cast( itsContext.getBean( componentName ) );
	}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>boolean 
    hasComponent(Class<T> componentClass,String componentName)
    {
        try
        {
            return itsContext.isTypeMatch( componentName,componentClass );
        }
        catch (NoSuchBeanDefinitionException e)
        {
            return false;
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
    
    /************************************************************************
     *  
     *
     * @param input
     * @return
     */
    private String[]
    toArray(List<String> input)
    {
        String[] output = new String[input.size()];
        int      i      = 0;
        
        for (String s:input)
            output[i++] = s;
        
        return output;
    }
}


// ##########################################################################
