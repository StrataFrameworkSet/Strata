// ##########################################################################
// # File Name:	Definition.java
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

package strata.foundation.bootstrap;

import java.lang.annotation.Annotation;
import javax.inject.Provider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Definition<T>
{
    private final Class<? extends Provider<T>> itsProviderType;
    private final Class<? extends Annotation>  itsScopeType;
    
    /************************************************************************
     * Creates a new LoggerDefinition. 
     *
     */
    public 
    Definition(
        Class<? extends Provider<T>> providerType,
        Class<? extends Annotation>  scopeType)
    {
        itsProviderType = providerType;
        itsScopeType    = scopeType;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public Class<? extends Provider<T>>
    getProviderType()
    {
        return itsProviderType;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Class<? extends Annotation>
    getScopeType()
    {
        return itsScopeType;
    }
}

// ##########################################################################
