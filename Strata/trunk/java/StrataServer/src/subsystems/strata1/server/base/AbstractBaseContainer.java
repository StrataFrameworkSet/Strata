// ##########################################################################
// # File Name:	AbstractBaseContainer.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInitializer Framework.
// #
// #   			The StrataInitializer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInitializer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInitializer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.server.base;

import strata1.common.annotation.Factory;
import strata1.common.containerprovider.IContainerProvider;
import strata1.integrator.annotation.Gateway;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractBaseContainer
    implements IBaseContainer
{
    private IContainerProvider itsProvider;

    /************************************************************************
     * Creates a new AbstractBaseContainer. 
     *
     */
    public 
    AbstractBaseContainer(IContainerProvider provider)
    {
        itsProvider = provider;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G> void 
    registerGateway(G gateway)
    {
        
        registerGateway( gateway.getClass().getCanonicalName(),gateway );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G> void 
    registerGateway(String gatewayName,G gateway)
    {
        Class<?> gatewayType = gateway.getClass();
        
        if ( gatewayType.isAnnotationPresent( Gateway.class ) )
            getProvider().registerInstance( gatewayName,gateway );
        else
            throw 
                new IllegalArgumentException( 
                    gatewayType + " is not a supported type.");    
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G> G 
    getGateway(Class<G> gatewayType)
    {
        return getGateway( gatewayType,gatewayType.getCanonicalName() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G> G 
    getGateway(Class<G> gatewayType,String gatewayName)
    {
        if ( !gatewayType.isAnnotationPresent( Gateway.class ) )
            throw 
                new IllegalArgumentException( 
                    gatewayType + "is not a gateway.");
        
        return itsProvider.getInstance( gatewayType,gatewayName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G> boolean 
    hasGateway(Class<G> gatewayType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G> boolean 
    hasGateway(Class<G> gatewayType,String gatewayName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> void 
    registerFactory(F factory)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> void 
    registerFactory(String factoryName,F factory)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> F 
    getFactory(Class<F> factoryType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> F 
    getFactory(Class<F> factoryType,String name)
    {
        if ( !factoryType.isAnnotationPresent( Factory.class ) )
            throw 
                new IllegalArgumentException( 
                    factoryType + "is not a factory.");
        
        return itsProvider.getInstance( factoryType,name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> boolean 
    hasFactory(Class<F> factoryType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> boolean 
    hasFactory(Class<F> factoryType,String factoryName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected IContainerProvider
    getProvider()
    {
        return itsProvider;
    }
    
}


// ##########################################################################
