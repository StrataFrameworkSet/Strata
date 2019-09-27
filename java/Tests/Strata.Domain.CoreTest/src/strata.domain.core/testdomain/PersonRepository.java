// ##########################################################################
// # File Name:	PersonRepository.java
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

import strata.domain.core.namedquery.InvalidInputException;
import strata.domain.core.repository.AbstractRepository;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
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
class PersonRepository
    extends AbstractRepository<Long,IPerson>
    implements IPersonRepository
{
    
    /************************************************************************
     * Creates a new {@code PersonRepository}. 
     *
     */
    @Inject
    public
    PersonRepository(IUnitOfWorkProvider provider)
    {
        super( Long.class,IPerson.class,provider );
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<IPerson>
    insertPerson(IPerson person)
    {
        return insert( person );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<IPerson>
    updatePerson(IPerson person)
    {
        return update( person );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Void>
    removePerson(IPerson person)
    {
        return remove( person );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Optional<IPerson>>
    getPersonByPartyKey(Long partyKey)
    {
        return getUnique( partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     * @throws InvalidInputException 
     */
    @Override
    public CompletionStage<List<IPerson>>
    getPersonsByName(PersonName name)
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
                                    .getAll()
                                    .thenApply(
                                        persons -> new ArrayList<>(persons));

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
    hasPersonWithPartyKey(Long partyKey)
    {
        return hasUnique( partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Boolean>
    hasPersonWithName(PersonName name) 
    {
        return
            getNamedQuery("HasPersonWithName")
                .thenCompose(
                    query ->
                    {
                        try
                        {
                            return
                                query
                                    .setInput( "name",name )
                                    .hasAny();
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
