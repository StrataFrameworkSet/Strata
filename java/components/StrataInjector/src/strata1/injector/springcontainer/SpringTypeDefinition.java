// ##########################################################################
// # File Name:	SpringTypeDefinition.java
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

import strata1.injector.containerprovider.IConstructorInjector;
import strata1.injector.containerprovider.IPropertyInjector;
import strata1.injector.old.LifetimeKind;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SpringTypeDefinition
{
    private Class<?>                      itsInterfaceType;
    private Class<?>                      itsImplementationType;
    private String                        itsName;
    private LifetimeKind                  itsLifetime;
    private IConstructorInjector          itsConstructorInjector;
    private Map<String,IPropertyInjector> itsPropertyInjectors;
    
    /************************************************************************
     * Creates a new {@code SpringTypeDefinition}. 
     *
     */
    public 
    SpringTypeDefinition()
    {
        itsInterfaceType       = null;
        itsImplementationType  = null;
        itsName                = null;
        itsLifetime            = LifetimeKind.INSTANCE_PER_PROCESS;
        itsConstructorInjector = null;
        itsPropertyInjectors   = new HashMap<String,IPropertyInjector>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public SpringTypeDefinition 
    setInterfaceType(Class<?> type)
    {
        itsInterfaceType      = type;
        itsImplementationType = type;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public SpringTypeDefinition 
    setImplementationType(Class<?> implementationType)
    {
        itsImplementationType = implementationType;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public SpringTypeDefinition 
    setName(String name)
    {
        itsName = name;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public SpringTypeDefinition 
    setLifetime(LifetimeKind lifetime)
    {
        itsLifetime = lifetime;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public SpringTypeDefinition 
    setConstructorInjector(IConstructorInjector injector)
    {
        itsConstructorInjector = injector;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public SpringTypeDefinition 
    insertPropertyInjector(IPropertyInjector injector)
    {
        itsPropertyInjectors.put( injector.getPropertyName(),injector );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public SpringTypeDefinition 
    removeConstructorInjector()
    {
        itsConstructorInjector = null;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public SpringTypeDefinition 
    removePropertyInjector(String propertyName)
    {
        itsPropertyInjectors.remove( propertyName );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public Class<?> 
    getInterfaceType()
    {
        return itsInterfaceType;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public Class<?> 
    getImplementationType()
    {
        return itsImplementationType;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public String 
    getName()
    {
        return itsName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public LifetimeKind 
    getLifetime()
    {
        return itsLifetime;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public IConstructorInjector 
    getConstructorInjector()
    {
        return itsConstructorInjector;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public List<IPropertyInjector> 
    getPropertyInjectors()
    {
        return 
            new ArrayList<IPropertyInjector>(itsPropertyInjectors.values());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public boolean 
    hasName()
    {
        return 
            itsName != null && 
            !itsName.isEmpty();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public boolean 
    hasConstructorInjector()
    {
        return itsConstructorInjector != null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public boolean 
    hasPropertyInjectors()
    {
        return !itsPropertyInjectors.isEmpty();
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public BeanDefinition
    getBeanDefinition()
    {
        BeanDefinitionBuilder builder = 
            BeanDefinitionBuilder
                .rootBeanDefinition( getImplementationType() )
                .setScope( toString( getLifetime() ) );
        
        registerConstructorInjector( builder );
        registerPropertyInjectors( builder );
        return builder.getBeanDefinition();
    }
    
    /************************************************************************
     *  
     *
     * @param definition
     * @param bean
     */
    private void 
    registerConstructorInjector(BeanDefinitionBuilder builder)
    {       
       if ( !hasConstructorInjector() )
            return;
        
        for (String value:getConstructorInjector().getConstructorValues())
            builder.addConstructorArgReference( value );
    }

    /************************************************************************
     *  
     *
     * @param definition
     * @param bean
     */
    private void 
    registerPropertyInjectors(BeanDefinitionBuilder builder)
    {
        for (IPropertyInjector injector:getPropertyInjectors())
            builder.addPropertyReference( 
                injector.getPropertyName(),
                injector.getPropertyValue() );
    }
    
    /************************************************************************
     *  
     *
     * @param scope
     * @return
     */
    private String
    toString(LifetimeKind scope)
    {
        switch (scope)
        {
        case INSTANCE_PER_PROCESS:
            return "singleton";
        
        case INSTANCE_PER_GET:
            return "prototype";
        
        default:
            return "unknown";
        }
    }

}

// ##########################################################################
