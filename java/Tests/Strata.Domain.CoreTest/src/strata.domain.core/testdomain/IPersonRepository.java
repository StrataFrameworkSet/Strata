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

package strata.domain.core.testdomain;


import strata.domain.core.repository.IRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IPersonRepository
    extends IRepository<Long,IPerson>
{
    CompletionStage<IPerson>
    insertPerson(IPerson person);
    
    CompletionStage<IPerson>
    updatePerson(IPerson person);
    
    CompletionStage<Void>
    removePerson(IPerson person);
    
    CompletionStage<Optional<IPerson>>
    getPersonByPartyKey(Long partyKey);
    
    CompletionStage<List<IPerson>>
    getPersonsByName(PersonName name);
    
    CompletionStage<Boolean>
    hasPersonWithPartyKey(Long partyKey);
    
    CompletionStage<Boolean>
    hasPersonWithName(PersonName name);
}

// ##########################################################################
