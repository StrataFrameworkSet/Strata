// ##########################################################################
// # File Name:	UserNameAndPasswordCredential.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClient Framework.
// #
// #   			The StrataClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.authentication;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class UserNameAndPasswordCredential
    implements IClientCredential
{
    private String itsUserName;
    private String itsPassword;
    
    /************************************************************************
     * Creates a new {@code UserNameAndPasswordCredential}. 
     *
     */
    public 
    UserNameAndPasswordCredential(String userName,String password)
    {
        itsUserName = userName;
        itsPassword = password;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> void 
    setField(String name,T field)
    {
        if ( "UserName".equalsIgnoreCase( name ) )
            itsUserName = (String)field;
        else if ( "Password".equalsIgnoreCase( name ) )
            itsPassword = (String)field;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getField(Class<T> type,String name)
    {
        if ( "UserName".equalsIgnoreCase( name ) )
            return type.cast( itsUserName );
        else if ( "Password".equalsIgnoreCase( name ) )
            return type.cast( itsPassword );
        
        throw new IllegalArgumentException();
    }

    
}

// ##########################################################################
