// ##########################################################################
// # File Name:	PersonByNamePredicate.java
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

package strata.persistence.inmemorytestdomain;

import java.util.Map;
import strata.persistence.inmemory.IPredicate;
import strata.persistence.testdomain.IPerson;
import strata.persistence.testdomain.PersonName;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PersonByNamePredicate
    implements IPredicate<IPerson>
{

    /************************************************************************
     * Creates a new {@code PersonByNamePredicate}. 
     *
     */
    public 
    PersonByNamePredicate() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    evaluate(IPerson target,Map<String,Object> inputs)
    {
        PersonName name = null;
        
        if ( !inputs.containsKey( "name" ) )
            throw new IllegalArgumentException("must provide name input");
        
        name = (PersonName)inputs.get( "name" );
        
        return target.getName().equals( name );
    }

}

// ##########################################################################
