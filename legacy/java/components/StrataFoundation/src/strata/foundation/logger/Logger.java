// ##########################################################################
// # File Name:	Logger.java
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

package strata.foundation.logger;

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
class Logger
    implements ILogger
{
    private Set<ILogEntryProcessor> itsProcessors;
    
    /************************************************************************
     * Creates a new {@code Logger}. 
     *
     */
    public
    Logger()
    {
        itsProcessors = new HashSet<ILogEntryProcessor>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized ILogger 
    attachProcessor(ILogEntryProcessor processor)
    {
        itsProcessors.add( processor );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized ILogger 
    detachProcessor(ILogEntryProcessor processor)
    {
        itsProcessors.remove( processor );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized boolean 
    hasProcessor(ILogEntryProcessor processor)
    {
        return itsProcessors.contains( processor );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized void 
    logStart(String message)
    {
        log( LoggingLevel.START,message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized void 
    logStop(String message)
    {
        log( LoggingLevel.STOP,message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized void 
    logDebug(String message)
    {
        log( LoggingLevel.DEBUG,message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized void 
    logVerbose(String message)
    {
        log( LoggingLevel.VERBOSE,message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized void 
    logInfo(String message)
    {
        log( LoggingLevel.INFO,message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized void 
    logWarning(String message)
    {
        log( LoggingLevel.WARNING,message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized void 
    logError(String message)
    {
        log( LoggingLevel.ERROR,message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public synchronized void 
    log(LoggingLevel level,String message)
    {
        ILogEntry entry = new LogEntry( level,message );
        
        for (ILogEntryProcessor processor:itsProcessors)
            processor.process( entry );
    }

}

// ##########################################################################
