// ##########################################################################
// # File Name:	AuthenticationFailureException.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.authentication;

import java.util.HashSet;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class AuthenticationFailureException
    extends Exception
{

    private static final long serialVersionUID = -4981275173938818839L;
    private Set<String>       itsInvalidFields;
    
    /************************************************************************
     * Creates a new {@code AuthenticationFailureException}. 
     *
     * @param t
     */
    public 
    AuthenticationFailureException(Throwable t)
    {
        super( "Authentication failed due to exception.",t );
    }
    
    /************************************************************************
     * Creates a new {@code AuthenticationFailureException}. 
     *
     * @param userName
     * @param fieldNames
     */
    public 
    AuthenticationFailureException(String userName,Set<String> fieldNames)
    {
        super( "Authentication failed for user: " + userName );
        itsInvalidFields = fieldNames;
    }

    /************************************************************************
     * Creates a new {@code AuthenticationFailureException}. 
     *
     * @param userName
     * @param fieldName
     */
    public
    AuthenticationFailureException(String userName,final String fieldName)
    {
        this( 
            userName,
            new HashSet<String>()
            {
                private static final long 
                serialVersionUID = 4427448892364141373L;
                {add(fieldName);}
            } );
    }
    
    /************************************************************************
     *  
     *
     * @param fieldName
     * @return
     */
    public boolean
    hasInvalidField(String fieldName)
    {
        return itsInvalidFields.contains( fieldName );
    }
}

// ##########################################################################
