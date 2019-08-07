// ##########################################################################
// # File Name:	UpdateResponse.java
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

package strata1.gwtclient.updateclient;

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
class UpdateResponse
    implements Serializable
{

    private static final long serialVersionUID = 5886357873613185935L;
    
    private String                   itsUpdatableName;
    private String                   itsUpdateAction;
    private Map<String,Serializable> itsUpdatedProperties;

    /************************************************************************
     * Creates a new {@code UpdateResponse}. 
     *
     */
    public 
    UpdateResponse()
    {
        this( "" );
    }
    
    /************************************************************************
     * Creates a new {@code UpdateResponse}. 
     *
     * @param updatableName
     */
    public 
    UpdateResponse(String updatableName)
    {
        itsUpdatableName     = updatableName;
        itsUpdatedProperties = new HashMap<String,Serializable>();
    }

    /************************************************************************
     *  
     *
     * @param updatableName
     */
    public void
    setUpdatableName(String updatableName)
    {
        itsUpdatableName = updatableName;
    }
    
    /************************************************************************
     *  
     *
     * @param updateAction
     */
    public void
    setUpdateAction(String updateAction)
    {
        itsUpdateAction = updateAction;
    }
    
    /************************************************************************
     *  
     *
     * @param name
     * @param value
     */
    public void
    insertUpdatedProperty(String name,Serializable value)
    {
        itsUpdatedProperties.put( name,value );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getUpdatableName()
    {
        return itsUpdatableName;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getUpdateAction()
    {
        return itsUpdateAction;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Set<String>
    getPropertyNames()
    {
        return itsUpdatedProperties.keySet();
    }
    
    /************************************************************************
     *  
     *
     * @param name
     * @return
     */
    public Serializable
    getPropertyValue(String name)
    {
        return itsUpdatedProperties.get( name );
    }
}

// ##########################################################################
