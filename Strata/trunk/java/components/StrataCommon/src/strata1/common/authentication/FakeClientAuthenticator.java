// ##########################################################################
// # File Name:	FakeClientAuthenticator.java
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

import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class FakeClientAuthenticator
    implements IClientAuthenticator
{
    private Map<String,String> itsUsers;
    
    /************************************************************************
     * Creates a new {@code FakeClientAuthenticator}. 
     *
     */
    public 
    FakeClientAuthenticator(Map<String,String> users)
    {
        itsUsers = new HashMap<String,String>();
        
        for (String userName:users.keySet())
            itsUsers.put( userName.toLowerCase(),users.get( userName ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPrincipal 
    authenticate(IClientCredential credential)
        throws AuthenticationFailureException
    {
        String userName = null;
        String password = null;
        
        try
        {
            userName = 
                credential
                    .getField(String.class,"UserName")
                    .toLowerCase();
            
            password = credential.getField( String.class,"Password" );        
        }
        catch (Throwable t)
        {
            throw new AuthenticationFailureException( t );
        }
        
        if ( !itsUsers.containsKey( userName ) )
            throw new AuthenticationFailureException( userName,"UserName" );
        
        if ( !itsUsers.get( userName ).equals( password ) )
            throw new AuthenticationFailureException( userName,"Password" );
           
        return new NullPrincipal();
    }

}

// ##########################################################################
