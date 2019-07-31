// ##########################################################################
// # File Name:	Log4jLogEntryProcessor.java
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

package strata.foundation.logger;

import org.apache.logging.log4j.Level;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Log4jLogEntryProcessor 
    implements ILogEntryProcessor
{
    private final org.apache.logging.log4j.Logger itsLogger;
    
    /************************************************************************
     * Creates a new Log4jLogEntryProcessor. 
     *
     */
    public 
    Log4jLogEntryProcessor(org.apache.logging.log4j.Logger logger)
    {
        itsLogger = logger;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    process(ILogEntry entry)
    {
        itsLogger.log( toLevel(entry.getLevel()),entry.getMessage() );
    }

    /************************************************************************
     *  
     *
     * @param level
     * @return
     */
    private Level
    toLevel(LoggingLevel level)
    {
        switch ( level )
        {
        case DEBUG:
            return Level.DEBUG;
        case ERROR:
            return Level.ERROR;
        case INFO:
        case START:
        case STOP:
            return Level.INFO;
        case VERBOSE:
            return Level.ALL;
        case WARNING:
            return Level.WARN;
        default:
            return Level.ALL;
        }
    }
}

// ##########################################################################
