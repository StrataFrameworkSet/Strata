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

package strata1.entity.testdomain;

import strata1.entity.repository.IFinder;
import strata1.entity.repository.IRepository;
import strata1.entity.repository.InsertFailedException;
import strata1.entity.repository.InvalidInputException;
import strata1.entity.repository.NotUniqueException;
import strata1.entity.repository.RemoveFailedException;
import strata1.entity.repository.UpdateFailedException;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IOrganizationRepository
    extends IRepository
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
    
    public IFinder<IOrganization>
    getFinder(String finderName);
    
    public boolean
    hasOrganizationWithPartyKey(Long partyKey);
    
    public boolean
    hasOrganizationWithName(String name) 
        throws NotUniqueException;
    
    public boolean
    hasFinder(String finderName);
    
}

// ##########################################################################
