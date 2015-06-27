// ##########################################################################
// # File Name:	EmailAddress.java
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

package strata1.persistence.testdomain;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class EmailAddress
    extends    AbstractContactInformation
    implements IContactInformation
{
    private String itsRecipient;
    private String itsMailHost;
    
    /************************************************************************
     * Creates a new {@code EmailAddress}. 
     *
     */
    public 
    EmailAddress()
    {
        // TODO Auto-generated constructor stub
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return itsRecipient + "@" + itsMailHost;
    }

    /************************************************************************
     *  
     *
     * @param recipient
     */
    public void
    setRecipient(String recipient)
    {
        itsRecipient = recipient;
    }
    
    /************************************************************************
     *  
     *
     * @param mailHost
     */
    public void
    setMailHost(String mailHost)
    {
        itsMailHost = mailHost;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getRecipient()
    {
        return itsRecipient;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getMailHost()
    {
        return itsMailHost;
    }
}

// ##########################################################################
