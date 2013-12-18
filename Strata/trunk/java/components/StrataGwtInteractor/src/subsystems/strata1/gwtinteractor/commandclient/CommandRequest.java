// ##########################################################################
// # File Name:	CommandRequest.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataGwtInteractor Framework.
// #
// #   			The StrataGwtInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataGwtInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataGwtInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.gwtinteractor.commandclient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandRequest
    implements Serializable
{
    private static final long serialVersionUID = -7189315109548434865L;
    
    private String                   itsInvokerName;
    private String                   itsCommandName;
    private Map<String,Serializable> itsProperties;

    /************************************************************************
     * Creates a new {@code CommandRequest}. 
     *
     */
    public 
    CommandRequest()
    {
        this( "","" );
    }

    /************************************************************************
     * Creates a new {@code CommandRequest}. 
     *
     * @param invokerName
     * @param commandName
     */
    public 
    CommandRequest(String invokerName,String commandName)
    {
        setInvokerName( invokerName );
        setCommandName( commandName );
        itsProperties = new HashMap<String,Serializable>();
    }
    
    /************************************************************************
     *  
     *
     * @param invokerName
     */
    public void
    setInvokerName(String invokerName)
    {
        itsInvokerName = invokerName;
    }
    
    /************************************************************************
     *  
     *
     * @param commandName
     */
    public void
    setCommandName(String commandName)
    {
        itsCommandName = commandName;
    }
    
    /************************************************************************
     *  
     *
     * @param propertyName
     * @param propertyValue
     */
    public void
    insertProperty(String propertyName,Serializable propertyValue)
    {
        itsProperties.put( propertyName,propertyValue );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getInvokerName()
    {
        return itsInvokerName;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getCommandName()
    {
        return itsCommandName;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Set<String>
    getPropertyNames()
    {
        return itsProperties.keySet();
    }
    
    /************************************************************************
     *  
     *
     * @param propertyName
     * @return
     */
    public Serializable
    getPropertyValue(String propertyName)
    {
        return itsProperties.get( propertyName );
    }
}

// ##########################################################################
