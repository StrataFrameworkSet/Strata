// ##########################################################################
// # File Name:	LogEntry.java
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

package strata1.common.logger;

import strata1.common.datetime.DateTime;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class LogEntry
    implements ILogEntry
{
    private final DateTime     itsTimestamp;
    private final LoggingLevel itsLevel;
    private final String       itsMessage;
    
    /************************************************************************
     * Creates a new {@code LogEntry}. 
     *
     */
    public 
    LogEntry(LoggingLevel level,String message)
    {
        itsTimestamp = new DateTime();
        itsLevel     = level;
        itsMessage   = message;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public DateTime 
    getTimestamp()
    {
        return itsTimestamp;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public LoggingLevel 
    getLevel()
    {
        return itsLevel;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getMessage()
    {
        return itsMessage;
    }

}

// ##########################################################################
