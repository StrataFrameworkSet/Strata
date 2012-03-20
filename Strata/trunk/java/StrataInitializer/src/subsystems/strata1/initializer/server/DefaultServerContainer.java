// ##########################################################################
// # File Name:	DefaultServerContainer.java
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

package strata1.initializer.server;

import strata1.common.annotation.Factory;
import strata1.entity.repository.IRepository;
import strata1.initializer.base.AbstractBaseContainer;
import strata1.initializer.provider.IContainerProvider;
import strata1.integrator.annotation.Gateway;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DefaultServerContainer
    extends    AbstractBaseContainer
    implements IServerContainer
{
    
    /************************************************************************
     * Creates a new DefaultServerContainer. 
     *
     */
    public 
    DefaultServerContainer(IContainerProvider provider)
    {
        super( provider );
    }

    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> void 
    registerRepository(R repository)
    {
        // TODO Auto-generated method stub
        
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> void 
    registerRepository(String repositoryName,R repository)
    {
        // TODO Auto-generated method stub
        
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> R 
    getRepository(Class<R> repositoryType)
    {
        // TODO Auto-generated method stub
        return null;
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> R 
    getRepository(Class<R> repositoryType,String repositoryName)
    {
        // TODO Auto-generated method stub
        return null;
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> boolean 
    hasRepository(Class<R> repositoryType)
    {
        // TODO Auto-generated method stub
        return false;
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <R extends IRepository> boolean 
    hasRepository(Class<R> repositoryType,String repositoryName)
    {
        // TODO Auto-generated method stub
        return false;
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    protected boolean
    isSupportedType(Class<?> type)
    {
        return
            type.isAnnotationPresent( Gateway.class ) ||
            type.isAnnotationPresent( Factory.class ) ||
            (IRepository.class).isAssignableFrom( type );
    }

}

// ##########################################################################
