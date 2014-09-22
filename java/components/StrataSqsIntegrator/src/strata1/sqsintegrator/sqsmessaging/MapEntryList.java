// ##########################################################################
// # File Name:	MapEntryList.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSqsIntegrator Framework.
// #
// #   			The StrataSqsIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSqsIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSqsIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.sqsintegrator.sqsmessaging;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@XmlRootElement(name="payload")
public 
class MapEntryList
{
    protected ArrayList<MapEntry> entries;
    
    /************************************************************************
     * Creates a new MapEntryList. 
     *
     */
    public 
    MapEntryList()
    {
        entries = new ArrayList<MapEntry>();
    }

    /************************************************************************
     * Creates a new MapEntryList. 
     *
     * @param payload
     */
    public 
    MapEntryList(Map<String,String> payload)
    {
        this();
        for (String key:payload.keySet())
            entries.add( new MapEntry( key,payload.get(key) ) );
    }

    /************************************************************************
     *  
     *
     * @param e
     */
    public void
    setEntries(ArrayList<MapEntry> e)
    {
        entries = e;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public ArrayList<MapEntry>
    getEntries()
    {
        return entries;
    }
}

// ##########################################################################
