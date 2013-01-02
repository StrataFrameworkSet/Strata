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

package strata1.entity.testdomain;

import strata1.entity.repository.IFinder;
import strata1.entity.repository.IRepositoryContext;
import strata1.entity.repository.IRepositoryProvider;
import strata1.entity.repository.InsertFailedException;
import strata1.entity.repository.InvalidInputException;
import strata1.entity.repository.RemoveFailedException;
import strata1.entity.repository.UpdateFailedException;
import java.util.ArrayList;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PersonRepository
    implements IPersonRepository
{
    private IRepositoryProvider<Long,IPerson> itsProvider;
    
    /************************************************************************
     * Creates a new {@code PersonRepository}. 
     *
     */
    public 
    PersonRepository(IRepositoryProvider<Long,IPerson> provider)
    {
        itsProvider = provider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IRepositoryContext 
    getContext()
    {
        return itsProvider.getContext();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPerson 
    insertPerson(IPerson person) 
        throws InsertFailedException
    {
        return itsProvider.insertNew( person );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPerson 
    updatePerson(IPerson person) 
        throws UpdateFailedException
    {
        return itsProvider.updateExisting( person );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    removePerson(IPerson person) 
        throws RemoveFailedException
    {
        itsProvider.removeExisting( person );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPerson 
    getPersonByPartyKey(Long partyKey)
    {
        return itsProvider.getExisting( partyKey );
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
        IFinder<IPerson> finder = getFinder("GetByName");
        
        finder.setInput( "name",name );
        return new ArrayList<IPerson>(finder.getAll());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IFinder<IPerson>
    getFinder(String finderName)
    {
        return itsProvider.getFinder( "strata1.entity.testdomain.IPerson." + finderName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasPersonWithPartyKey(Long partyKey)
    {
        return itsProvider.hasExisting( partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasPersonWithName(PersonName name) 
    {
        IFinder<IPerson> finder = getFinder( "GetByName" );
        
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

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasFinder(String finderName)
    {
        return itsProvider.hasFinder( "strata1.entity.testdomain.IPerson." + finderName );
    }
}

// ##########################################################################
