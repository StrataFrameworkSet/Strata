// ##########################################################################
// # File Name:	OrganizationRepository.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataEntity Framework.
// #
// #   			The StrataEntity Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataEntity Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.domain.core.testdomain;


import strata.domain.core.repository.AbstractRepository;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

import java.util.Optional;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class OrganizationRepository
    extends AbstractRepository<Long,IOrganization>
    implements IOrganizationRepository
{
    
    /************************************************************************
     * Creates a new {@code OrganizationRepository}. 
     *
     */
    public 
    OrganizationRepository(IUnitOfWorkProvider provider)
    {
        super( Long.class,IOrganization.class,provider );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<IOrganization>
    insertOrganization(IOrganization organization)
    {
        return insert( organization );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<IOrganization>
    updateOrganization(IOrganization organization)
    {
        return update( organization );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Void>
    removeOrganization(IOrganization organization)
    {
        return remove( organization );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Optional<IOrganization>>
    getOrganizationByPartyKey(Long partyKey)
    {
        return getUnique( partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Optional<IOrganization>>
    getOrganizationByName(String name)
    {
        return
            getNamedQuery("GetByName")
                .thenCompose(
                    query ->
                    {
                        try
                        {
                            return
                                query
                                    .setInput( "name",name )
                                    .getUnique();

                        }
                        catch (Exception e)
                        {
                            throw new CompletionException(e);
                        }
                    }
                );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Boolean>
    hasOrganizationWithPartyKey(Long partyKey)
    {
        return hasUnique( partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Boolean>
    hasOrganizationWithName(String name)
    {
        return
            getNamedQuery("GetByName")
                .thenCompose(
                    query ->
                    {
                        try
                        {
                            return
                                query
                                    .setInput( "name",name )
                                    .hasUnique();

                        }
                        catch (Exception e)
                        {
                            throw new CompletionException(e);
                        }
                    }
                );
    }

}

// ##########################################################################
