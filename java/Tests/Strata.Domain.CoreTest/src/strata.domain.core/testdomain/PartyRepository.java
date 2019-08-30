// ##########################################################################
// # File Name:	PartyRepository.java
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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static strata.foundation.core.utility.Awaiter.await;


/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PartyRepository
    extends AbstractRepository<Long,IParty>
    implements IPartyRepository
{
    
    /************************************************************************
     * Creates a new {@code PartyRepository}. 
     *
     * @param provider
     */
    public 
    PartyRepository(IUnitOfWorkProvider provider)
    {
        super( Long.class,IParty.class,provider );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Optional<IParty>>
    getPartyByKey(Long partyKey)
    {
        return getUnique(partyKey);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<List<IParty>>
    getMembersOf(IOrganization organization)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                    organization
                        .getMemberIds()
                        .stream()
                        .map( key -> await(getPartyByKey(key)))
                        .filter( party -> party.isPresent())
                        .map( party -> party.get() )
                        .collect(Collectors.toList()));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Boolean>
    hasPartyWithKey(Long partyKey)
    {
        return hasUnique(partyKey);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Boolean>
    hasMembersOf(IOrganization organization)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    for (
                        Boolean result:
                            organization
                                .getMemberIds()
                                .stream()
                                .map( key -> await(hasPartyWithKey(key)))
                                .collect(Collectors.toList()))
                        if (result == true)
                            return true;

                    return false;
                });

    }

}

// ##########################################################################
