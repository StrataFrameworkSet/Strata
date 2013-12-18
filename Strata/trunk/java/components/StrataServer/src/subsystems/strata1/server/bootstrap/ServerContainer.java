// ##########################################################################
// # File Name:	ServerContainer.java
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

package strata1.server.bootstrap;

import strata1.common.annotation.Factory;
import strata1.common.authentication.IPrincipal;
import strata1.common.containerprovider.IContainerProvider;
import strata1.entity.repository.IRepository;
import strata1.integrator.annotation.Gateway;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ServerContainer
    implements IServerContainer
{
    
    private IContainerProvider itsProvider;

    /************************************************************************
     * Creates a new ServerContainer. 
     *
     */
    public 
    ServerContainer(IContainerProvider provider)
    {
        itsProvider = provider;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> void 
    registerRepository(R repository)
    {
        registerRepository(
            repository.getClass().getCanonicalName(),
            repository);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> void 
    registerRepository(String repositoryName,R repository)
    {
        new InstanceInserter<R>(getProvider())
            .insertByType( IRepository.class,repositoryName,repository );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> R 
    getRepository(Class<R> repositoryType)
    {
        return 
            getRepository(repositoryType,repositoryType.getCanonicalName());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> R 
    getRepository(Class<R> repositoryType,String repositoryName)
    {
        return getProvider().getInstance( repositoryType,repositoryName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> boolean 
    hasRepository(Class<R> repositoryType)
    {
        return             
            hasRepository(repositoryType,repositoryType.getCanonicalName());

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> boolean 
    hasRepository(Class<R> repositoryType,String repositoryName)
    {
        return getProvider().hasInstance( repositoryType,repositoryName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> void 
    registerPrincipal(P principal)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> void 
    registerPrincipal(String principalName,P principal)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> P 
    getPrincipal(Class<P> principalType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> P 
    getPrincipal(Class<P> principalType,String principalName)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> boolean 
    hasPrincipal(Class<P> principalType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> boolean 
    hasPrincipal(Class<P> principalType,String principalName)
    {
        // TODO Auto-generated method stub
        return false;
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
    public <F>F getFactory(Class<F> factoryType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F>F getFactory(Class<F> factoryType,String name)
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
    public <F>boolean hasFactory(Class<F> factoryType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F>boolean hasFactory(Class<F> factoryType,String factoryName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected IContainerProvider getProvider()
    {
        return itsProvider;
    }

}

// ##########################################################################
