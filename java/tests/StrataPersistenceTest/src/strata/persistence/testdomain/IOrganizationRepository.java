// ##########################################################################
// # File Name:	IOrganizationRepository.java
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

import strata.persistence.namedquery.InvalidInputException;
import strata.persistence.namedquery.NotUniqueException;
import strata.persistence.repository.IRepository;
import strata.persistence.repository.InsertFailedException;
import strata.persistence.repository.RemoveFailedException;
import strata.persistence.repository.UpdateFailedException;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IOrganizationRepository
    extends IRepository<Long,IOrganization>
{
    public IOrganization
    insertOrganization(IOrganization organization)
        throws InsertFailedException;
    
    public IOrganization
    updateOrganization(IOrganization organization)
        throws UpdateFailedException;
    
    public void
    removeOrganization(IOrganization organization)
        throws RemoveFailedException;
    
    public IOrganization
    getOrganizationByPartyKey(Long partyKey);
    
    public IOrganization
    getOrganizationByName(String name)
        throws InvalidInputException,NotUniqueException;
    
    public boolean
    hasOrganizationWithPartyKey(Long partyKey);
    
    public boolean
    hasOrganizationWithName(String name) 
        throws NotUniqueException;
}

// ##########################################################################
