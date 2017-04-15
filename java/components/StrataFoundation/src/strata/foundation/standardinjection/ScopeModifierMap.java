// ##########################################################################
// # File Name:	ScopeModifierMap.java
// #
// # Copyright:	2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataFoundation Framework.
// #
// #   			The StrataFoundation Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataFoundation Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataFoundation
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.standardinjection;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import strata.foundation.injection.IScopeModifier;
import strata.foundation.injection.NullScope;
import strata.foundation.injection.RequestScope;
import strata.foundation.injection.SingletonScope;
import strata.foundation.injection.ThreadScope;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ScopeModifierMap 
    extends HashMap<Class<? extends Annotation>,IScopeModifier> 
    implements IScopeModifierMap
{

    private static final long serialVersionUID = -7997394220980633929L;

    /************************************************************************
     * Creates a new ScopeModifierMap. 
     *
     */
    public
    ScopeModifierMap() 
    {
        put( NullScope.class,new NullScopeModifier() );
        put( SingletonScope.class,new SingletonScopeModifier() );
        put( ThreadScope.class,new ThreadScopeModifier() );
        put( RequestScope.class,new RequestScopeModifier() );
    }
}

// ##########################################################################
