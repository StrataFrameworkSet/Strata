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

package strata.persistence.testdomain;

import java.util.ArrayList;
import java.util.List;
import strata.persistence.namedquery.INamedQuery;
import strata.persistence.namedquery.InvalidInputException;
import strata.persistence.repository.AbstractRepository;
import strata.persistence.repository.InsertFailedException;
import strata.persistence.repository.RemoveFailedException;
import strata.persistence.repository.UpdateFailedException;
import strata.persistence.unitofwork.IUnitOfWorkProvider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PersonRepository
    extends    AbstractRepository<Long,IPerson>
    implements IPersonRepository
{
    
    /************************************************************************
     * Creates a new {@code PersonRepository}. 
     *
     */
    public 
    PersonRepository(IUnitOfWorkProvider provider)
    {
        super( Long.class,IPerson.class,provider );
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPerson 
    insertPerson(IPerson person) 
        throws InsertFailedException
    {
        return insertNew( person );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPerson 
    updatePerson(IPerson person) 
        throws UpdateFailedException
    {
        return updateExisting( person );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    removePerson(IPerson person) 
        throws RemoveFailedException
    {
        removeExisting( person );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPerson 
    getPersonByPartyKey(Long partyKey)
    {
        return getExisting( partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     * @throws InvalidInputException 
     */
    @Override
    public List<IPerson> 
    getPersonsByName(PersonName name) 
        throws InvalidInputException
    {
        INamedQuery<IPerson> finder = getNamedQuery("GetByName");
        
        finder.setInput( "name",name );
        return new ArrayList<IPerson>(finder.getAll());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasPersonWithPartyKey(Long partyKey)
    {
        return hasExisting( partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasPersonWithName(PersonName name) 
    {
        INamedQuery<IPerson> finder = getNamedQuery( "HasPersonWithName" );
        
        try
        {
            finder.setInput( "name",name );
            return finder.hasAny();
        }
        catch(InvalidInputException e)
        {
            return false;
        }
    }

}

// ##########################################################################
