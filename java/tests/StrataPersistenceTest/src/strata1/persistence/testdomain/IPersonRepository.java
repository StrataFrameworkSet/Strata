// ##########################################################################
// # File Name:	IPersonRepository.java
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

package strata1.persistence.testdomain;

import strata1.persistence.repository.IFinder;
import strata1.persistence.repository.IRepository;
import strata1.persistence.repository.InsertFailedException;
import strata1.persistence.repository.InvalidInputException;
import strata1.persistence.repository.RemoveFailedException;
import strata1.persistence.repository.UpdateFailedException;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IPersonRepository
    extends IRepository
{
    public IPerson
    insertPerson(IPerson person)
        throws InsertFailedException;
    
    public IPerson
    updatePerson(IPerson person)
        throws UpdateFailedException;
    
    public void
    removePerson(IPerson person)
        throws RemoveFailedException;
    
    public IPerson
    getPersonByPartyKey(Long partyKey);
    
    public List<IPerson>
    getPersonsByName(PersonName name) 
        throws InvalidInputException;
    
    public IFinder<IPerson>
    getFinder(String finderName);
    
    public boolean
    hasPersonWithPartyKey(Long partyKey);
    
    public boolean
    hasPersonWithName(PersonName name);
    
    public boolean
    hasFinder(String finderName);
}

// ##########################################################################
