// ##########################################################################
// # File Name:	GuiceScopeConverter.java
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

package strata.foundation.guiceinjection;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import com.google.inject.Singleton;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;
import strata.foundation.injection.RequestScope;
import strata.foundation.injection.SessionScope;
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
class GuiceScopeConverter
{
    private Map<Class<? extends Annotation>,Class<? extends Annotation>>
    itsScopes;
    
    /************************************************************************
     * Creates a new GuiceScopeConverter. 
     *
     */
    public 
    GuiceScopeConverter() 
    {
        itsScopes = 
            new HashMap<
                Class<? extends Annotation>,
                Class<? extends Annotation>>();
        
        itsScopes.put( SingletonScope.class,Singleton.class );
        itsScopes.put( RequestScope.class,RequestScoped.class );
        itsScopes.put( SessionScope.class,SessionScoped.class );
        itsScopes.put( ThreadScope.class,ThreadScoped.class );
    }

    /************************************************************************
     *  
     *
     * @param scope
     * @return
     */
    public Class<? extends Annotation>
    convert(Class<? extends Annotation> scope)
    {
        if ( itsScopes.containsKey( scope ) )
            return itsScopes.get( scope );
        
        return scope;
    }
}

// ##########################################################################
