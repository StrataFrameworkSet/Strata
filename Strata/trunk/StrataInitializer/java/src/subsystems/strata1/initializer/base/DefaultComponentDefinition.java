// ##########################################################################
// # File Name:	DefaultComponentDefinition.java
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
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.initializer.base;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DefaultComponentDefinition
    implements ComponentDefinition
{
    private String                  itsComponentName;
    private Class<?>                itsComponentType;
    private ConstructorInjector     itsConstructorInjector;
    private PropertyInjectorManager itsPropertyInjectorManager;
    private ComponentScope          itsScope;
    
    /************************************************************************
     * Creates a new {@code DefaultComponentDefinition}. 
     *
     */
    public 
    DefaultComponentDefinition()
    {
        itsComponentName           = null;
        itsComponentType           = null;
        itsConstructorInjector     = null;
        itsPropertyInjectorManager = new PropertyInjectorManager();
        itsScope                   = ComponentScope.SINGLETON;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setComponentName(String componentName)
    {
        itsComponentName = componentName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setComponentType(Class<?> componentType)
    {
        itsComponentType = componentType;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setComponentType(
        Class<?> interfaceType,
        Class<?> implementationType)
    {
        itsComponentType = implementationType;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setConstructorInjector(ConstructorInjector injector)
    {
        itsConstructorInjector = injector;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setScope(ComponentScope scope)
    {
        itsScope = scope;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getComponentName()
    {
        return itsComponentName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Class<?> 
    getComponentType()
    {
        return itsComponentType;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ConstructorInjector 
    getConstructorInjector()
    {
        return itsConstructorInjector;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PropertyInjectorManager 
    getPropertyInjectorManager()
    {
        return itsPropertyInjectorManager;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ComponentScope 
    getScope()
    {
        return itsScope;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasConstructorInjector()
    {
        return itsConstructorInjector != null;
    }

}

// ##########################################################################
