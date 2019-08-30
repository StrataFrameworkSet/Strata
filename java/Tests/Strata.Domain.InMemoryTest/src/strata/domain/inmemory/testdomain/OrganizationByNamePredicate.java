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

package strata.domain.inmemory.testdomain;


import strata.domain.core.testdomain.IOrganization;
import strata.domain.inmemory.IPredicate;

import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class OrganizationByNamePredicate
    implements IPredicate<IOrganization>
{

    /************************************************************************
     * Creates a new {@code PersonByNamePredicate}. 
     *
     */
    public 
    OrganizationByNamePredicate() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    evaluate(IOrganization target,Map<String,Object> inputs)
    {
        String name = null;
        
        if ( !inputs.containsKey( "name" ) )
            throw new IllegalArgumentException("must provide name input");
        
        name = (String)inputs.get( "name" );
        
        return target.getName().equals( name );
    }

}

// ##########################################################################
